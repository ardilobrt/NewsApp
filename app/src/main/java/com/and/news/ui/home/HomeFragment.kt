package com.and.news.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.and.news.adapter.ArticlesAdapter
import com.and.news.data.MyCompanion.showLoading
import com.and.news.data.model.ArticlesItem
import com.and.news.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var list: List<ArticlesItem>
    private val adapter get() = ArticlesAdapter(list)

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

        viewModel.setArticles()

        binding.srlNews.setOnRefreshListener {
            lifecycleScope.launch{
                delay(2000)
                withContext(Dispatchers.Main) {
                    getArticles(list)
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

    private fun getArticles(listArticles: List<ArticlesItem>) {
        list = listArticles
        adapter.setList()
        binding.rvNews.adapter = adapter
        binding.srlNews.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}