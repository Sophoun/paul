package com.sophoun.utils.billing

import android.app.Activity
import android.content.Context
import androidx.annotation.MainThread
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.SkuType
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.Purchase.PurchaseState
import com.android.billingclient.api.PurchaseHistoryRecord
import com.android.billingclient.api.PurchaseHistoryResponseListener
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.sophoun.utils.DLog

/**
 * GooglePlayBilling is a class that provide
 * rich functionality to lunch Google billing purchase flow.
 *
 * Sample usage:
 * ```
 * val googlePlayBilling = GooglePlayBilling.Builder(context!!, getString(R.string.app_name))
 *      .onConnected {
 *          DLog.i("Billing", "Connected")
 *      }.onDisconnected {
 *          DLog.i("Billing", "Disconnected")
 *      }.onError {
 *          DLog.i("Billing", "Error: ${it.message}")
 *      }.onBillingUpdated { billingResult, purchases ->
 *          DLog.i("Billing", "onBillingUpdated ${billingResult.debugMessage}")
 *      }.onPurchaseHistoryUpdated { billingResult, purchasesHistory ->
 *          DLog.i("Billing", "onPurchaseHistoryUpdated ${billingResult.debugMessage}")
 *      }.build()
 *
 *      googlePlayBilling.connect()
 *
 *      googlePlayBilling.querySkuDetails("android.test.purchased", "android.test.canceled", "android.test.item_unavailable") {
 *          it.forEach { sku ->
 *              googlePlayBilling.purchase(activity!!, sku)
 *          }
 *     }
 * ```
 */
