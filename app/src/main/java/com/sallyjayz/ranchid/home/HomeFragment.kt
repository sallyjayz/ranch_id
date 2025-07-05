package com.sallyjayz.ranchid.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sallyjayz.ranchid.LoginActivity
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentHomeBinding
import com.sallyjayz.ranchid.home.activities.ViewPagerAdapter
import com.sallyjayz.ranchid.model.register.packinglist.packinglist.AllPackingList
import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTag
import com.sallyjayz.ranchid.model.unusedpassport.UnusedPassport
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.*
import com.sallyjayz.ranchid.viewmodel.packinglist.LivestockDataViewModel
import com.sallyjayz.ranchid.viewmodel.packinglist.response.ScanPackingListResponseViewModel
import com.sallyjayz.ranchid.viewmodel.register.*
import com.sallyjayz.ranchid.viewmodel.register.response.*
import com.sallyjayz.ranchid.viewmodel.vet.AnimalTypeWithVaccineViewModel
import com.sallyjayz.ranchid.viewmodel.vet.TreatmentTypeViewModel
import com.sallyjayz.ranchid.viewmodel.vet.VetDashboardActivitiesViewModel
import com.sallyjayz.ranchid.viewmodel.vet.response.AnimalTypeWithVaccineResponseViewModel
import com.sallyjayz.ranchid.viewmodel.vet.response.TreatmentTypeResponseViewModel
import com.sallyjayz.ranchid.viewmodel.vet.response.VetDashboardActivitiesResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*val periodArray = arrayOf(
    "Today",
    "This Week",
    "This Month"
)*/

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private val tokenViewModel: TokenViewModel by activityViewModels()
//    private val authViewModel: AuthViewModel by activityViewModels()
//    private val farmLocationViewModel: FarmLocationViewModel by viewModels()
//    private val farmLocationResponseViewModel: FarmLocationResponseViewModel by viewModels()
    private val farmLocationWithStateViewModel: FarmLocationWithStateViewModel by viewModels()
    private val farmLocationWithStateResponseViewModel: FarmLocationWithStateResponseViewModel by viewModels()
    private val stateViewModel: StateViewModel by viewModels()
    private val lgaViewModel: LgaViewModel by viewModels()
    private val stateResponseViewModel: StateResponseViewModel by viewModels()
    private val lgaResponseViewModel: LgaResponseViewModel by viewModels()
    private val allOwnerResponseViewModel: AllOwnerResponseViewModel by viewModels()
    private val allOwnerViewModel: AllOwnerViewModel by viewModels()
    private val allKeeperResponseViewModel: AllKeeperResponseViewModel by viewModels()
    private val allKeeperViewModel: AllKeeperViewModel by viewModels()
    private val dashboardActivitiesViewModel: DashboardActivitiesViewModel by viewModels()
    private val dashboardActivitiesResponseViewModel: DashboardActivitiesResponseViewModel by viewModels()
    private val unusedPassportViewModel: UnusedPassportViewModel by viewModels()
    private val unusedPassportResponseViewModel: UnusedPassportResponseViewModel by viewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by activityViewModels()
    private val animalTypeViewModel: AnimalTypeViewModel by viewModels()
    private val animalBreedViewModel: AnimalBreedViewModel by viewModels()
    private val animalTypeResponseViewModel: AnimalTypeResponseViewModel by viewModels()
    private val animalBreedTypeResponseViewModel: AnimalBreedResponseViewModel by viewModels()
    private val unusedEnumeratorTagResponseViewModel: UnusedEnumeratorTagResponseViewModel by viewModels()
    private val unusedEnumeratorTagViewModel: UnusedEnumeratorTagViewModel by viewModels()
    private val usedEnumeratorTagResponse: UsedEnumeratorTagResponseViewModel by viewModels()
    private val usedEnumeratorTagViewModel: UsedEnumeratorTagViewModel by viewModels()
    private val scanPackingListResponseViewModel: ScanPackingListResponseViewModel by viewModels()
    private val livestockDataViewModel: LivestockDataViewModel by viewModels()
    private val vetDashboardActivitiesViewModel: VetDashboardActivitiesViewModel by viewModels()
    private val vetDashboardActivitiesResponseViewModel: VetDashboardActivitiesResponseViewModel by viewModels()
    private val animalTypeWithVaccineViewModel: AnimalTypeWithVaccineViewModel by viewModels()
    private val animalTypeWithVaccineResponseViewModel: AnimalTypeWithVaccineResponseViewModel by viewModels()
    private val treatmentTypeViewModel: TreatmentTypeViewModel by viewModels()
    private val treatmentTypeResponseViewModel: TreatmentTypeResponseViewModel by viewModels()

    private var token: String? = null
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.homeFragment = this

        mainMenuSetup()

        viewPagerAdapter = ViewPagerAdapter(this)

