package com.faroh.shamoandroid.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.databinding.ItemPopularProductBinding
import com.faroh.shamoandroid.R

class ListPopularProductAdapter(
    private val list: ArrayList<DataItem>,
    private val onClick: (DataItem) -> Unit
) :
    RecyclerView.Adapter<ListPopularProductAdapter.ListViewHolder>() {


    class ListViewHolder(var binding: ItemPopularProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val binding =
            ItemPopularProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]
        val imagePopular = if (data.galleries?.isNotEmpty() == true) data.galleries.get(0)?.url else ""
        val namePopular = list[position].name
        val categoryPopular = list[position].category?.name
        val pricePopular = list[position].price

        Glide.with(holder.itemView.context).load(imagePopular)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(holder.binding.ivPpopular)

        holder.binding.apply {
            tvCatPopular.text = categoryPopular
            tvNamePopular.text = namePopular
            tvPricePopular.text =
                holder.itemView.context.getString(R.string.price, pricePopular)
        }

        holder.itemView.setOnClickListener {
            onClick(list[holder.adapterPosition])
        }
    }
}