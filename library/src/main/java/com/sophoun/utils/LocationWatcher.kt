package com.sophoun.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.location.LocationManagerCompat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * LocationWatcher is a class that make us easier
 * to listen to location update and check GPS is enable or not
 * and it's also easy to get last know location.
 *
 * Note: require permission
 * - [android.permission.ACCESS_FINE_LOCATION]
 * or
 * - [android.permission.ACCESS_COARSE_LOCATION]
 */
@ExperimentalCoroutinesApi
@SuppressLint("MissingPermission")
class LocationWatcher(
    context: Context,
    private val provider: String = LocationManager.GPS_PROVIDER, // use GPS as default
    private val updateInterval: Long = 10 * 1000, // 10 seconds
    private val minDistance: Float = 1f // update every moved 1 meter
) {
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    /**
     * @return location service is enable (GPS)
     */
    fun isLocationEnabled() = LocationManagerCompat.isLocationEnabled(locationManager)

    /**
     * Collect location update using flow api
     */
    fun getLocationUpdateFlow() = callbackFlow<Location>  {
        val locationUpdateListener = object: LocationListener {
            override fun onLocationChanged(location: Location?) {
                if (location != null) {
                    offer(location)
                }
            }
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String?) {}
            override fun onProviderDisabled(provider: String?) {}
        }

        try {
            locationManager.requestLocationUpdates(
                provider,
                updateInterval,
                minDistance,
                locationUpdateListener
            )
        } catch (e: Exception) {
            close(e)
        }

        awaitClose {
            locationManager.removeUpdates(locationUpdateListener)
        }
    }

    /**
     * Get last know location
     * @return [null] If you get it for the first time
     */
    suspend fun getLastLocation() = suspendCancellableCoroutine<Location> { cancelation ->
        try {
            val location = locationManager.getLastKnownLocation(provider)
            cancelation.resume(location)
        } catch (e: Exception) {
            cancelation.resumeWithException(e)
        }
    }
}
