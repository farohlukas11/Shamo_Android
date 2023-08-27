package com.faroh.shamoandroid.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.databinding.ItemFavouriteProductBinding

class ListFavouriteProductAdapter(
    private val list: ArrayList<DataItem>,
    private val onClick: (DataItem) -> Unit,
    private val onRemove: (DataItem) -> Unit
) : RecyclerView.Adapter<ListFavouriteProductAdapter.ListFavouriteViewHolder>() {

    class ListFavouriteViewHolder(var binding: ItemFavouriteProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFavouriteViewHolder {
        val binding =
            ItemFavouriteProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListFavouriteViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListFavouriteViewHolder, position: Int) {
        val imageFavourite = list[position].galleries?.get(0)?.url
        val nameFavourite = list[position].name
        val priceFavourite = list[position].price

        Glide.with(holder.itemView.context).load(imageFavourite)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(holder.binding.ivFavProduct)

        holder.binding.apply {
            tvNameFavProduct.text = nameFavourite
            tvPriceFavProduct.text =
                holder.itemView.context.getString(R.string.price, priceFavourite)
            tglFavProduct.isChecked = true
        }

        holder.binding.tglFavProduct.setOnClickListener {
            onRemove(list[holder.adapterPosition])
        }

        holder.itemView.setOnClickListener {
            onClick(list[holder.adapterPosition])
        }
    }

}