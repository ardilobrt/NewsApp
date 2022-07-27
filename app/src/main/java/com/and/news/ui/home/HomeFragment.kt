package com.and.news.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.and.news.R
import com.and.news.adapter.ArticlesAdapter
import com.and.news.data.local.entity.Articles
import com.and.news.databinding.FragmentHomeBinding
import com.and.news.ui.detail.DetailNewsActivity
import com.and.news.utils.MyCompanion
import com.and.news.utils.SharedPrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: HomeViewModelFactory
    private lateinit var articlesAdapter: ArticlesAdapter
    private var country: String? = null
    private val viewModel: HomeViewModel by viewModels { factory }


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

        setupComponent()
        observerValue()
        setRefreshListener()
    }

    private fun setupComponent() {

        articlesAdapter = ArticlesAdapter { article ->
            val isLogin = SharedPrefManager.getIsOnLogin(requireActivity())
            if (!isLogin) {
                findNavController().navigate(R.id.action_navigation_home_to_signInActivity)
            } else {
                if (article.isBookmarked) {
                    viewModel.deleteBookmark(article)
                } else viewModel.saveBookmark(article)
            }
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = articlesAdapter
        }

        articlesAdapter.setOnItemClickCallback(object : ArticlesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Articles) {
                Intent(requireActivity(), DetailNewsActivity::class.java).also {
                    it.putExtra(MyCompanion.EXTRA_ARTICLES, data)
                    startActivity(it)
                }
            }
        })

        binding.chipFilter.setOnClickListener {
            setBottomDialogSheet()
        }
    }

    private fun observerValue() {
        factory = HomeViewModelFactory.getInstance(requireActivity())

        viewModel.listArticles.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = View.GONE
            articlesAdapter.submitList(result.toMutableList())
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { event ->
            binding.progressBar.visibility = View.GONE
            event.getContentIfNotHandled().let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setRefreshListener() {
        binding.srlNews.apply {
            setOnRefreshListener {
                lifecycleScope.launch {
                    delay(2000)
                    withContext(Dispatchers.Main) {
                        viewModel.getArticles(country.toString())
                        binding.rvNews.apply {
                            layoutManager = LinearLayoutManager(context)
                            setHasFixedSize(true)
                            adapter = articlesAdapter
                        }
                        isRefreshing = false
                    }
                }
            }
        }
    }

    private fun setBottomDialogSheet() {
        val dialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.dialog_bottom_sheet, null)
        val chipIdn: Chip = view.findViewById(R.id.chip_idn)
        val chipUsa: Chip = view.findViewById(R.id.chip_usa)

        chipIdn.setOnClickListener {
            setCountry("id")
            dialog.dismiss()
        }

        chipUsa.setOnClickListener {
            setCountry("us")
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }

    private fun setCountry(country: String) {
        SharedPrefManager.setCountry(requireActivity(), country)
        viewModel.getArticles(country)
    }

    override fun onStart() {
        super.onStart()
        country = SharedPrefManager.getCountry(requireActivity())
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getArticles(country.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}