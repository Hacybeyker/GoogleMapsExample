package com.hacybeyker.googlemapsexample.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.hacybeyker.googlemapsexample.R
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var mAddress: List<Address>
    private lateinit var mGeocoder: Geocoder
    private lateinit var mMarketOptions: MarkerOptions


    private lateinit var currentLocation: Location
    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView = view.findViewById(R.id.mapView)
        mMapView.let {
            it.onCreate(null)
            it.onResume()
            mMapView.getMapAsync(this)
        }

        floatingActionButton.setOnClickListener {
            if (isGpsEnabled())
                showInfoAlert()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {

            loadStyleMap(it)
            mGoogleMap = it
            locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager


              if (ActivityCompat.checkSelfPermission(
                     context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            mGoogleMap.isMyLocationEnabled = true
            mGoogleMap.setOnMyLocationButtonClickListener(this)
            //mGoogleMap.uiSettings.isMyLocationButtonEnabled = false
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L,
                0F,
                object : LocationListener {
                    override fun onLocationChanged(p0: Location) {
                        Log.d(
                            "TAG",
                            "Here - onLocationChanged: latitud: ${p0.latitude}, lopngitud: ${p0.longitude}"
                        )

                        val mAddress = mGeocoder.getFromLocation(p0.latitude, p0.longitude, 20)
                        val address = mAddress.get(0).getAddressLine(0)
                        val city = mAddress.get(0).locality
                        mGoogleMap.addMarker(
                            MarkerOptions()
                                .title("$address - $city")
                                .position(LatLng(p0.latitude, p0.longitude))
                        )
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        Log.d("TAG", "Here - onStatusChanged: ")
                    }

                    override fun onProviderEnabled(provider: String) {
                        Log.d("TAG", "Here - onProviderEnabled: ")
                    }

                    override fun onProviderDisabled(provider: String) {
                        Log.d("TAG", "Here - onProviderDisabled: ")
                    }
                })

            val place = LatLng(-12.077246, -77.036668)
            val placeTwo = LatLng(-12.093670, -77.034889)
            val placeThree = LatLng(-12.102747, -77.030659)
            val placeFour = LatLng(-12.093528, -77.025400)
            val placeFive = LatLng(-12.043412, -76.973056)
            val placeSix = LatLng(-12.072742, -76.998377)
            val placeSeven = LatLng(-8.107652, -79.027402)
            mGoogleMap.addMarker(MarkerOptions().position(place).title("Home").draggable(true))
            mGoogleMap.addMarker(MarkerOptions().position(placeTwo).title("Home").draggable(true))
            mGoogleMap.addMarker(MarkerOptions().position(placeThree).title("Home").draggable(true))
            mGoogleMap.addMarker(MarkerOptions().position(placeFour).title("Home").draggable(true))
            mGoogleMap.addMarker(MarkerOptions().position(placeFive).title("Home").draggable(true))
            mGoogleMap.addMarker(MarkerOptions().position(placeSix).title("Home").draggable(true))
            mGoogleMap.addMarker(MarkerOptions().position(placeSeven).title("Home").draggable(true))

            //mGoogleMap.addMarker(MarkerOptions().position(place).title("Home").draggable(true))
            /*mGoogleMap.setMinZoomPreference(10f)
            mGoogleMap.setMaxZoomPreference(15f)*/
            val cameraPosition = CameraPosition.Builder()
                .target(place)
                .zoom(15f)
                .bearing(0f)
                .tilt(0f)
                .build()
            //mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            val builder = LatLngBounds.Builder()
            builder.include(place)
            builder.include(placeTwo)
            builder.include(placeThree)
            builder.include(placeFour)
            builder.include(placeFive)
            builder.include(placeSix)
            builder.include(placeSeven)
            val bounds = builder.build()
            val pading = 150
            val algo = CameraUpdateFactory.newLatLngBounds(bounds, pading)
            mGoogleMap.moveCamera(algo)

            /*mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(place))
            val zoom = CameraUpdateFactory.zoomTo(15f)*/

            mMarketOptions = MarkerOptions()
            mMarketOptions.position(place)
            mMarketOptions.title("Mi Marcador")
            mMarketOptions.snippet("Esto es una caja de texto donde modificar datos")
            //mMarketOptions.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on))
            mMarketOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_avengers))
            mMarketOptions.draggable(true)
            mGoogleMap.addMarker(mMarketOptions)

            //mGoogleMap.animateCamera(zoom)
            mGoogleMap.setOnMarkerDragListener(this)
            mGeocoder = Geocoder(context, Locale.getDefault())
        }
    }

    private fun loadStyleMap(googleMap: GoogleMap) {
        val success =
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.maps))
    }

    override fun onMarkerDragEnd(market: Marker?) {
        market?.position?.let {
            market.showInfoWindow()
            val latitude = it.latitude
            val longitude = it.longitude
            Log.d("TAG", "Here onMarkerDragEnd: latitude: $latitude - longitude: $longitude")
            mAddress = mGeocoder.getFromLocation(latitude, longitude, 20)
            for (item in mAddress) {
                val address = item.getAddressLine(0)
                val city = item.locality
                val state = item.adminArea
                val country = item.countryName
                val postalCode = item.postalCode
                Log.d("TAG", "Here - address: $address")
                Log.d("TAG", "Here - city: $city")
                Log.d("TAG", "Here - city: $address")
                Log.d("TAG", "Here - state: $state")
                Log.d("TAG", "Here - country: $country")
                Log.d("TAG", "Here - postalCode: $postalCode")
                Log.d("TAG", "Here =======================================")
                market.snippet = "$address"
            }

        }
    }

    override fun onMarkerDragStart(marker: Marker?) {
        marker?.hideInfoWindow()
    }

    override fun onMarkerDrag(market: Marker?) {

    }

    private fun isGpsEnabled(): Boolean {
        val gpsSignal =
            Settings.Secure.getInt(context?.contentResolver, Settings.Secure.LOCATION_MODE)
        return gpsSignal == 0
    }

    private fun showInfoAlert() {
        AlertDialog.Builder(context)
            .setTitle("GPS Signal")
            .setMessage("You don't have GPS signal enable. Would you like to enable the GPD signal now?")
            .setPositiveButton("OK") { p0, p1 ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("CANCEL", null)
            .show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Log.d("TAG", "Here - onMyLocationButtonClick: ")
        return false
    }


}