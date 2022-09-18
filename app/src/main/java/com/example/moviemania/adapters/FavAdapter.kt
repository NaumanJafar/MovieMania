package com.example.moviemania.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemania.extensions.GlideImageExtension.loadImage
import com.example.moviemania.models.Movie
import com.example.moviemania.R
import com.example.moviemania.databinding.MovieListBinding

class FavAdapter(private val list: List<Movie>) : RecyclerView.Adapter<FavAdapter.MyViewHolder>() {

    private lateinit var myListener: onItemClickListener

    interface onItemClickListener {
        fun onToggleClick(isFavorite: Boolean, item: Movie)
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: MovieListBinding =
            MovieListBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        return MyViewHolder(binding, myListener)
    }

    override fun onBindViewHolder(holder: FavAdapter.MyViewHolder, position: Int) {
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
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(item: Movie) {
            item.moviePoster.let { binding.ivMoviePoster.loadImage(it) }
            binding.tvMovieName.text = item.movieName
            binding.movieReleaseDate.text = item.movieReleaseDate
            binding.favIcon.setImageResource(if (item.fav) R.drawable.redheart else R.drawable.redheart_border)
            binding.favIcon.setOnClickListener {
                myListener.onToggleClick(item.fav, item)
                item.fav = !item.fav
                notifyItemChanged(adapterPosition)
            }
        }
    }


}