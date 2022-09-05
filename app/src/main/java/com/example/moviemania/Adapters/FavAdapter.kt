package com.example.moviemania.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemania.Extensions.GlideImageExtension.loadImage
import com.example.moviemania.Models.Movie
import com.example.moviemania.Models.Movies
import com.example.moviemania.R

class FavAdapter(private val list: List<Movie>) : RecyclerView.Adapter<FavAdapter.MyViewHolder>() {

    private lateinit var myListener: onItemClickListener

    interface onItemClickListener {
        fun onToggleClick( isFavorite: Boolean, item: Movie)
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false),
            myListener)
    }

    override fun onBindViewHolder(holder: FavAdapter.MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(view) {
        private val movieImage: ImageView = view.findViewById(R.id.iv_moviePoster)
        private val movieName: TextView = view.findViewById(R.id.tv_movieName)
        private val movieReleaseDate: TextView = view.findViewById(R.id.movieReleaseDate)
        private val favImage : ImageView = view.findViewById(R.id.favIcon)

        init {
            view.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(item: Movie) {
            item.moviePoster.let { movieImage.loadImage(it) }
            movieName.text = item.movieName
            movieReleaseDate.text = item.movieReleaseDate
            favImage.setImageResource(if (item.fav) R.drawable.redheart else R.drawable.redheart_border)
            favImage.setOnClickListener{
                myListener.onToggleClick(item.fav,item)
                item.fav = !item.fav
                notifyItemChanged(adapterPosition)
            }
        }
    }



}