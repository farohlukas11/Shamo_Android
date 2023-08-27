package com.faroh.shamoandroid.view.home.training

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
import com.faroh.shamoandroid.databinding.FragmentTrainingBinding
import com.faroh.shamoandroid.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrainingFragment : Fragment() {

    private lateinit var trainingBinding: FragmentTrainingBinding
    private val trainingViewModel: TrainingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        trainingBinding = FragmentTrainingBinding.inflate(layoutInflater)
        return trainingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trainingViewModel.getProductTrainingCategory().observe(requireActivity()) { response ->
            when (response) {
                is Resource.Loading -> trainingBinding.progressBar.visibility = View.VISIBLE

                is Resource.Success -> {
                    trainingBinding.progressBar.visibility = View.GONE

                    val dataResponse = response.data

                    dataResponse?.let {
                        trainingBinding.rvTrainning.layoutManager =
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
                        trainingBinding.rvTrainning.adapter = listProductAdapter
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