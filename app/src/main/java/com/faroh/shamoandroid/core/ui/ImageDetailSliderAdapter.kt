package com.faroh.shamoandroid.core.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.data.source.remote.response.GalleriesItem

class ImageDetailSliderAdapter(
    private val context: Context,
    private val imageList: ArrayList<GalleriesItem>
) : PagerAdapter() {
    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_image_detail, null)
        val ivImage = view.findViewById<ImageView>(R.id.iv_image_detail)

        imageList[position].let {
            Glide.with(context).load(it.url).centerCrop().into(ivImage)
        }

        val vp = container as ViewPager
        vp.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}