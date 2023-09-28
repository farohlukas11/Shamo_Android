package com.faroh.shamoandroid.view.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.core.ui.ListCartProductAdapter
import com.faroh.shamoandroid.core.utils.ToastUtils.showCustomToast
import com.faroh.shamoandroid.databinding.FragmentCartBinding
import com.faroh.shamoandroid.view.checkout.CheckoutActivity
import com.faroh.shamoandroid.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var cartBinding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cartBinding = FragmentCartBinding.inflate(layoutInflater)
        return cartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel.getCartProduct().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                cartBinding.bgEmptyCart.visibility = View.VISIBLE
            } else {
                cartBinding.bgEmptyCart.visibility = View.GONE
            }

            cartBinding.rvCart.layoutManager = LinearLayoutManager(requireActivity())

            val listCart = ArrayList<DataItemCart>()
            for (cartProduct in it) {
                listCart.add(cartProduct)
            }

            val listCartAdapter = ListCartProductAdapter(
                listCart,
                { dataIntent ->
                    val intentDetail =
                        Intent(requireActivity(), DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.DETAIL_DATA_ITEM, dataIntent)
                    startActivity(intentDetail)
                }, { plus ->
                    plus.inCart += 1
                    cartViewModel.setCartProduct(plus)
                }, { minus ->
                    if (minus.inCart < 1) minus.inCart = 0 else minus.inCart -= 1

                    cartViewModel.setCartProduct(minus)
                }, { remove ->
                    remove.inCart = 0
                    cartViewModel.setCartProduct(remove)
                }
            )
            cartBinding.rvCart.adapter = listCartAdapter

            val subTotalPrice = it.sumOf { sub -> sub.dataItem.price!! * sub.inCart }
            cartBinding.tvSubtotalPrice.text = getString(R.string.price, subTotalPrice)

            cartBinding.btnConCheck.setOnClickListener {
                if (subTotalPrice != 0) {
                    startActivity(Intent(requireActivity(), CheckoutActivity::class.java))
                } else {
                    Toast(requireContext()).showCustomToast(
                        true,
                        getString(R.string.opss_your_cart_is_empty),
                        requireActivity()
                    )
                }
            }
        }
    }
}