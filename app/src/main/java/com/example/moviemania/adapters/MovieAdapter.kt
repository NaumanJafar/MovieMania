package com.example.moviemania.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemania.extensions.GlideImageExtension.loadImage
import com.example.moviemania.models.Movies
import com.example.moviemania.R
import com.example.moviemania.databinding.MovieListBinding

class MovieAdapter(private val list: List<Movies>) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    private lateinit var myListener: onItemClickListener


    interface onItemClickListener {
        fun onToggleClick(isFavorite: Boolean, item: Movies)
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: MovieListBinding =
            MovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding,
            myListener
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(private val binding: MovieListBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                with(listener) { onItemClick(adapterPosition) }
            }
        }

        fun bind(item: Movies) {
            item.poster_path?.let { binding.ivMoviePoster.loadImage(it) }
            binding.tvMovieName.text = item.original_title
            binding.movieReleaseDate.text = item.release_date
            binding.favIcon.setImageResource(if (item.fav) R.drawable.redheart else R.drawable.redheart_border)
            binding.favIcon.setOnClickListener {
                myListener.onToggleClick(item.fav, item)
                item.fav = !item.fav
                notifyItemChanged(adapterPosition)
            }
        }
    }

}