package com.sallyjayz.ranchid.generalinformation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sallyjayz.ranchid.databinding.FragmentGeneralInformationSearchBinding
import com.sallyjayz.ranchid.model.generalInformation.generateinformation.request.GenerateInformationPost
import com.sallyjayz.ranchid.model.generalInformation.search.SearchInformation
import com.sallyjayz.ranchid.recyclerview.generalInformation.SearchAdapter
import com.sallyjayz.ranchid.utils.ApiResponse
import com.sallyjayz.ranchid.viewmodel.CoroutinesErrorHandler
import com.sallyjayz.ranchid.viewmodel.generalinformation.GeneralInformationResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class GeneralInformationSearchFragment : Fragment() {

    private lateinit var binding: FragmentGeneralInformationSearchBinding
    private val generalInformationResponseViewModel: GeneralInformationResponseViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private var searchInfoList = ArrayList<SearchInformation>()
    private var selectedId: String? = null
    private var selectedType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGeneralInformationSearchBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.generalInfoFragment = this

//        searchInfo = ArrayList()
        binding.searchRecyclerview.setHasFixedSize(true)
        binding.searchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        searchAdapter = SearchAdapter(searchInfoList)
        binding.searchRecyclerview.adapter = searchAdapter


        searchName()

    }

    private fun searchName(){
        binding.searchView.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    generalInformationResponseViewModel.searchNameResponse.observe(viewLifecycleOwner) {
                        when(it){
                            is ApiResponse.Failure -> {
                                binding.errorTv.text = "Failure: ${it.errorMessage}"
                            }
                            ApiResponse.Loading -> {
                                binding.errorTv.text = "Loading"
                            }
                            is ApiResponse.Success -> {
                                binding.errorTv.text = "success"

                                val searchInfo = ArrayList<SearchInformation>()
                                for (searchName in it.data.data) {
                                    searchInfo.add(
                                        SearchInformation(searchName.custodian_type, searchName.id,
                                            searchName.other_names, searchName.surname)
                                    )
                                }
                                searchInfoList.clear()
                                searchInfoList.addAll(searchInfo)
                                searchAdapter.notifyDataSetChanged()


                                /*val searchInfo = ArrayList<SearchInformation>()
                                for (searchName in it.data.data) {
                                    searchInfo.add(
                                        SearchInformation(searchName.custodian_type, searchName.id,
                                            searchName.other_names, searchName.surname)
                                    )
                                }

                                searchAdapter = SearchAdapter(searchInfo)
                                Log.d("search adapter", "$searchInfo")
                                binding.searchRecyclerview.adapter = searchAdapter*/

                            }
                        }
                    }

                    searchAdapter.setOnClickListener(object : SearchAdapter.OnClickListener{
                        override fun onClickSearchInformation(
                            position: Int,
                            searchInformation: SearchInformation
                        ) {
                            binding.searchView.setQuery("${searchInformation.surname} ${searchInformation.other_names}", true)
                            selectedId = searchInformation.id.toString()
                            selectedType = searchInformation.custodian_type

                            Log.d("search adapter2", "$searchInformation")
                        }

                    })

                    generalInformationResponseViewModel.getSearchName(query.toString(), object:
                        CoroutinesErrorHandler{
                        override fun onError(message: String) {
                            binding.errorTv.text = "Error $message"
                        }

                    })

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            }
        )
    }

    /*private fun searchName(){
        binding.searchDropdown.setOnQueryTextListener(
            object: android.widget.SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    generalInformationResponseViewModel.searchNameResponse.observe(viewLifecycleOwner) {
                        when(it){
                            is ApiResponse.Failure -> {
                                binding.errorTv.text = "Failure: ${it.errorMessage}"
                            }
                            ApiResponse.Loading -> {
                                binding.errorTv.text = "Loading"
                            }
                            is ApiResponse.Success -> {
                                binding.errorTv.text = "success"

                                val nameSearched = ArrayList<String>()
                                for (searchName in it.data.data) {
                                    nameSearched.add("${searchName.surname} ${searchName.other_names}")
                                    searchInfo.add(
                                        SearchInformation(searchName.custodian_type, searchName.id,
                                            searchName.other_names, searchName.surname)
                                    )
                                }

                                searchNameAdapter =
//                                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, nameSearched)
//                                    binding.listview.adapter = searchNameAdapter
                                    ArrayAdapter(requireContext(), R.layout.search_dropdown_list_item, nameSearched)

                                if(::searchNameAdapter.isInitialized) {
                                    binding.listview.adapter = searchNameAdapter
                                    searchNameAdapter.notifyDataSetChanged()
                                }

                                *//*searchNameAdapter =
//                                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, nameSearched)
//                                    binding.listview.adapter = searchNameAdapter
                                    ArrayAdapter(requireContext(), R.layout.search_dropdown_list_item, nameSearched)
                                binding.listview.adapter = searchNameAdapter
                                searchNameAdapter.notifyDataSetChanged()*//*

                            }
                        }
                    }

                    binding.listview.onItemClickListener =
                        AdapterView.OnItemClickListener { parent, _, position, _ ->
                            selectedName = searchNameAdapter.getItem(position).toString()
                            selectedItemPosition = position
                            binding.searchDropdown.setQuery(selectedName, true)
                            binding.errorTv.text = "Selected Name: $selectedName"
                        }


                    generalInformationResponseViewModel.getSearchName(query.toString(), object:
                        CoroutinesErrorHandler{
                        override fun onError(message: String) {
                            binding.errorTv.text = "Error $message"
                        }

                    })

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
//                    searchNameAdapter.filter.filter(newText)
                    if(::searchNameAdapter.isInitialized) {
                        searchNameAdapter.filter.filter(newText)
                    }
                    return false
                }

            }
        )
    }*/


    fun generateInfoClicked() {

        if (selectedId.isNullOrEmpty() || selectedType.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Search and Select Livestock Owner or Keeper", Toast.LENGTH_SHORT).show()
        } else {
            generalInformationResponseViewModel.generateInformationResponse.observe(viewLifecycleOwner) {
                when(it) {
                    is ApiResponse.Failure -> {

                    }
                    ApiResponse.Loading -> {

                    }
                    is ApiResponse.Success -> {

                        val action = GeneralInformationSearchFragmentDirections
                            .actionGeneralInformationSearchFragmentToGeneralInformationDetailFragment(
                                it.data.data.custodian,
                                it.data.data.ref_id,
                                selectedType.toString()
                            )
                        findNavController().navigate(action)
                    }
                }
            }

            generalInformationResponseViewModel.generateInformationPost(
                GenerateInformationPost(
                    selectedId.toString(),
                    selectedType.toString()
                ),
                object: CoroutinesErrorHandler {
                    override fun onError(message: String) {
//                    TODO("Not yet implemented")
                    }

                }
            )
        }
    }

}