class GooglePlayBilling private constructor(
    private val context: Context,
    private val signatureKey: String,
    // Connection status
    private val onConnected: () -> Unit,
    private val onDisconnected: () -> Unit,
    private val onError: (Exception) -> Unit,
    // Listeners
    private val onBillingUpdated: (billingResult: BillingResult, purchases: List<Purchase>) -> Unit,
    private val onPurchaseHistoryUpdated: (billingResult: BillingResult, purchases: List<PurchaseHistoryRecord>?) -> Unit
) {

    private val billingClientBuilder: BillingClient.Builder = BillingClient.newBuilder(context)
    private lateinit var billingClient: BillingClient

    /**
     * Billing client state listener
     */
    private val billingClientStateListener = object: BillingClientStateListener {
        override fun onBillingServiceDisconnected() {
            DLog.i("onBillingServiceDisconnected")
            onDisconnected.invoke()
        }

        override fun onBillingSetupFinished(billingResult: BillingResult) {
            DLog.i("onBillingSetupFinished")
            onConnected.invoke()
        }
    }

    /**
     * Purchase update listener.
     *
     * We should handle 4 main state:
     *  - `BillingResponseCode.OK` = purchase success
     *  - `BillingResponseCode.ITEM_ALREADY_OWNED` = product already is buy
     *  - `BillingResponseCode.USER_CANCELED` = user provide invalid credit card or something
     *  - `BillingResponseCode.ITEM_UNAVAILABLE` = product id doesn't exist on google play console
     */
    private val purchaseUpdateListener = PurchasesUpdatedListener { billingResult, purchaseList ->
        purchaseList?.forEach {
            val purchaseVerify =
                GooglePlayBillingSecurity.verifyPurchase(
                    signatureKey,
                    it.originalJson,
                    it.signature
                )
            if (!purchaseVerify) {
                onError.invoke(Exception("Can't verify purchase signature"))
                return@PurchasesUpdatedListener
            }
        }
        onBillingUpdated.invoke(billingResult, purchaseList.orEmpty())
        acknowledgedPurchases(purchaseList.orEmpty())
    }

    private val purchaseHistoryResponseListener = PurchaseHistoryResponseListener { billingResult, purchaseHistoryList ->
        onPurchaseHistoryUpdated.invoke(billingResult, purchaseHistoryList)
    }

    /**
     * Acknowledged purchased products
     * { you must acknowledge all purchases within three days.
     * Failure to properly acknowledge purchases results in those purchases being refunded. }
     */
    private fun acknowledgedPurchases(purchases: List<Purchase>) {
        purchases.filter {
            (it.purchaseState == PurchaseState.PURCHASED) && !it.isAcknowledged
        }.forEach {
            val acknowledgePurchase = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(it.purchaseToken).build()
            billingClient.acknowledgePurchase(acknowledgePurchase) { billingResult ->
                if (billingResult.responseCode == BillingResponseCode.OK) {
                    DLog.i("AcknowledgedResponse")
                } else {
                    onError.invoke(Exception(billingResult.debugMessage))
                }
            }
        }
    }

    /**
     * Connect billing service
     */
    fun connect() {
        billingClientBuilder
            .enablePendingPurchases()
            .setListener(purchaseUpdateListener)
        billingClient = billingClientBuilder.build()
        billingClient.startConnection(billingClientStateListener)
    }

    /**
     * To query update purchased cache on Google Play.
     *
     * You should call queryPurchases() at least twice in your code:
     * - Call every time your app launches so that you can restore any
     *   purchases that a user has made since the app last stopped.
     * - Call in your onResume() method, because a user can make a purchase
     *   when your app is in the background (for example, redeeming
     *   a promo code in the Google Play Store app).
     * @param loadFromCache (true) means not make a network request [default false]
     * @param skuType SkuType.INAPP or SkuType.SUBS
     */
    fun queryCachedPurchase(loadFromCache: Boolean = false, skuType: String = SkuType.INAPP) {
        if (loadFromCache) {
            billingClient.queryPurchases(skuType)
        } else {
            billingClient.queryPurchaseHistoryAsync(skuType, purchaseHistoryResponseListener)
        }
    }

    /**
     * Testing product id
     * `"android.test.purchased"`, `"android.test.canceled"`, `"android.test.item_unavailable"`
     * @param sku is a list of IN APP product id
     * @param skuType SkuType.INAPP or SkuType.SUBS
     */
    fun querySkuDetails(vararg sku: String, skuType: String = SkuType.INAPP, onQueryFinished: (List<SkuDetails>) -> Unit = {}) {
        val skuDetailsParams = SkuDetailsParams.newBuilder()
            .setSkusList(sku.toList())
            .setType(skuType)
            .build()
        billingClient.querySkuDetailsAsync(skuDetailsParams) { billingResult, listSkuDetails ->
            if (billingResult.responseCode == BillingResponseCode.OK) {
                DLog.i("querySkuDetailsAsync: listSkuDetails= $listSkuDetails")
                listSkuDetails?.let {
                    DLog.i("Sku: %s", listSkuDetails.map { it.sku })
                    onQueryFinished.invoke(listSkuDetails)
                }
            } else {
                onError.invoke(Exception(billingResult.debugMessage))
            }
        }
    }

    /**
     * Result will callback in method connect() above and in
     * object `billingClientBuilder.setListener` `onBillingUpdated()`
     *
     * for clearing purchase history use command:
     *  - `./adb -s (deviceId) shell pm clear com.android.vending`
     *
     *  Ex:
     *  - `./adb -s ac968101 shell pm clear com.android.vending`
     */
    @MainThread
    fun purchase(activity: Activity, skuDetails: SkuDetails) {
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()
        billingClient.launchBillingFlow(activity, billingFlowParams)
    }

    /**
     * Builder class
     */
    class Builder(private val context: Context, private val signatureKey: String) {
        // Initialize on billing connected listener
        private var onConnected: () -> Unit = {}
        // Initialize on billing disconnected listener
        private var onDisconnected: () -> Unit = {}
        // Initialize error listener
        private var onError: (Exception) -> Unit = {}
        // Initialize Billing update listener
        private var onBillingUpdated: (billingResult: BillingResult, purchases: List<Purchase>) -> Unit = { _, _  -> }
        private var onPurchaseHistoryUpdated: (billingResult: BillingResult, purchasesHistory: List<PurchaseHistoryRecord>?) -> Unit = { _, _ -> }

        // Connection state
        fun onConnected(onConnected: () -> Unit) = apply { this@Builder.onConnected = onConnected }
        fun onDisconnected(onDisconnected: () -> Unit) = apply { this@Builder.onDisconnected = onDisconnected }
        fun onError(onError: (Exception) -> Unit) = apply { this@Builder.onError = onError }

        // Listeners
        fun onBillingUpdated(onBillingUpdated: (billingResult: BillingResult, purchases: List<Purchase>) -> Unit) = apply { this@Builder.onBillingUpdated = onBillingUpdated }
        fun onPurchaseHistoryUpdated(onPurchaseHistoryUpdated: (billingResult: BillingResult, purchasesHistory: List<PurchaseHistoryRecord>?) -> Unit) = apply { this@Builder.onPurchaseHistoryUpdated = onPurchaseHistoryUpdated }

        // Build GooglePlayBilling object
        fun build() = GooglePlayBilling(
            context,
            signatureKey,
            onConnected,
            onDisconnected,
            onError,
            onBillingUpdated,
            onPurchaseHistoryUpdated
        )
    }
}