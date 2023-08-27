package com.faroh.shamoandroid.view.home.basketball

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
import com.faroh.shamoandroid.databinding.FragmentBasketBallBinding
import com.faroh.shamoandroid.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketBallFragment : Fragment() {

    private lateinit var basketBallBinding: FragmentBasketBallBinding
    private val basketBallViewModel: BasketBallViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        basketBallBinding = FragmentBasketBallBinding.inflate(layoutInflater)
        return basketBallBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        basketBallViewModel.getProductRunningCategory().observe(requireActivity()) { repsonse ->
            when (repsonse) {
                is Resource.Loading -> basketBallBinding.progressBar.visibility = View.VISIBLE

                is Resource.Success -> {
                    basketBallBinding.progressBar.visibility = View.GONE

                    val dataResponse = repsonse.data

                    dataResponse?.let {
                        basketBallBinding.rvBasketball.layoutManager =
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
                        basketBallBinding.rvBasketball.adapter = listProductAdapter
                    }
                }

                is Resource.Error -> {
                    Toast(requireActivity()).showCustomToast(
                        true,
                        repsonse.message.toString(),
                        requireActivity()
                    )
                }
            }
        }
    }
}