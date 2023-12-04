package com.faroh.shamoandroid.view.sigin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.faroh.shamoandroid.MainActivity
import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.domain.model.LoginBody
import com.faroh.shamoandroid.core.utils.ToastUtils.showCustomToast
import com.faroh.shamoandroid.databinding.ActivitySignInBinding
import com.faroh.shamoandroid.view.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding: ActivitySignInBinding
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signInBinding.root)

        signInBinding.tvSignUpBottom.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }

        signInBinding.btnSignIn.setOnClickListener {
            val emailAddress = signInBinding.etEmailAddressSignIn.text.toString()
            val password = signInBinding.etPasswordSignIn.text.toString()

            when {
                emailAddress.isEmpty() || password.isEmpty() -> Toast(
                    this
                ).showCustomToast(true, "input must be required filled", this)

                else -> {
                    signInViewModel.signInUser(
                        LoginBody(
                            email = emailAddress,
                            password = password
                        )
                    ).observe(this) { response ->
                        when (response) {

                            is Resource.Loading -> {
                                signInBinding.progressBar.visibility =
                                    View.VISIBLE
                            }

                            is Resource.Success -> {
                                signInBinding.progressBar.visibility = View.GONE
                                val dataResponse = response.data
                                Toast(this@SignInActivity).showCustomToast(
                                    false,
                                    dataResponse?.meta?.message.toString(),
                                    this
                                )
                                if (dataResponse != null) {
                                    signInViewModel.saveUser(dataResponse)
                                    signInViewModel.isLogin()
                                }
                                val intentToMain =
                                    Intent(this, MainActivity::class.java)
                                startActivity(intentToMain)
                                finish()
                            }

                            is Resource.Error -> {
                                Toast(this).showCustomToast(
                                    true,
                                    response.message.toString(),
                                    this
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}