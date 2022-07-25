package com.and.news.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.and.news.R
import com.and.news.data.remote.model.SignInResponse
import com.and.news.databinding.FragmentProfileBinding
import com.and.news.utils.SharedPrefManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isLogin = SharedPrefManager.getIsOnLogin(requireActivity())
        if (isLogin) {
            observerValue()
        } else findNavController().navigate(R.id.action_navigation_profile_to_signInActivity)
    }

    private fun observerValue() {

        val viewModel: ProfileViewModel by viewModels()
        val username = SharedPrefManager.getEmail(requireActivity())
        val password = SharedPrefManager.getPassword(requireActivity())
        val signInResponse = SignInResponse(username.toString(), password.toString())

        binding.progressBar.visibility = View.VISIBLE
        viewModel.getUser(requireActivity(), signInResponse)

        viewModel.data.observe(viewLifecycleOwner) {
            binding.apply {
                binding.progressBar.visibility = View.GONE
                inputUserName.setText(it?.username)
                inputEmail.setText(it?.email)
            }
        }

        viewModel.dataError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled().let {
                if (it != null) {
                    showMessage(it)
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}