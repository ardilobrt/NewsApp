package com.and.news.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.and.news.databinding.FragmentAuthorizedBinding
import com.and.news.ui.auth.SignInActivity

class AuthorizedFragment : Fragment() {
    private var _binding: FragmentAuthorizedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAuthorizedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSignUp.setOnClickListener {
            Intent(requireContext(), SignInActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}