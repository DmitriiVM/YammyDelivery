package com.dvm.order.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.order.R
import com.dvm.order.map.model.MapState
import com.dvm.utils.getErrorMessage
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
internal class MapViewModel(
    private val context: Context,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(MapState())
        private set

    private val addressItems = savedState.getLiveData("address_items", emptyList<String>())

    init {
        addressItems
            .asFlow()
            .distinctUntilChanged()
            .map { it.joinToString(", ") }
            .onEach { state = state.copy(address = it) }
            .launchIn(viewModelScope)
    }

    fun dismissAlert() {
        state = state.copy(alert = null)
    }

    fun onMapReady(map: GoogleMap) {
        moveToLocation(map)

        map
            .locationFlow()
            .distinctUntilChanged()
            .debounce(500)
            .catch { throwable ->
                state = state.copy(alert = throwable.getErrorMessage(context))
            }
            .onEach { latLng ->
                addressItems.value = getAddress(latLng.latitude, latLng.longitude)
            }
            .launchIn(viewModelScope)
    }

    fun onLocationPermissionGranted(map: GoogleMap) {
        moveToLocation(map)
    }

    fun onButtonCompleteClick() {
        if (addressItems.value?.size == 3) {
            navigator.goTo(
                Destination.BackToOrdering(
                    addressItems.value.orEmpty().joinToString(", ")
                )
            )
        } else {
            state = state.copy(alert = context.getString(R.string.ordering_address_error))
        }
    }

    @SuppressLint("MissingPermission")
    private fun moveToLocation(
        map: GoogleMap,
        defaultLat: Double = 55.752,
        defaultLng: Double = 37.624,
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val move = { lat: Double, lng: Double ->
                val location = LatLng(lat, lng)
                val defaultMarker =
                    MarkerOptions()
                        .draggable(true)
                        .position(location)
                map.addMarker(defaultMarker)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
            }

            LocationServices
                .getFusedLocationProviderClient(context)
                .lastLocation
                .addOnSuccessListener { location ->
                    move(location.latitude, location.longitude)
                    addressItems.value = getAddress(location.latitude, location.longitude)
                }
                .addOnFailureListener {
                    move(defaultLat, defaultLng)
                    addressItems.value = getAddress(defaultLat, defaultLng)
                }
        }
    }

    private fun getAddress(latitude: Double, longitude: Double): List<String> {
        val locationAddress = try {
            Geocoder(context)
                .getFromLocation(latitude, longitude, 1)
                .first()
        } catch (e: Exception) {
            state = state.copy(alert = context.getString(R.string.message_unknown_error))
            return emptyList()
        }
        val city = locationAddress.subAdminArea?.let {
            context.getString(R.string.ordering_address_city, locationAddress.subAdminArea)
        }
        val building = locationAddress.thoroughfare?.let {
            context.getString(R.string.ordering_address_street, locationAddress.thoroughfare)
        }
        val flat = locationAddress.subThoroughfare?.let {
            context.getString(R.string.ordering_address_street, locationAddress.subThoroughfare)
        }
        return listOfNotNull(city, building, flat)
    }

    private fun GoogleMap.locationFlow() = callbackFlow<LatLng> {
        setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(marker: Marker) {
                trySend(marker.position)
            }

            override fun onMarkerDragStart(marker: Marker) {
                /* do nothing */
            }

            override fun onMarkerDragEnd(marker: Marker) {
                /* do nothing */
            }
        })
        awaitClose { setOnMarkerDragListener(null) }
    }
}