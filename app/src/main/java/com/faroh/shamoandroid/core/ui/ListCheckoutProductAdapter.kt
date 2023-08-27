package com.faroh.shamoandroid.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.databinding.ItemCheckoutProductBinding

class ListCheckoutProductAdapter(
    private val list: ArrayList<DataItemCart>
) : RecyclerView.Adapter<ListCheckoutProductAdapter.ListCheckoutViewHolder>() {
    class ListCheckoutViewHolder(var binding: ItemCheckoutProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCheckoutViewHolder {
        val binding =
            ItemCheckoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListCheckoutViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListCheckoutViewHolder, position: Int) {
        val checkoutProduct = list[position]

        val imageCheckout = checkoutProduct.dataItem.galleries?.get(0)?.url
        val nameCheckout = checkoutProduct.dataItem.name
        val priceCheckout = checkoutProduct.dataItem.price
        val itemsCountCheckout = checkoutProduct.inCart

        Glide.with(holder.itemView.context).load(imageCheckout)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(holder.binding.ivCheckoutProduct)

        holder.binding.apply {
            tvNameCheckoutProduct.text = nameCheckout
            tvPriceCheckoutProduct.text =
                holder.itemView.context.getString(R.string.price, priceCheckout)
            tvItemsCheckoutProduct.text =
                holder.itemView.context.getString(R.string.items, itemsCountCheckout)
        }
    }
}