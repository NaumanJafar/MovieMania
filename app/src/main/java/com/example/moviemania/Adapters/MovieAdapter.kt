package com.example.moviemania.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemania.Extensions.GlideImageExtension.loadImage
import com.example.moviemania.Models.Movies
import com.example.moviemania.R

class MovieAdapter(private val list: List<Movies>) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    private lateinit var myListener:onItemClickListener


    interface onItemClickListener{
        fun onToggleClick( isFavorite: Boolean, item: Movies)
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieImage: ImageView = view.findViewById(R.id.iv_moviePoster)
        private val movieName: TextView = view.findViewById(R.id.tv_movieName)
        private val movieReleaseDate: TextView = view.findViewById(R.id.movieReleaseDate)
        private val favImage :ImageView = view.findViewById(R.id.favIcon)

        init {
            view.setOnClickListener{
                myListener.onItemClick(adapterPosition)
            }
        }

        fun bind(item: Movies) {
            item.poster_path?.let { movieImage.loadImage(it) }
            movieName.text = item.original_title
            movieReleaseDate.text = item.release_date
            favImage.setImageResource(if (item.fav) R.drawable.redheart else R.drawable.redheart_border)
            favImage.setOnClickListener{
                myListener.onToggleClick( item.fav ,item)
                item.fav = !item.fav
                notifyItemChanged(adapterPosition)
            }
        }
    }

}