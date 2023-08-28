package com.faroh.shamoandroid.view.checkout

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.domain.model.DataCheckout
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.domain.model.ItemCheckOut
import com.faroh.shamoandroid.core.ui.ListCheckoutProductAdapter
import com.faroh.shamoandroid.core.utils.DataMapper
import com.faroh.shamoandroid.core.utils.ToastUtils.showCustomToast
import com.faroh.shamoandroid.databinding.ActivityCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var checkoutBinding: ActivityCheckoutBinding
    private val checkoutViewModel: CheckoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkoutBinding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(checkoutBinding.root)

        checkoutViewModel.getCartProduct().observe(this) {
            it?.let {
                checkoutBinding.rvCheckoutProduct.layoutManager = LinearLayoutManager(this)

                val listCheckout = ArrayList<DataItemCart>()
                for (checkoutProduct in it) {
                    listCheckout.add(checkoutProduct)
                }

                val listCheckoutAdapter = ListCheckoutProductAdapter(listCheckout)
                checkoutBinding.rvCheckoutProduct.adapter = listCheckoutAdapter

                val subTotalPrice = it.sumOf { sub -> sub.dataItem.price!! * sub.inCart }

                checkoutBinding.apply {
                    tvItemsCheckout.text = getString(R.string.items, it.size)
                    tvPriceCheckout.text = getString(R.string.price, subTotalPrice)
                    tvTotalPrice.text = getString(R.string.price, subTotalPrice)
                }
            }
        }

        checkoutBinding.icBackCheckout.setOnClickListener(this)
        checkoutBinding.btnCheckout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ic_back_checkout -> finish()
            R.id.btn_checkout -> {
                checkoutViewModel.getCartProduct().observe(this) {
                    it?.let { dataItemCarts: List<DataItemCart> ->
                        checkoutViewModel.user.observe(this) { user ->
                            checkoutViewModel.checkoutProduct(
                                token = user.token!!,
                                dataCheckout = DataCheckout(
                                    items = DataMapper.mapDataItemToDataCheckout(dataItemCarts),
                                    total_price = dataItemCarts.sumOf { sub -> sub.dataItem.price!! * sub.inCart }
                                )
                            ).observe(this) { response ->
                                when (response) {
                                    is Resource.Loading -> checkoutBinding.progressBar.visibility =
                                        View.VISIBLE

                                    is Resource.Success -> {
                                        checkoutBinding.progressBar.visibility = View.GONE
                                        val dataResponse = response.data
                                        Toast(this@CheckoutActivity).showCustomToast(
                                            false,
                                            dataResponse?.meta?.message.toString(),
                                            this
                                        )

                                        val dialog =
                                            Dialog(this, R.style.RoundedCornersDialog).apply {
                                                requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                setCancelable(false)
                                                setContentView(R.layout.custom_checkout_dialog)
                                                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                            }

                                        dialog.findViewById<ImageButton>(R.id.btn_close_dialog_checkout)
                                            .setOnClickListener {
                                                dialog.dismiss()
                                                finish()
                                            }
                                        dialog.show()
                                    }

                                    is Resource.Error -> {
                                        Toast(this).showCustomToast(
                                            true,
                                            response.message.toString(),
                                            this
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}