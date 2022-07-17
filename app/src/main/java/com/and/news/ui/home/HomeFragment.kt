package com.and.news.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.and.news.adapter.ArticlesAdapter
import com.and.news.data.MyResult
import com.and.news.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: HomeViewModelFactory = HomeViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels { factory }

        val articlesAdapter = ArticlesAdapter { article ->
            if (article.isBookmarked) {
                viewModel.deleteBookmark(article)
            } else viewModel.saveBookmark(article)
        }

        viewModel.listArticles.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is MyResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is MyResult.Success -> {
                        binding.progressBar.visibility = View.GONE
                        articlesAdapter.submitList(result.data)
                    }
                    is MyResult.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = articlesAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        binding.edtSearch.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}