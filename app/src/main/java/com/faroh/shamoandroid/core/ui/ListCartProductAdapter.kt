package com.faroh.shamoandroid.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.data.source.remote.response.DataItem
import com.faroh.shamoandroid.core.domain.model.DataItemCart
import com.faroh.shamoandroid.databinding.ItemCartProductBinding

class ListCartProductAdapter(
    private val list: ArrayList<DataItemCart>,
    private val onClick: (DataItem) -> Unit,
    private val onPlus: (DataItemCart) -> Unit,
    private val onMinus: (DataItemCart) -> Unit,
    private val onRemove: (DataItemCart) -> Unit
) : RecyclerView.Adapter<ListCartProductAdapter.ListCartViewHolder>() {
    class ListCartViewHolder(var binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCartViewHolder {
        val binding =
            ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListCartViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListCartViewHolder, position: Int) {
        val cartproduct = list[position]
        val data = list[holder.adapterPosition]

        val imageCart = cartproduct.dataItem.galleries?.get(0)?.url
        val nameCart = cartproduct.dataItem.name
        val priceCart = cartproduct.dataItem.price
        val countCart = cartproduct.inCart

        Glide.with(holder.itemView.context).load(imageCart)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(holder.binding.ivCartProduct)

        holder.binding.apply {
            tvNameCartProduct.text = nameCart
            tvPriceCartProduct.text = holder.itemView.context.getString(R.string.price, priceCart)
            tvCountCart.text = countCart.toString()
        }

        holder.itemView.setOnClickListener {
            onClick(data.dataItem)
        }

        holder.binding.bgRemoveCartProduct.setOnClickListener {
            onRemove(data)
            notifyItemChanged(position)
        }

        holder.binding.btnPlusCart.setOnClickListener {
            onPlus(data)
            notifyItemChanged(position)
        }

        holder.binding.btnMinusCart.setOnClickListener {
            onMinus(data)
            notifyItemChanged(position)
        }
    }
}