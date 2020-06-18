package com.sophoun.utils.billing

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.Signature
import java.security.spec.X509EncodedKeySpec

/**
 * Security-related methods. For a secure implementation, all of this code
 * should be implemented on a server that communicates with the
 * application on the device. For the sake of simplicity and clarity of this
 * example, this code is included here and is executed on the device. If you
 * must verify the purchases on the phone, you should obfuscate this code to
 * make it harder for an attacker to replace the code with stubs that treat all
 * purchases as verified.
 */
object GooglePlayBillingSecurity {
    private const val KEY_FACTORY_ALGORITHM = "RSA"
    private const val SIGNATURE_ALGORITHM = "SHA1withRSA"
    /**
     * Verifies that the data was signed with the given signature, and returns
     * the verified purchase. The data is in JSON format and signed
     * with a private key. The data also contains the [PurchaseState]
     * and product ID of the purchase.
     * @param base64PublicKey The base64-encoded public key to use for verifying.
     * @param signedData The signed JSON string (signed, not encrypted)
     * @param signature The signature for the data, signed with the private key
     */
    fun verifyPurchase(base64PublicKey: String?, signedData: String, signature: String?): Boolean {
        val key =
            generatePublicKey(
                base64PublicKey
            )
        return verify(
            key,
            signedData,
            signature
        )
    }

    /**
     * Generates a PublicKey instance from a string containing the
     * Base64-encoded public key.
     *
     * @param encodedPublicKey Base64-encoded public key
     * @throws IllegalArgumentException If encodedPublicKey is invalid
     */
    private fun generatePublicKey(encodedPublicKey: String?): PublicKey? {
        return try {
            val decodedKey: ByteArray = Base64.decode(encodedPublicKey, Base64.DEFAULT)
            val keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM)
            keyFactory.generatePublic(X509EncodedKeySpec(decodedKey))
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Verifies that the signature from the server matches the computed
     * signature on the data. Returns true if the data is correctly signed.
     *
     * @param publicKey Public key associated with the developer account
     * @param signedData Signed data from server
     * @param signature Server signature
     * @return [true] If the data and signature match
     */
    private fun verify(publicKey: PublicKey?, signedData: String, signature: String?): Boolean {
        val sig: Signature
        return try {
            sig = Signature.getInstance(SIGNATURE_ALGORITHM)
            sig.initVerify(publicKey)
            sig.update(signedData.toByteArray())
            sig.verify(Base64.decode(signature, Base64.DEFAULT))
        } catch (e: Exception) {
            false
        }
    }
}