//        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

        viewPager = binding.homePager
        viewPager.adapter = viewPagerAdapter

        val tabLayout = binding.homeTablayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = periodArray[position]

            when (position) {
                0 -> {
                    tab.text = getString(R.string.today)
                }
                1 -> {
                    tab.text = getString(R.string.this_week)
                }
                2 -> {
                    tab.text = getString(R.string.this_month)
                }
            }

        }.attach()

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            this.token = token
            if (token == null){
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }

        }

        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            this.username = username
            binding.userName.text = username

        }

        /*tokenViewModel.name.observe(viewLifecycleOwner) { name ->
            binding.headerName.text = name
        }

        tokenViewModel.userEmail.observe(viewLifecycleOwner) { email ->
            binding.headerEmail.text = email
        }

        tokenViewModel.userRole.observe(viewLifecycleOwner) { role ->
            binding.headerRole.text = role
        }

        *//*tokenViewModel.userPhoto.observe(viewLifecycleOwner) { photo ->
            Glide.with(requireContext())
                .load(photo)
                .override(70, 70)
                .centerCrop()
                .into(binding.headerImage)
        }*/

        networkStatusViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                MyState.Fetched -> {
                    binding.offlineTv.isVisible = false
//                    insertFarmLocation()
                    insertFarmLocationWithState()
                    insertStateAndLGA()
                    insertAllOwnersAndKeepersFarmLocation()
                    insertDashboardActivity()
                    insertUnusedPassport()
                    insertAnimalTypeAndBreed()
                    insertUnusedEnumeratorTag()
                    insertUsedEnumeratorTag()
                    insertPackingList()
                    insertVetDashboardActivity()
                    insertVetAnimalTypeWithVaccine()
                    insertVetTreatmentType()
                }
                MyState.Error -> {
                    binding.offlineTv.isVisible = true
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        /*insertFarmLocation()
        insertStateAndLGA()
        insertAllOwnersAndKeepersFarmLocation()
        insertDashboardActivity()
        insertUnusedPassport()*/

        /*binding.menuLogout.setOnClickListener {
            tokenViewModel.deleteToken()
            activity?.finish()
        }*/

        /*if (tokenViewModel.loginRole.value.equals("ENUMERATOR")) {
            binding.fab.isVisible = false
        } else if (tokenViewModel.loginRole.value.equals("vet_doctor")){
            binding.fab.isVisible = true
        }*/

    }

    fun vaccinateLivestock() {
        val action = HomeFragmentDirections
            .actionHomeFragmentToVaccinateLivestockOneFragment()
        findNavController().navigate(action)
    }

    private fun mainMenuSetup() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_main_menu -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToHomeMenuBottomSheet()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    /*private fun insertFarmLocation() {
        farmLocationResponseViewModel.farmLocationResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = getString(R.string.farm_location_download_failed)
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
//                        Log.d("Home Fragment", "${it.data.farmLocation}")
                        farmLocationViewModel.deleteAllFarmLocation()
                        farmLocationViewModel.insertLocations(it.data.farmLocation)
                        *//*it.data.farmLocation.forEach{
                            farmLocationViewModel.insertLocations(it)
                        }
                        for(location in it.data.farmLocation) {
                            farmLocationViewModel.insertLocations(location)
                        }*//*
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }

            }
        }

        farmLocationResponseViewModel.getLocation(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
//                binding.menuBtn.isClickable = true
            }
        })
    }*/

    private fun insertFarmLocationWithState() {
        farmLocationWithStateResponseViewModel.farmLocationWithStateResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.errorTv.text = getString(R.string.farm_location_download_failed)
                }
                ApiResponse.Loading -> {
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }
                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
