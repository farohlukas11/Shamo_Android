package com.faroh.shamoandroid.view.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.ui.ListCheckoutProductAdapter
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

            }
        }

        checkoutBinding.icBackCheckout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ic_back_checkout -> finish()
        }

    }
}