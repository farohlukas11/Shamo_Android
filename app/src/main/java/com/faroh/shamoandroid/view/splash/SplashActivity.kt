package com.faroh.shamoandroid.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import com.faroh.shamoandroid.MainActivity
import com.faroh.shamoandroid.databinding.ActivitySplashBinding
import com.faroh.shamoandroid.view.sigin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()
    private var userToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        val delayTime = 2000L

        splashViewModel.mediator.observe(this) {}
        splashViewModel.user.observe(this) {
            userToken = it
        }

        Handler(Looper.getMainLooper()).postDelayed({
            splashViewModel.getState().observe(this@SplashActivity) {
                if (it) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                }
                finish()
            }
        }, delayTime)
    }
}
