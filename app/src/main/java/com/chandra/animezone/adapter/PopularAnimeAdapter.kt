package com.chandra.animezone.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chandra.animezone.CONSTANTS
import com.chandra.animezone.R
import com.chandra.animezone.models.Response
import java.text.DecimalFormat

class PopularAnimeAdapter : ListAdapter<Response, PopularAnimeAdapter.AnimeViewHolder> (diffUtil){

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        context = parent.context
        Log.d(CONSTANTS.TAG, "Layout created")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val anime_poster = itemView.findViewById(R.id.anime_poster) as ImageView
        private val title = itemView.findViewById(R.id.anime_title) as TextView
        private val rating = itemView.findViewById(R.id.anime_rating) as TextView

        fun bind(data: Response?) {
            Glide.with(context)
                .load(data?.images?.jpg?.largeImageUrl)
                .placeholder(R.drawable.anime_item_template)
                .into(anime_poster)
            title.text = data?.title.toString()
            val df = DecimalFormat("#.#")
            val score = df.format(data?.score).toDouble()
            rating.text = String.format("%s/10", score)
        }

    }

    object diffUtil : DiffUtil.ItemCallback<Response>() {
        override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem == newItem
        }

    }


}