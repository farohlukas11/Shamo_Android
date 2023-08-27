package com.faroh.shamoandroid.view.home.all_shoes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.ui.ListPopularProductAdapter
import com.faroh.shamoandroid.core.ui.ListProductAdapter
import com.faroh.shamoandroid.core.utils.ToastUtils.showCustomToast
import com.faroh.shamoandroid.databinding.FragmentAllShoesBinding
import com.faroh.shamoandroid.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllShoesFragment : Fragment() {

    private lateinit var allShoesBinding: FragmentAllShoesBinding
    private val allShoesViewModel: AllShoesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        allShoesBinding = FragmentAllShoesBinding.inflate(layoutInflater)
        return allShoesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allShoesViewModel.getAllProduct().observe(requireActivity()) { response ->
            when (response) {
                is Resource.Loading -> allShoesBinding.progressBar.visibility =
                    View.VISIBLE

                is Resource.Success -> {
                    allShoesBinding.progressBar.visibility =
                        View.GONE

                    val dataResponse = response.data

                    dataResponse?.let {
                        if (it.isNotEmpty()) {
                            allShoesBinding.rvPopularProduct.layoutManager =
                                LinearLayoutManager(
                                    requireActivity(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )

                            val listPopular = ArrayList<DataItem>()
                            for (popularProduct in it.filter { data -> if (data.price != null) data.price <= 100 else false }) {
                                listPopular.add(popularProduct)
                            }

                            if (listPopular.isNotEmpty()) {
                                val listPopularProductAdapter =
                                    ListPopularProductAdapter(listPopular) { data ->
                                        val intentDetail =
                                            Intent(requireActivity(), DetailActivity::class.java)
                                        intentDetail.putExtra(DetailActivity.DETAIL_DATA_ITEM, data)
                                        startActivity(intentDetail)
                                    }
                                allShoesBinding.rvPopularProduct.adapter = listPopularProductAdapter
                            }
                        }

                    }
                }

                is Resource.Error -> {
                    Toast(requireActivity()).showCustomToast(
                        true,
                        response.message.toString(),
                        requireActivity()
                    )
                }
            }
        }

        allShoesViewModel.getAllProduct().observe(requireActivity()) { response ->
            when (response) {
                is Resource.Loading -> allShoesBinding.progressBar.visibility =
                    View.VISIBLE

                is Resource.Success -> {
                    allShoesBinding.progressBar.visibility = View.GONE

                    val dataResponse = response.data

                    dataResponse?.let {
                        allShoesBinding.rvProduct.layoutManager =
                            LinearLayoutManager(requireActivity())

                        val listProduct = ArrayList<DataItem>()
                        for (product in it) {
                            listProduct.add(product)
                        }

                        if (listProduct.isNotEmpty()) {
                            val listProductAdapter = ListProductAdapter(listProduct) { data ->
                                val intentDetail =
                                    Intent(requireActivity(), DetailActivity::class.java)
                                intentDetail.putExtra(DetailActivity.DETAIL_DATA_ITEM, data)
                                startActivity(intentDetail)

                            }
                            allShoesBinding.rvProduct.adapter = listProductAdapter
                        }
                    }
                }

                is Resource.Error -> {
                    Toast(requireActivity()).showCustomToast(
                        true,
                        response.message.toString(),
                        requireActivity()
                    )
                }
            }
        }
    }
}