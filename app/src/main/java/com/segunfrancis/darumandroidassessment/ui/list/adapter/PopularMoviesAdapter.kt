package com.segunfrancis.darumandroidassessment.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.segunfrancis.darumandroidassessment.R
import com.segunfrancis.darumandroidassessment.data.dto.MovieResponse
import com.segunfrancis.darumandroidassessment.databinding.ItemPopularMoviesBinding
import com.segunfrancis.darumandroidassessment.util.AppConstants.buildImageUrl
import com.segunfrancis.darumandroidassessment.util.loadImage

class PopularMoviesAdapter :
    PagingDataAdapter<MovieResponse, PopularMoviesAdapter.PopularMoviesViewHolder>(
        PopularMoviesDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        return PopularMoviesViewHolder(
            binding = ItemPopularMoviesBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_popular_movies, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PopularMoviesViewHolder(private val binding: ItemPopularMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(response: MovieResponse?) = with(binding) {
            movieTitle.text = response?.title
            movieImage.loadImage(buildImageUrl(path = response?.backdrop_path))
        }
    }

    companion object PopularMoviesDiffUtil : DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return false
        }
    }
}
