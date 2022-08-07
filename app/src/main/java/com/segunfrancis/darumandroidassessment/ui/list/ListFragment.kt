package com.segunfrancis.darumandroidassessment.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.paging.LoadState
import com.segunfrancis.darumandroidassessment.R
import com.segunfrancis.darumandroidassessment.databinding.FragmentListBinding
import com.segunfrancis.darumandroidassessment.ui.list.adapter.PopularMoviesAdapter
import com.segunfrancis.darumandroidassessment.ui.list.adapter.PopularMoviesLoadStateAdapter
import com.segunfrancis.darumandroidassessment.util.handleThrowable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ListViewModel>()

    private val moviesAdapter: PopularMoviesAdapter by lazy { PopularMoviesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        setupAdapter()
        setupObservers()
    }

    private fun setupAdapter() = with(binding) {
        moviesList.adapter = moviesAdapter.withLoadStateFooter(
            footer = PopularMoviesLoadStateAdapter { moviesAdapter.retry() }
        )
        mainError.retryButton.setOnClickListener { moviesAdapter.retry() }
        moviesAdapter.addLoadStateListener { loadState ->
            moviesList.isVisible = loadState.source.refresh is LoadState.NotLoading
            mainLoading.root.isVisible = loadState.source.refresh is LoadState.Loading
            mainError.root.isVisible = loadState.source.refresh is LoadState.Error

            val errorState =
                loadState.source.append as? LoadState.Error ?: loadState.append as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error
            errorState?.let { mainError.textError.text = it.error.handleThrowable() }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            whenStarted {
                viewModel.getPopularMovies().collectLatest {
                    moviesAdapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
