package com.and.news.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.and.news.R
import com.and.news.databinding.FragmentAuthorizedBinding

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
}