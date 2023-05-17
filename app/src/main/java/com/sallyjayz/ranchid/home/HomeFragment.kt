package com.sallyjayz.ranchid.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.sallyjayz.ranchid.HomeMenuBottomSheet
import com.sallyjayz.ranchid.LoginActivity
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.databinding.FragmentHomeBinding
import com.sallyjayz.ranchid.home.activities.ViewPagerAdapter
import com.sallyjayz.ranchid.model.unusedenumeratortag.all.AllUnusedEnumeratorTag
import com.sallyjayz.ranchid.model.unusedpassport.UnusedPassport
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.*
import com.sallyjayz.ranchid.viewmodel.register.*
import com.sallyjayz.ranchid.viewmodel.register.response.*
import dagger.hilt.android.AndroidEntryPoint
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
    private val farmLocationViewModel: FarmLocationViewModel by viewModels()
    private val farmLocationResponseViewModel: FarmLocationResponseViewModel by viewModels()
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

    private var token: String? = null

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
        binding.homeFragment = this

        viewPagerAdapter = ViewPagerAdapter(this)
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
                    insertFarmLocation()
                    insertStateAndLGA()
                    insertAllOwnersAndKeepersFarmLocation()
                    insertDashboardActivity()
                    insertUnusedPassport()
                    insertAnimalTypeAndBreed()
                    insertUnusedEnumeratorTag()
                }
                MyState.Error -> {
                    binding.offlineTv.isVisible = true
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
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

    }

    private fun insertFarmLocation() {
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
//                        Log.d("Home Fragment", "${it.data.farmLocation}")
                        farmLocationViewModel.deleteAllFarmLocation()
                        farmLocationViewModel.insertLocations(it.data.farmLocation)
                        /*it.data.farmLocation.forEach{
                            farmLocationViewModel.insertLocations(it)
                        }
                        for(location in it.data.farmLocation) {
                            farmLocationViewModel.insertLocations(location)
                        }*/
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }

            }
        }

        farmLocationResponseViewModel.getLocation(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
                binding.menuBtn.isClickable = true
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        stateViewModel.deleteAllStates()
                        stateViewModel.insertStates(it.data.state)
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }
            }
        }

        stateResponseViewModel.getState(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
                binding.menuBtn.isClickable = true
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        lgaViewModel.deleteAllLgas()
                        for (data in it.data.data){
                            lgaViewModel.insertLgas(data.locals)
//                            Log.d("Owner1", "Id: ${data.locals}")
                        }
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }
            }
        }

        lgaResponseViewModel.getLGA(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
                binding.menuBtn.isClickable = true
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        allOwnerViewModel.deleteAllOwners()
                        allOwnerViewModel.insertAllOwners(it.data.allOwnersList)
                        Log.d("Home Fragment1", "${it.data.allOwnersList}")
                        /* old response from server
                        allOwnerViewModel.insertAllOwners(it.data.record.allOwnersList)
                        Log.d("Home Fragment1", "${it.data.record.allOwnersList}")
                        */
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }
            }
        }

        allOwnerResponseViewModel.getAllOwner(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
                binding.menuBtn.isClickable = true
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        allKeeperViewModel.deleteAllKeepers()
                        allKeeperViewModel.insertAllKeepers(it.data.allKeepersList)
                        /*old response from server
                        allKeeperViewModel.insertAllKeepers(it.data.record.allKeepersList)
                        */
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }
            }
        }

        allKeeperResponseViewModel.getAllKeeper(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
                binding.menuBtn.isClickable = true
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
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
                    binding.menuBtn.isClickable = true
                }
            }
        }

        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                dashboardActivitiesResponseViewModel.getActivities(username,
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                            binding.errorTv.text = "Error! $message"
                            binding.offlineTv.isVisible = true
                            binding.homeFragmentProgress.isVisible = false
                            binding.homeFragmentConstraintLayout.alpha = 1.0F
                            binding.menuBtn.isClickable = true
                        }
                    })
            }
        }
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {

                        unusedPassportViewModel.deleteAllUnusedPassport()

                        var unusedPassport: UnusedPassport
                        for (passportid in it.data.data) {
                            unusedPassport = UnusedPassport(0, passportid)
                            unusedPassportViewModel.insertUnusedPassport(unusedPassport)
//                            Log.d("Fragment UnusedPassport", "Id: ${unusedPassport.id}, " +
//                                    "PassportId: ${unusedPassport.passportId}")
                        }

//                        Log.d("Fragment List Data", "${it.data.data}")
//                        Log.d("Fragment Record", "${it.data.total_record}")
//                        Log.d("Fragment Count", "${it.data.total_count}")


                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }
            }
        }

        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                unusedPassportResponseViewModel.getUnusedPassport(username.lowercase(),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                            binding.errorTv.text = "Error! $message"
                            binding.offlineTv.isVisible = true
                            binding.homeFragmentProgress.isVisible = false
                            binding.homeFragmentConstraintLayout.alpha = 1.0F
                            binding.menuBtn.isClickable = true
                            /*if (binding.menu.isVisible) {
                                binding.homeFragmentConstraintLayout.alpha = 0.1F
                                binding.menuConstraintLayout.alpha = 1.0F
                            } else {
                                binding.homeFragmentConstraintLayout.alpha = 1.0F
                            }*/
                        }
                    })
            }
        }
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        animalTypeViewModel.deleteAllAnimalType()
                        animalTypeViewModel.insertAnimalType(it.data.data)
                    }
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }
            }
        }

        animalTypeResponseViewModel.getAnimalType(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
                binding.menuBtn.isClickable = true
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        animalBreedViewModel.deleteAllBreed()
                        animalBreedViewModel.insertAnimalBreed(it.data.data)
                    }
