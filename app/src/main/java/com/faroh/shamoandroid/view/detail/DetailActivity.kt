package com.faroh.shamoandroid.view.detail

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.data.source.remote.response.GalleriesItem
import com.faroh.shamoandroid.core.ui.ImageDetailSliderAdapter
import com.faroh.shamoandroid.core.ui.ListFamiliarProductAdapter
import com.faroh.shamoandroid.core.utils.ToastUtils.showCustomToast
import com.faroh.shamoandroid.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var detailBinding: ActivityDetailBinding
    private var dataItem: DataItem? = null
    private val detailViewModel: DetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        dataItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DETAIL_DATA_ITEM, DataItem::class.java)
        } else {
            intent.getParcelableExtra(DETAIL_DATA_ITEM)
        }

        dataItem?.let {
            detailViewModel.saveDataItem(it)
        }

        detailViewModel.dataItem.observe(this) { data ->
            data?.let {
                val arrayImage = ArrayList<GalleriesItem>()
                for (image in it.galleries!!) {
                    arrayImage.add(image!!)
                }

                val viewPagerAdapter = ImageDetailSliderAdapter(this, arrayImage)
                detailBinding.apply {
                    vpImageDetail.adapter = viewPagerAdapter
                    indicatorVpDetail.setViewPager(vpImageDetail)

                    tvNameDetail.text = it.name
                    tvCatDetail.text = it.category?.name
                    tvPriceDetail.text = getString(R.string.price, it.price)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        tvContentDescription.justificationMode = JUSTIFICATION_MODE_INTER_WORD
                    }
                    tvContentDescription.text = it.description
                }

                detailViewModel.getFamiliarProductByCategory()
                    .observe(this@DetailActivity) { response ->
                        when (response) {
                            is Resource.Loading -> detailBinding.progressBar.visibility =
                                View.VISIBLE

                            is Resource.Success -> {
                                detailBinding.progressBar.visibility = View.GONE

                                val dataResponse = response.data

                                dataResponse?.let { data ->
                                    detailBinding.rvFamiliarShoes.layoutManager =
                                        LinearLayoutManager(
                                            this@DetailActivity,
                                            LinearLayoutManager.HORIZONTAL,
                                            false
                                        )

                                    val listFamiliar = ArrayList<DataItem>()
                                    for (familiarProduct in data) {
                                        listFamiliar.add(familiarProduct)
                                    }

                                    val listFamiliarProductAdapter =
                                        ListFamiliarProductAdapter(listFamiliar) { dataDetail ->
                                            val intentDetail =
                                                Intent(
                                                    this@DetailActivity,
                                                    DetailActivity::class.java
                                                )
                                            intentDetail.putExtra(
                                                DETAIL_DATA_ITEM,
                                                dataDetail
                                            )
                                            startActivity(intentDetail)

                                        }
                                    detailBinding.rvFamiliarShoes.adapter =
                                        listFamiliarProductAdapter
                                }
                            }

                            is Resource.Error -> {
                                Toast(this@DetailActivity).showCustomToast(
                                    true,
                                    response.message.toString(),
                                    this@DetailActivity
                                )
                            }
                        }

                    }

                var isChecked = false

                detailViewModel.checkFavouriteProduct().observe(this) { available ->
                    if (available > 0) {
                        detailBinding.tglFav.isChecked = true
                        isChecked = true
                    } else {
                        detailBinding.tglFav.isChecked = false
                        isChecked = false
                    }
                }

                detailBinding.tglFav.setOnClickListener {
                    isChecked = !isChecked
                    if (isChecked) {
                        detailViewModel.setFavouriteProduct()
                    } else {
                        detailViewModel.deleteFavouriteProduct()
                    }
                    detailBinding.tglFav.isChecked = isChecked
                }
            }
        }

        detailBinding.apply {
            icBackDetail.setOnClickListener(this@DetailActivity)
            btnAddToCart.setOnClickListener(this@DetailActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ic_back_detail -> finish()
            R.id.btn_add_to_cart -> {
                detailViewModel.setCartProduct()

                val dialog = Dialog(this, R.style.RoundedCornersDialog).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setCancelable(false)
                    setContentView(R.layout.custom_detail_dialog)
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

                dialog.findViewById<ImageButton>(R.id.btn_close_dialog).setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }

    companion object {
        const val DETAIL_DATA_ITEM = "detail_data_item"
    }
}