package com.faroh.shamoandroid.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.databinding.ItemProductBinding

class ListProductAdapter(
    private val list: ArrayList<DataItem>,
    private val onClick: (DataItem) -> Unit
) : RecyclerView.Adapter<ListProductAdapter.ListProductViewHolder>() {

    class ListProductViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListProductViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListProductViewHolder, position: Int) {
        val data = list[position]

        val imageProduct = if (data.galleries?.isNotEmpty() == true) data.galleries[0]?.url else ""
        val nameProduct = list[position].name
        val categoryProduct = list[position].category?.name
        val priceProduct = list[position].price

        Glide.with(holder.itemView.context)
            .load(imageProduct ?: holder.itemView.context.getDrawable(R.drawable.image_shoes))
            .transform(CenterCrop(), RoundedCorners(20))
            .into(holder.binding.ivProduct)

        holder.binding.apply {
            tvCatProduct.text = categoryProduct
            tvNameProduct.text = nameProduct
            tvPriceProduct.text = holder.itemView.context.getString(R.string.price, priceProduct)
        }

        holder.itemView.setOnClickListener {
            onClick(list[holder.adapterPosition])
        }
    }
}