//                    binding.errorTv.text = "Inserted"
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }
            }
        }

        animalBreedTypeResponseViewModel.getAnimalBreed(object: CoroutinesErrorHandler {
            override fun onError(message: String) {
//                binding.errorTv.text = "Error! $message"
                binding.offlineTv.isVisible = true
                binding.homeFragmentProgress.isVisible = false
                binding.homeFragmentConstraintLayout.alpha = 1.0F
                binding.menuBtn.isClickable = true
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
                    binding.menuBtn.isClickable = false
                }
                is ApiResponse.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        unusedEnumeratorTagViewModel.deleteAllUnusedEnumeratorTag()
                        var allUnusedEnumeratorTag: AllUnusedEnumeratorTag
                        for (unusedEnumeratorTag in it.data.allUnusedEnumeratorTag) {
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

                        Log.d("Unused Enumerator Tags", "${it.data.allUnusedEnumeratorTag}")


                    }
                    binding.homeFragmentProgress.isVisible = false
                    binding.homeFragmentConstraintLayout.alpha = 1.0F
                    binding.menuBtn.isClickable = true
                }
            }
        }

        tokenViewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                unusedEnumeratorTagResponseViewModel.getAllUnusedEnumeratorTag(username.lowercase(),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
//                            binding.errorTv.text = "Error! $message"
                            binding.offlineTv.isVisible = true
                            binding.homeFragmentProgress.isVisible = false
                            binding.homeFragmentConstraintLayout.alpha = 1.0F
                            binding.menuBtn.isClickable = true
                            /*if (binding.menu.isVisible) {
                                binding.homeFragmentConstraintLayout.alpha = 0.1F
                                binding.menuConstraintLayout.alpha = 1.0F
                            } else {
                                binding.homeFragmentConstraintLayout.alpha = 1.0F
                            }*/
                        }
                    })
            }
        }
    }

    fun showMenu() {
        /*binding.menu.isVisible = true
        binding.homeFragmentConstraintLayout.alpha = 0.1F
        binding.menuConstraintLayout.alpha = 1.0F*/
        /*val modalBottomSheet = HomeMenuBottomSheet()
        modalBottomSheet.show(requireActivity().supportFragmentManager, HomeMenuBottomSheet.TAG)*/
        val action = HomeFragmentDirections.actionHomeFragmentToHomeMenuBottomSheet()
        findNavController().navigate(action)
    }

    /*fun closeMenu() {
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