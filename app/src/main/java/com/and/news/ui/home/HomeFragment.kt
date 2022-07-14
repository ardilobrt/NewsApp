package com.and.news.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.and.news.adapter.ArticlesAdapter
import com.and.news.utils.MyCompanion
import com.and.news.utils.MyCompanion.showLoading
import com.and.news.data.remote.model.ArticlesItem
import com.and.news.databinding.FragmentHomeBinding
import com.and.news.ui.detail.DetailNewsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)

        binding.apply {
            rvNews.layoutManager = layoutManager
            rvNews.addItemDecoration(itemDecoration)
        }

        binding.srlNews.setOnRefreshListener {
            binding.edtSearch.clearFocus()
            hideKeyboard()
            lifecycleScope.launch {
                delay(2000)
                withContext(Dispatchers.Main) {
                    viewModel.setArticles()
                }
            }
        }

        viewModel.listArticles.observe(viewLifecycleOwner) {
            getArticles(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.rvNews.showLoading(it)
        }

    }

    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun getArticles(listArticles: ArrayList<ArticlesItem>) {
        binding.rvNews.adapter = ArticlesAdapter(listArticles) { articles ->
            Intent(this.context, DetailNewsActivity::class.java).also {
                it.putExtra(MyCompanion.EXTRA_ARTICLES, articles)
                startActivity(it)
            }
        }
        binding.srlNews.isRefreshing = false
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