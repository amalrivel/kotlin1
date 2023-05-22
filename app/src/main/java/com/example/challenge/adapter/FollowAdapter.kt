package com.example.challenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge.R
import com.example.challenge.model.FollowResponseItem

class FollowAdapter(private val listUser: ArrayList<FollowResponseItem>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_follow, viewGroup, false)
        )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_avatar)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        val tvUrl: TextView = itemView.findViewById(R.id.tv_url)
    }

    override fun onBindViewHolder(holder: FollowAdapter.ViewHolder, position: Int) {
        holder.tvUsername.text = listUser[position].login
        holder.tvUrl.text = listUser[position].url
        Glide.with(holder.itemView.context)
            .load(listUser[position]?.avatarUrl)
            .into(holder.imgPhoto)
    }

    override fun getItemCount(): Int = listUser.size
}