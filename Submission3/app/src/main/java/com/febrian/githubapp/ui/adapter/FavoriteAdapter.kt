package com.febrian.githubapp.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.febrian.githubapp.R
import com.febrian.githubapp.data.database.DataRoom
import com.febrian.githubapp.databinding.ItemListBinding
import com.febrian.githubapp.ui.activity.detail.DetailUserActivity

class FavoriteAdapter(private val list: ArrayList<DataRoom>, private val activity: Activity) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataRoom) {
            binding.username.text = data.username.toString()
            Glide.with(itemView).load(data.avatar).error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.image)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.KEY_USER, data.username.toString())
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return if (list.size > 10) 10 else list.size
    }
}