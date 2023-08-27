package com.faroh.shamoandroid.view.favourite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.ui.ListFavouriteProductAdapter
import com.faroh.shamoandroid.databinding.FragmentFavouriteBinding
import com.faroh.shamoandroid.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var favouriteBinding: FragmentFavouriteBinding
    private val favouriteViewModel: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favouriteBinding = FragmentFavouriteBinding.inflate(layoutInflater)
        return favouriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteViewModel.getFavouriteProduct().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                favouriteBinding.bgEmptyFav.visibility = View.VISIBLE
            } else {
                favouriteBinding.bgEmptyFav.visibility = View.GONE
            }

            favouriteBinding.rvFav.layoutManager = LinearLayoutManager(requireActivity())

            val listFavourite = ArrayList<DataItem>()
            for (favouriteProduct in it) {
                listFavourite.add(favouriteProduct)
            }

            val listFavouriteAdapter = ListFavouriteProductAdapter(
                listFavourite,
                { dataIntent ->
                    val intentDetail =
                        Intent(requireActivity(), DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.DETAIL_DATA_ITEM, dataIntent)
                    startActivity(intentDetail)
                },
                { dataRemove ->
                    favouriteViewModel.deleteFavouriteProduct(dataRemove)
                }
            )

            favouriteBinding.rvFav.adapter = listFavouriteAdapter

        }
    }
}