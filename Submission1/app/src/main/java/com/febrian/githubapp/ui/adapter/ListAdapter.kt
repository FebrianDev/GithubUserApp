package com.febrian.githubapp.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.febrian.githubapp.R
import com.febrian.githubapp.ui.activity.DetailUserActivity.Companion.KEY_USER
import com.febrian.githubapp.data.entity.User
import com.febrian.githubapp.databinding.ItemListBinding
import com.febrian.githubapp.ui.activity.DetailUserActivity

class ListAdapter(private var list: ArrayList<User>, private var activity: Activity) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    inner class ViewHolder(private var binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(u: User) {
            binding.name.text = u.name.toString()
            binding.username.text = u.username.toString()
            val img = activity.resources.getIdentifier(u.avatar, null, activity.packageName)
            Glide.with(itemView).load(img).error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.image)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(KEY_USER, u)
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = list[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}