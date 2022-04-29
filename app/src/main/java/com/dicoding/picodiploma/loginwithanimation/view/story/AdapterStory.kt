package com.dicoding.picodiploma.loginwithanimation.view.story

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.databinding.AdapterStoryBinding

class AdapterStory(private var results: List<ListStory>) : RecyclerView.Adapter<AdapterStory.ViewHolder>() {

//    private lateinit var results: List<ListStory>
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.binding.tvName.text = result.name

        Glide.with(holder.itemView.context)
            .load(result.photoUrl)
            .centerCrop()
            .into(holder.binding.ivPreview)

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClick(result)
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    class ViewHolder(val binding : AdapterStoryBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setStories(story: List<ListStory>) {
        this.results = story
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClick(result: ListStory)
    }
}


