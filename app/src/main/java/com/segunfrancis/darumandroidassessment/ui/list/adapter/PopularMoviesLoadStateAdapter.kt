package com.segunfrancis.darumandroidassessment.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.segunfrancis.darumandroidassessment.R
import com.segunfrancis.darumandroidassessment.databinding.ItemLoadStateBinding
import com.segunfrancis.darumandroidassessment.util.handleThrowable

class PopularMoviesLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<PopularMoviesLoadStateAdapter.PopularMoviesLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PopularMoviesLoadStateViewHolder {
        return PopularMoviesLoadStateViewHolder(
            binding = ItemLoadStateBinding.bind(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.item_load_state, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: PopularMoviesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class PopularMoviesLoadStateViewHolder(private val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {
                stateErrorMessage.text = loadState.error.handleThrowable()
            }
            stateLoader.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
            stateErrorMessage.isVisible = loadState !is LoadState.Loading
        }
    }
}
