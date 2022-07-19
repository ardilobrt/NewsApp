package com.and.news.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.and.news.adapter.ArticlesAdapter
import com.and.news.data.local.entity.Articles
import com.and.news.databinding.FragmentBookmarkBinding
import com.and.news.ui.detail.DetailNewsActivity
import com.and.news.utils.MyCompanion
import com.and.news.utils.MyCompanion.showText

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = BookmarkViewModelFactory.getInstance(requireActivity())
        val viewModel: BookmarkViewModel by viewModels { factory }

        val articlesAdapter = ArticlesAdapter { article ->
            if (article.isBookmarked) {
                viewModel.deleteBookmark(article)
            } else viewModel.saveBookmark(article)
        }

        viewModel.listBookmark.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.textProfile.apply {
                    if (result.isEmpty()) showText(true) else showText(false)
                }
                articlesAdapter.submitList(result)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}