//                        Log.d("Home Fragment", "${it.data.farmLocationWithState}")
                        farmLocationWithStateViewModel.deleteAllFarmLocationWithState()
                        farmLocationWithStateViewModel.insertLocationsWithStates(it.data.farmLocationWithState)

                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                }

            }
        }

        farmLocationWithStateResponseViewModel.getLocationWithState(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home Farm Location", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
//                binding.menuBtn.isClickable = true
            }
        })
    }

    private fun insertStateAndLGA() {
        stateResponseViewModel.stateResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = getString(R.string.state_download_failed)
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }
                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        stateViewModel.deleteAllStates()
                        stateViewModel.insertStates(it.data.state)
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        stateResponseViewModel.getState(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home State", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
//                binding.menuBtn.isClickable = true
            }
        })


       /* LGA*/

        lgaResponseViewModel.lgaResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = getString(R.string.lga_download_failed)
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }
                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        lgaViewModel.deleteAllLgas()
                        for (data in it.data.data){
                            lgaViewModel.insertLgas(data.locals)
//                            Log.d("Owner1", "Id: ${data.locals}")
                        }
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        lgaResponseViewModel.getLGA(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home LGA", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
//                binding.menuBtn.isClickable = true
            }
        })
    }

    private fun insertAllOwnersAndKeepersFarmLocation() {
        allOwnerResponseViewModel.allOwnerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = getString(R.string.owner_download_failed)
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }
                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        allOwnerViewModel.deleteAllOwners()
                        allOwnerViewModel.insertAllOwners(it.data.data.allOwnersList)
                        Log.d("Home Fragment1", "${it.data.data.allOwnersList}")
                        /* old response from server
                        allOwnerViewModel.insertAllOwners(it.data.record.allOwnersList)
                        Log.d("Home Fragment1", "${it.data.record.allOwnersList}")
                        */
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        allOwnerResponseViewModel.getAllOwner(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home All Owner", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
//                binding.menuBtn.isClickable = true
            }
        })

        /*All Keeper*/
        allKeeperResponseViewModel.allKeeperResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = getString(R.string.keeper_download_failed)
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }
                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        allKeeperViewModel.deleteAllKeepers()
                        allKeeperViewModel.insertAllKeepers(it.data.allKeepersList)
                        /*old response from server
                        allKeeperViewModel.insertAllKeepers(it.data.record.allKeepersList)
                        */
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        allKeeperResponseViewModel.getAllKeeper(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home All Keeper", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
//                binding.menuBtn.isClickable = true
            }
        })
    }

    private fun insertDashboardActivity() {
        dashboardActivitiesResponseViewModel.activitiesResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = getString(R.string.dashboard_activity_download_failed)
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }
                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        dashboardActivitiesViewModel.deleteExit()
                        dashboardActivitiesViewModel.deleteKeeper()
                        dashboardActivitiesViewModel.deleteOwner()
                        dashboardActivitiesViewModel.deleteTagged()

                        dashboardActivitiesViewModel.insertExit(it.data.livestock.exit)
                        dashboardActivitiesViewModel.insertKeeper(it.data.keepers)
                        dashboardActivitiesViewModel.insertOwner(it.data.owners)
                        dashboardActivitiesViewModel.insertTagged(it.data.livestock.tagged)

