package com.faroh.shamoandroid.view.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.domain.model.RegisterBody
import com.faroh.shamoandroid.core.utils.ToastUtils.showCustomToast
import com.faroh.shamoandroid.databinding.ActivitySignUpBinding
import com.faroh.shamoandroid.view.sigin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        signUpBinding.tvSignInBottom.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        signUpBinding.btnSignUp.setOnClickListener {
            val fullname = signUpBinding.etFullname.text.toString()
            val username = signUpBinding.etUsername.text.toString()
            val emailAddress = signUpBinding.etEmailAddress.text.toString()
            val password = signUpBinding.etPassword.text.toString()

            when {
                fullname.isEmpty() && username.isEmpty() && emailAddress.isEmpty() && password.isEmpty() -> Toast(
                    this
                ).showCustomToast(true, "input must be required filled", this)

                else -> {
                    signUpViewModel.signUpUser(
                        RegisterBody(
                            name = fullname,
                            email = emailAddress,
                            username = username,
                            password = password,
                            phone = ""
                        )
                    ).observe(this) { response ->
                        when (response) {
                            is Resource.Loading -> signUpBinding.progressBar.visibility =
                                View.VISIBLE

                            is Resource.Success -> {
                                signUpBinding.progressBar.visibility = View.GONE
                                val dataResponse = response.data
                                Toast(this).showCustomToast(
                                    false,
                                    dataResponse?.meta?.message.toString(),
                                    this
                                )
                                startActivity(Intent(this, SignInActivity::class.java))
                            }

                            is Resource.Error -> {
                                Toast(this).showCustomToast(
                                    true,
                                    response.message.toString(),
                                    this
                                )
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}