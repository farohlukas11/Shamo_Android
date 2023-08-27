package com.faroh.shamoandroid.view.home.running

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
import com.faroh.shamoandroid.core.ui.ListProductAdapter
import com.faroh.shamoandroid.core.utils.ToastUtils.showCustomToast
import com.faroh.shamoandroid.databinding.FragmentRunningBinding
import com.faroh.shamoandroid.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunningFragment : Fragment() {

    private lateinit var runningBinding: FragmentRunningBinding
    private val runningViewModel: RunningViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        runningBinding = FragmentRunningBinding.inflate(layoutInflater)
        return runningBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runningViewModel.getProductRunningCategory().observe(requireActivity()) { response ->
            when (response) {
                is Resource.Loading -> runningBinding.progressBar.visibility = View.VISIBLE

                is Resource.Success -> {
                    runningBinding.progressBar.visibility = View.GONE

                    val dataResponse = response.data

                    dataResponse?.let {
                        runningBinding.rvRunning.layoutManager =
                            LinearLayoutManager(requireActivity())

                        val listProduct = ArrayList<DataItem>()
                        for (product in it) {
                            listProduct.add(product)
                        }
                        val listProductAdapter = ListProductAdapter(listProduct) { data ->
                            val intentDetail =
                                Intent(requireActivity(), DetailActivity::class.java)
                            intentDetail.putExtra(DetailActivity.DETAIL_DATA_ITEM, data)
                            startActivity(intentDetail)

                        }
                        runningBinding.rvRunning.adapter = listProductAdapter
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