//                        Log.d("Fragment Keeper", "${it.data.keepers}")
//                        Log.d("Fragment Owner", "${it.data.owners}")
//                        Log.d("Fragment Exit", "${it.data.livestock.exit}")
//                        Log.d("Fragment Tagged", "${it.data.livestock.tagged}")


                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        /*tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                dashboardActivitiesResponseViewModel.getActivities(username,
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                            binding.errorTv.text = "Error! $message"
                            binding.offlineTv.isVisible = true
                            binding.homeFragmentProgress.isVisible = false
                            binding.homeFragmentConstraintLayout.alpha = 1.0F
//                            binding.menuBtn.isClickable = true
                        }
                    })
            }
        }*/

        dashboardActivitiesResponseViewModel.getActivities(username.toString().lowercase(),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                    Log.d("Home Dashboard", message)
                    binding.offlineTv.isVisible = true
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                            binding.menuBtn.isClickable = true
                }
            })
    }

    private fun insertUnusedPassport() {
        unusedPassportResponseViewModel.unusedPassportResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = getString(R.string.unused_passport_download_failed)
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        unusedPassportViewModel.deleteAllUnusedPassport()

                        var unusedPassport: UnusedPassport
                        for (passportid in it.data.data) {
                            unusedPassport = UnusedPassport(passportid)
                            unusedPassportViewModel.insertUnusedPassport(unusedPassport)
                        }
                    }



                    /*CoroutineScope(Dispatchers.IO).launch {

                        unusedPassportViewModel.readAllUnusedPassport.observe(viewLifecycleOwner) {unUsedPassportList ->
                            if (unUsedPassportList != null) {
                                unusedPassportViewModel.deleteAllUnusedPassport()
                            }
                        }

                        var unusedPassport: UnusedPassport
                        for (passportid in it.data.data) {
                            unusedPassport = UnusedPassport(passportid)
                            unusedPassportViewModel.insertUnusedPassport(unusedPassport)
//                            Log.d("Fragment UnusedPassport", "Id: ${unusedPassport.id}, " +
//                                    "PassportId: ${unusedPassport.passportId}")
                        }

//                        Log.d("Fragment List Data", "${it.data.data}")
//                        Log.d("Fragment Record", "${it.data.total_record}")
//                        Log.d("Fragment Count", "${it.data.total_count}")


                    }*/
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        /*tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                unusedPassportResponseViewModel.getUnusedPassport(username.lowercase(),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                            binding.errorTv.text = "Error! $message"
                            binding.offlineTv.isVisible = true
                            binding.homeFragmentProgress.isVisible = false
                            binding.homeFragmentConstraintLayout.alpha = 1.0F
//                            binding.menuBtn.isClickable = true
                            *//*if (binding.menu.isVisible) {
                                binding.homeFragmentConstraintLayout.alpha = 0.1F
                                binding.menuConstraintLayout.alpha = 1.0F
                            } else {
                                binding.homeFragmentConstraintLayout.alpha = 1.0F
                            }*//*
                        }
                    })
            }
        }*/

        unusedPassportResponseViewModel.getUnusedPassport(username.toString().lowercase(),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                    Log.d("Home Unused Passport", message)
                    binding.offlineTv.isVisible = true
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                            binding.menuBtn.isClickable = true
                    /*if (binding.menu.isVisible) {
                        binding.homeFragmentConstraintLayout.alpha = 0.1F
                        binding.menuConstraintLayout.alpha = 1.0F
                    } else {
                        binding.homeFragmentConstraintLayout.alpha = 1.0F
                    }*/
                }
            })
    }

    private fun insertAnimalTypeAndBreed() {

        animalTypeResponseViewModel.animalTypeResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.errorTv.text = "Failed to download Animal Type data from server"
                }
                ApiResponse.Loading -> {
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        animalTypeViewModel.deleteAllAnimalType()
                        animalTypeViewModel.insertAnimalType(it.data.data)
                    }

                    /*CoroutineScope(Dispatchers.IO).launch {
                        try {
                            animalTypeViewModel.deleteAllAnimalType()
                            animalTypeViewModel.insertAnimalType(it.data.data)
                        }catch(e: Exception) {
                            Toast.makeText(requireContext(), getString(R.string.something_went_wrong) + "${e.message}",
                                Toast.LENGTH_LONG).show()
                        }
                    }*/

                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        animalTypeResponseViewModel.getAnimalType(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home Animal Type", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
//                binding.menuBtn.isClickable = true
            }

        })


        /* Animal Breed*/

        animalBreedTypeResponseViewModel.animalBreedResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = "Failed to download Animal Breed data from server"
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        animalBreedViewModel.deleteAllBreed()
                        animalBreedViewModel.insertAnimalBreed(it.data.data)
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        animalBreedTypeResponseViewModel.getAnimalBreed(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home Animal Breed", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
//                binding.menuBtn.isClickable = true
            }
        })

    }

    private fun insertUnusedEnumeratorTag() {
        unusedEnumeratorTagResponseViewModel.allUnusedEnumeratorTag.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
//                    binding.errorTv.text = "Code: ${it.code}, ${it.errorMessage}"
                    binding.errorTv.text = "Failed to download UnusedEnumeratorTag from server"
                }
                ApiResponse.Loading -> {
//                    binding.errorTv.text = "Loading"
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
//                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        unusedEnumeratorTagViewModel.deleteAllUnusedEnumeratorTag()
                        var allUnusedEnumeratorTag: AllUnusedEnumeratorTag
                        for (unusedEnumeratorTag in it.data.data.allUnusedEnumeratorTag) {
                            allUnusedEnumeratorTag = AllUnusedEnumeratorTag(
                                unusedEnumeratorTag.id,
                                unusedEnumeratorTag.batch_id,
                                unusedEnumeratorTag.date_tagged,
                                unusedEnumeratorTag.enumerator,
                                unusedEnumeratorTag.first_four,
                                unusedEnumeratorTag.print_status,
                                unusedEnumeratorTag.qr_code,
                                unusedEnumeratorTag.rand_code,
                                unusedEnumeratorTag.second_four,
                                unusedEnumeratorTag.serial_number,
                                unusedEnumeratorTag.tag_id,
                                unusedEnumeratorTag.tag_type,
                                unusedEnumeratorTag.tagging_contractor,
                                "Available"
                            )
                            unusedEnumeratorTagViewModel.insertUnusedEnumeratorTag(allUnusedEnumeratorTag)
                        }

                        /*unusedPassportViewModel.deleteAllUnusedPassport()

                        var unusedPassport: UnusedPassport
                        for (passportid in it.data.data) {
                            unusedPassport = UnusedPassport(0, passportid)
                            unusedPassportViewModel.insertUnusedPassport(unusedPassport)
//                            Log.d("Fragment UnusedPassport", "Id: ${unusedPassport.id}, " +
//                                    "PassportId: ${unusedPassport.passportId}")
                        }*/

                        Log.d("Unused Enumerator Tags", "${it.data.data.allUnusedEnumeratorTag}")


                    }
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                    binding.menuBtn.isClickable = true
                }
            }
        }

        /*tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                unusedEnumeratorTagResponseViewModel.getAllUnusedEnumeratorTag(username.lowercase(),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                            binding.errorTv.text = "Error! $message"
                            binding.offlineTv.isVisible = true
                            binding.homeFragmentProgress.isVisible = false
                            binding.homeFragmentConstraintLayout.alpha = 1.0F
//                            binding.menuBtn.isClickable = true
                            *//*if (binding.menu.isVisible) {
                                binding.homeFragmentConstraintLayout.alpha = 0.1F
                                binding.menuConstraintLayout.alpha = 1.0F
                            } else {
                                binding.homeFragmentConstraintLayout.alpha = 1.0F
                            }*//*
                        }
                    })
            }
        }*/

        unusedEnumeratorTagResponseViewModel.getAllUnusedEnumeratorTag(username.toString().lowercase(),
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                    Log.d("Home UnusedEnumeratorTag", message)
                    binding.offlineTv.isVisible = true
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
//                            binding.menuBtn.isClickable = true
                    /*if (binding.menu.isVisible) {
                        binding.homeFragmentConstraintLayout.alpha = 0.1F
                        binding.menuConstraintLayout.alpha = 1.0F
                    } else {
                        binding.homeFragmentConstraintLayout.alpha = 1.0F
                    }*/
                }
            })
    }

    private fun insertUsedEnumeratorTag() {
        usedEnumeratorTagResponse.allUsedEnumeratorTagResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.errorTv.text = "Failed to download UsedEnumeratorTag from server"
                }
                ApiResponse.Loading -> {
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        usedEnumeratorTagViewModel.deleteUsedEnumeratorTag()

                        for (data in it.data.data.usedEnumeratorTag){
                            usedEnumeratorTagViewModel.insertUsedEnumeratorTag(data)
                        }

                    }

                    /*for (data in it.data.usedEnumeratorTag){
                        CoroutineScope(Dispatchers.IO).launch {
                            usedEnumeratorTagViewModel.deleteUsedEnumeratorTag()
                            usedEnumeratorTagViewModel.insertUsedEnumeratorTag(data)
                        }
//                        Log.d("Fragment UsedTags", "UsedTag: ${data.tag_id}")
                        *//*if (data.tag_id.contains(scannedTag.toString())) {
                            availableUsedTag = scannedTag.toString()
                        } else {
                            Log.d("Fragment getusedtag", "Tag not found ${data.tag_id}, ${scannedTag}")
                        }*//*

                    }*/
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                }
            }
        }

        /*tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                usedEnumeratorTagResponse.getAllUsedEnumeratorTag(username, object:
                    CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        binding.offlineTv.isVisible = true
                        binding.homeFragmentProgress.isVisible = false
                        binding.homeFragmentConstraintLayout.alpha = 1.0F
                    }

                })
            }
        }*/

        usedEnumeratorTagResponse.getAllUsedEnumeratorTag(username.toString().lowercase(), object:
            CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home Used Enumerator", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
            }

        })
    }

    private fun insertPackingList() {
        scanPackingListResponseViewModel.packingListResponse.observe(viewLifecycleOwner){ parkingItem ->
            when(parkingItem) {
                is ApiResponse.Failure -> {
                    binding.errorTv.text = "Failed to download Packing List"
                }
                ApiResponse.Loading -> {
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        livestockDataViewModel.deletePackingList()
                        var packingList: AllPackingList
                        for (data in parkingItem.data.data) {
                            for(tag in data.tags){
                                packingList = AllPackingList(
//                                    0,
                                    data.status,
                                    tag
                                )
                                livestockDataViewModel.insertPackingList(packingList)
                            }
                        }
                    }

                }
            }
        }

        /*tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                scanPackingListResponseViewModel.getParkingList(username, object:
                    CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        binding.offlineTv.isVisible = true
                        binding.homeFragmentProgress.isVisible = false
                        binding.homeFragmentConstraintLayout.alpha = 1.0F
                    }

                })
            }
        }*/

        scanPackingListResponseViewModel.getParkingList(username.toString().lowercase(), object:
            CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home packing list", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
            }

        })
    }

    private fun insertVetDashboardActivity() {

        vetDashboardActivitiesResponseViewModel.activitiesResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.errorTv.text = getString(R.string.dashboard_activity_download_failed)
                }
                ApiResponse.Loading -> {
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        vetDashboardActivitiesViewModel.deleteAppointment()
                        vetDashboardActivitiesViewModel.deleteVaccination()

                        vetDashboardActivitiesViewModel.insertDashboardAppointment(it.data.upcoming_appointments)
                        vetDashboardActivitiesViewModel.insertDashboardVaccination(it.data.vaccinations)
                    }

                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                }
            }
        }

        vetDashboardActivitiesResponseViewModel.getVetActivities(object: CoroutinesErrorHandler {
                override fun onError(message: String) {
//                    binding.errorTv.text = "Error! $message"
                    Log.d("Home vet dashboard", message)
                    binding.offlineTv.isVisible = true
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                }

            })
    }

    private fun insertVetAnimalTypeWithVaccine() {
        animalTypeWithVaccineResponseViewModel.animalTypeWithVaccineResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.errorTv.text = "Failed to download Animal Type With Vaccine data from server"
                }
                ApiResponse.Loading -> {
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        animalTypeWithVaccineViewModel.deleteAllAnimalTypeWithVaccine()
                        animalTypeWithVaccineViewModel.insertAnimalTypeWithVaccine(it.data.data)
                    }
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                }
            }
        }

        animalTypeWithVaccineResponseViewModel.getAnimalTypeWithVaccine(object : CoroutinesErrorHandler{
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home Animal Type Vaccine", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
            }

        })
    }

    private fun insertVetTreatmentType() {
        treatmentTypeResponseViewModel.treatmentTypeResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    binding.errorTv.text = "Failed to download Treatment Type from server"
                }
                ApiResponse.Loading -> {
                    binding.homeFragmentProgress.isVisible = true
                    binding.homeFragmentConstraintLayout.alpha = 0.5F
                }
                is ApiResponse.Success -> {
                    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                        throwable.printStackTrace()
                    }

                    CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                        treatmentTypeViewModel.deleteAllTreatmentType()
                        treatmentTypeViewModel.insertTreatmentType(it.data.data)
                    }
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                }
            }
        }

        treatmentTypeResponseViewModel.getTreatmentType(object : CoroutinesErrorHandler{
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                Log.d("Home Treatment Type", message)
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
            }

        })
    }




    fun showMenu() {
        /*val action = HomeFragmentDirections.actionHomeFragmentToHomeMenuBottomSheet()
        findNavController().navigate(action)*/
    }

    /*

    fun showMenu() {
        /*binding.menu.isVisible = true
        binding.homeFragmentConstraintLayout.alpha = 0.1F
        binding.menuConstraintLayout.alpha = 1.0F*/
        /*val modalBottomSheet = HomeMenuBottomSheet()
        modalBottomSheet.show(requireActivity().supportFragmentManager, HomeMenuBottomSheet.TAG)*/

    }


    fun closeMenu() {
        binding.menu.isVisible = false
        binding.homeFragmentConstraintLayout.alpha = 1.0F
    }

    fun showRegisterFragment() {
        val action = HomeFragmentDirections
            .actionHomeFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    fun showTagFragment() {
        val action = HomeFragmentDirections
            .actionHomeFragmentToTagFragment()
        findNavController().navigate(action)
    }

    fun showOfflineFragment() {
        val action = HomeFragmentDirections
            .actionHomeFragmentToOfflineFragment()
        findNavController().navigate(action)

    }

    fun showReportFragment() {
        val action = HomeFragmentDirections
            .actionHomeFragmentToReportFragment()
        findNavController().navigate(action)
    }

    fun showLogoutFragment() {
        tokenViewModel.deleteToken()
        activity?.finish()
    }*/
}