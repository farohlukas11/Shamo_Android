package com.faroh.shamoandroid.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.faroh.shamoandroid.R

object ToastUtils {
    @SuppressLint("MissingInflatedId")
    fun Toast.showCustomToast(errorState: Boolean, message: String, activity: Activity) {
        val layout = activity.layoutInflater.inflate(
            R.layout.custom_toast_layout,
            activity.findViewById(R.id.toast_container)
        )

        val basedToast = layout.findViewById<LinearLayout>(R.id.based_toast)
        basedToast.background = if (!errorState) ContextCompat.getDrawable(
            activity.applicationContext,
            R.drawable.custom_toast_success
        ) else ContextCompat.getDrawable(
            activity.applicationContext,
            R.drawable.custom_toast_error
        )

        val tvMessage = layout.findViewById<TextView>(R.id.tv_toast)
        tvMessage.text = message

        this.apply {
            view = layout
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            duration = Toast.LENGTH_SHORT
            show()
        }
    }
}