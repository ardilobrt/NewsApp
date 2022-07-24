package com.and.news.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.and.news.R
import com.and.news.databinding.FragmentProfileBinding
import com.and.news.utils.SharedPrefManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>()

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
        val username = SharedPrefManager.getUserName(requireContext())
        viewModel.showUser(username.toString())

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textProfile.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}