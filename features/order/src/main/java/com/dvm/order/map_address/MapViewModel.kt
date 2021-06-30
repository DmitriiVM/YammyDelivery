package com.dvm.order.map_address

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.order.R
import com.dvm.utils.getErrorMessage
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
internal class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    private var googleMap: GoogleMap? = null

    private val addressItems = savedState.getLiveData("address_items", emptyList<String>())

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        moveToLocation()

        googleMap
            .locationFlow()
            .distinctUntilChanged()
            .debounce(500)
            .catch { throwable ->
                _error.send(throwable.getErrorMessage(context))
            }
            .onEach { latLng ->
                addressItems.value = getAddress(latLng.latitude, latLng.longitude)
            }
            .launchIn(viewModelScope)
    }

    fun address() =
        addressItems
            .asFlow()
            .distinctUntilChanged()
            .map { it.joinToString(", ") }
            .flowOn(Dispatchers.IO)

    fun onLocationPermissionGranted() {
        moveToLocation()
    }

    @SuppressLint("MissingPermission")
    private fun moveToLocation(
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
                googleMap?.addMarker(defaultMarker)
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
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
            _error.trySend(context.getString(R.string.message_unknown_error))
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

    fun onButtonCompleteClick() {
        if (addressItems.value?.size == 3) {
            navigator.goTo(
                Destination.BackToOrdering(
                    addressItems.value.orEmpty().joinToString(", ")
                )
            )
        } else {
            _error.trySend(context.getString(R.string.ordering_address_error))
        }
    }

    private fun GoogleMap.locationFlow() = callbackFlow<LatLng> {
        setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(marker: Marker) {
                trySend(marker.position)
            }

            override fun onMarkerDragStart(p0: Marker) {
                /*do nothing*/
            }

            override fun onMarkerDragEnd(p0: Marker) {
                /*do nothing*/
            }
        })
        awaitClose { setOnMarkerDragListener(null) }
    }
}