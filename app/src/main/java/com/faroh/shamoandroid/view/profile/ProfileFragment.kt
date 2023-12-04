package com.faroh.shamoandroid.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.data.Resource
import com.faroh.shamoandroid.core.data.source.remote.response.RegisterAndLoginResponse
import com.faroh.shamoandroid.core.utils.ToastUtils.showCustomToast
import com.faroh.shamoandroid.databinding.FragmentProfileBinding
import com.faroh.shamoandroid.view.sigin.SignInActivity
import com.faroh.shamoandroid.view.transaction.TransactionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var profileBinding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(layoutInflater)
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getUser().observe(requireActivity()) { user ->
            user?.let {
                profileBinding.apply {
                    tvNameProfile.text = getString(R.string.hello_home, it.name)
                    tvUsernameProfile.text = getString(R.string.username_home, it.username)

                    Glide.with(requireActivity()).load(it.userProfilePhoto).circleCrop()
                        .into(ivProfile)
                }
            }
        }

        profileBinding.btnExitProfile.setOnClickListener {
            profileViewModel.getUser().observe(requireActivity()) { user ->
                profileViewModel.logoutUser(user.token!!).observe(requireActivity()) { response ->
                    when (response) {
                        is Resource.Loading -> profileBinding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            profileBinding.progressBar.visibility = View.GONE

                            val dataResponse = response.data
                            Toast(requireContext()).showCustomToast(
                                false,
                                dataResponse?.meta?.message.toString(),
                                requireActivity()
                            )

                            if (dataResponse != null) {
                                profileViewModel.saveUser(RegisterAndLoginResponse())
                                profileViewModel.isLogOut()

                                startActivity(Intent(requireActivity(), SignInActivity::class.java))
                                requireActivity().finish()
                            }
                        }

                        is Resource.Error -> {
                            Toast(requireActivity()).showCustomToast(
                                true,
                                response.message.toString(),
                                requireActivity()
                            )
                        }
                    }
                }
            }
        }

        profileBinding.bgYourOrders.setOnClickListener {
            startActivity(Intent(requireActivity(), TransactionActivity::class.java))
        }
    }
}