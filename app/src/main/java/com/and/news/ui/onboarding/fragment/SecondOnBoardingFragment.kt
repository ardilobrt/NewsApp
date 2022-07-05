package com.and.news.ui.onboarding.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.and.news.R
import com.and.news.databinding.FragmentScreenOnBoardingBinding

class SecondOnBoardingFragment : Fragment() {
    private var _binding: FragmentScreenOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScreenOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivOnboarding.setImageResource(R.drawable.on_boarding_2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}