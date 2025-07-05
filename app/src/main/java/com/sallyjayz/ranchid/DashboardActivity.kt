package com.sallyjayz.ranchid

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.location.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sallyjayz.ranchid.viewmodel.AuthViewModel
import com.sallyjayz.ranchid.viewmodel.PermissionViewModel
import com.sallyjayz.ranchid.viewmodel.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: PermissionViewModel
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var authViewModel: AuthViewModel
//    private lateinit var livestockWithVetHistoryViewModel: LivestockWithHistoryViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var timeInterval: Long = TimeUnit.SECONDS.toMillis(60)
    private var fastestInterval: Long = TimeUnit.SECONDS.toMillis(30)
    private var maxWaitTime: Long = TimeUnit.MINUTES.toMillis(2)
    private var requestingLocationUpdates: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)
        viewModel = ViewModelProvider(this)[PermissionViewModel::class.java]
        tokenViewModel = ViewModelProvider(this)[TokenViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
//        livestockWithVetHistoryViewModel = ViewModelProvider(this)[LivestockWithHistoryViewModel::class.java]
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestingLocationUpdates = false
        createLocationCallback()
        createLocationRequest()

        val vetCouncilNumber = intent.getStringExtra("vetCouncilNumber")
        val userRole = intent.getStringExtra("userRole")
//        livestockWithVetHistoryViewModel.setVetNumber(vetCouncilNumber.toString())
        tokenViewModel.setLoginRole(userRole.toString())

        if (isPermissionGranted()) {
            requestingLocationUpdates = true
            viewModel.setPermission(isPermissionGranted())
//            getLocation()
        } else {
            checkPermissions()
        }

        val topBar: MaterialToolbar = findViewById(R.id.topAppBar)


        setSupportActionBar(topBar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        topBar.setNavigationOnClickListener {
            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.addLivestockLocationSuccessfulFragment ||
                destination.id == R.id.addLivestockLocationUnsuccessfulFragment ||
                destination.id == R.id.addLivestockOwnerSuccessfulFragment ||
                destination.id == R.id.addLivestockOwnerUnsuccessfulFragment ||
                destination.id == R.id.addLivestockKeeperSuccessfulFragment ||
                destination.id == R.id.addLivestockKeeperUnsuccessfulFragment ||
                destination.id == R.id.tagLivestockSuccessfulFragment ||
                destination.id == R.id.tagLivestockUnsuccessfulFragment ||
                destination.id == R.id.offlineOwnerSuccess ||
                destination.id == R.id.offlineKeeperSuccess ||
                destination.id == R.id.offlineTagLivestockSuccess ||
                destination.id == R.id.addPackingListUnsuccessfulFragment ||
                destination.id == R.id.addPackingListSuccessfulFragment ||
                destination.id == R.id.vaccinateLivestockSuccessfulFragment ||
                destination.id == R.id.vaccinateLivestockFailedFragment ||
                destination.id == R.id.livestockTreatmentSuccessFragment ||
                destination.id == R.id.livestockTreatmentFailedFragment

                ) {

                topBar.visibility = View.GONE

            } else {

                topBar.visibility = View.VISIBLE

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /*private fun menuSetup() {
        addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.dashboard_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_sync -> {
                        *//*TODO*//*
                        true
                    }
                    R.id.action_notification -> {
                        *//*TODO*//*
                        true
                    }
                    else -> false
                }
            }
        })
    }*/

    private fun isPermissionGranted(): Boolean {
        /*return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED*/

        /*if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false*/

        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA,
                ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST) {
            if (isPermissionGranted()) {
                requestingLocationUpdates = true
                viewModel.setPermission(isPermissionGranted())
//                getLocation()
                getLastLocation()
            } else {
//                MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_RanchID_Button_TextButton_Dialog)
                MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.permission_required))
                    .setMessage("This application needs to access the camera and location")
                    .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                        checkPermissions()
                    }
                    .setCancelable(false)
                    .create()
                    .apply {
                        setCanceledOnTouchOutside(false)
                        show()
                    }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkPermissions() {
        ActivityCompat.requestPermissions(
            this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    /*@SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (isPermissionGranted()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        list?.get(0)?.let { viewModel.setLongitude(it.longitude) }
                        list?.get(0)?.let { viewModel.setLatitude(it.latitude) }
                        list?.get(0)?.let { viewModel.setAddress(it.getAddressLine(0)) }
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            checkPermissions()
        }
    }*/

    @SuppressWarnings("MissingPermission")
    private fun getLastLocation() {
        if (isPermissionGranted()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            viewModel.setLongitude(location.longitude)
                            viewModel.setLatitude(location.latitude)
                        }
                    }
//                Log.d("Dashboard", "latitude: ${viewModel.latitude.value}, longitude: ${viewModel.longitude.value}")
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            checkPermissions()
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeInterval).apply {
//            setMinUpdateDistanceMeters(minimalDistance)
            setMinUpdateIntervalMillis(fastestInterval)
            setMaxUpdateDelayMillis(maxWaitTime)
//            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(false)
        }.build()

    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
//                currentLocation = locationResult.lastLocation
//                currentLocation?.let { viewModel.setLatitude(it.latitude) }
//                currentLocation?.let { viewModel.setLongitude(it.longitude) }
                for (location in locationResult.locations) {
                    if (location != null) {
                        viewModel.setLongitude(location.longitude)
                        viewModel.setLatitude(location.latitude)
                    }
                }
            }
        }
//        Log.d("Dashboard", "latitude: ${viewModel.latitude.value}, longitude: ${viewModel.longitude.value}")
    }

    @SuppressWarnings("MissingPermission")
    override fun onStart() {
        super.onStart()
        if (requestingLocationUpdates) fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())

    }

    override fun onStop() {
        super.onStop()
        requestingLocationUpdates = false
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val PERMISSION_REQUEST = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}