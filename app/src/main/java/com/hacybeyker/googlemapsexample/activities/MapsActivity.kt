package com.hacybeyker.googlemapsexample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hacybeyker.googlemapsexample.R

//https://developers.google.com/maps/documentation/android-sdk/views?hl=es-419

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setMinZoomPreference(10f)
        mMap.setMaxZoomPreference(15f)

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val home = LatLng(-12.077246, -77.036668)
        mMap.addMarker(MarkerOptions().position(home).title("My Home").draggable(true))

        val cameraHomePosition = CameraPosition.Builder()
                .target(home)
                .zoom(15f)              //1-5-10-15-20
                .bearing(0f)            //rotacion - 0-365
                .tilt(90f)              //inclinacion, da efecto 3D - 90
                .build()

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(home))
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraHomePosition))

        //eventos
        mMap.setOnMapClickListener {
            Log.d("TAG", "Here - setOnMapClickListener: latitud: ${it.latitude} - longitude: ${it.longitude}")
        }

        mMap.setOnMapLongClickListener {
            Log.d("TAG", "Here - setOnMapLongClickListener: latitud: ${it.latitude} - longitude: ${it.longitude}")
        }

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(p0: Marker?) {
                Log.d("TAG", "Here - onMarkerDragEnd: ${p0?.position?.latitude} ${p0?.position?.longitude}")
            }

            override fun onMarkerDragStart(p0: Marker?) {
                Log.d("TAG", "Here - onMarkerDragStart: ${p0?.position?.latitude} ${p0?.position?.longitude}")
            }

            override fun onMarkerDrag(p0: Marker?) {
                Log.d("TAG", "Here - onMarkerDrag: ${p0?.position?.latitude} ${p0?.position?.longitude}")
            }
        })
    }
}