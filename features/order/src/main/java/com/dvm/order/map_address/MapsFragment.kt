package com.dvm.order.map_address

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dvm.order.R
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
internal class MapsFragment : Fragment(R.layout.fragment_maps) {

    private val viewModel: MapViewModel by viewModels()

    private val requestPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                viewModel.onLocationPermissionGranted()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestLocationPermission()

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(viewModel.callback)

        val textViewAddress = view.findViewById<TextView>(R.id.textAddress)
        view.findViewById<Button>(R.id.buttonComplete).setOnClickListener {
            viewModel.onButtonCompleteClick()
        }

        viewModel
            .address()
            .onEach { textViewAddress.text = it }
            .launchIn(lifecycleScope)

        viewModel
            .error
            .onEach { message ->
                AlertDialog
                    .Builder(requireContext())
                    .setMessage(message)
                    .setPositiveButton(R.string.common_ok, null)
                    .show()
            }
            .launchIn(lifecycleScope)
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}