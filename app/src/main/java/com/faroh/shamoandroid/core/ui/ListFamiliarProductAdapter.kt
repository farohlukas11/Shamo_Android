package com.faroh.shamoandroid.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.databinding.ItemFamiliarShoesBinding

class ListFamiliarProductAdapter(
    private val list: ArrayList<DataItem>,
    private val onClick: (DataItem) -> Unit
) : RecyclerView.Adapter<ListFamiliarProductAdapter.ListFamiliarProductViewHolder>() {

    class ListFamiliarProductViewHolder(var binding: ItemFamiliarShoesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFamiliarProductViewHolder {
        val binding =
            ItemFamiliarShoesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListFamiliarProductViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListFamiliarProductViewHolder, position: Int) {
        val imageProduct = list[position].galleries?.get(0)?.url

        Glide.with(holder.itemView.context).load(imageProduct)
            .transform(CenterCrop(), RoundedCorners(6)).into(holder.binding.ivFamiliarShoes)

        holder.itemView.setOnClickListener {
            onClick(list[holder.adapterPosition])
        }
    }
}