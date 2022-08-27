package com.afterclass.storyviewer

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afterclass.storyviewer.databinding.StoryViewBinding
import com.bumptech.glide.Glide

class StoryViewPagerAdapter:ListAdapter<Image,StoryViewPagerAdapter.StoryViewHolder>(
    object:DiffUtil.ItemCallback<Image>(){
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return  oldItem.toString() == newItem.toString()
        }

    }
) {
    private var _context:Context?=null

    class StoryViewHolder(val binding:StoryViewBinding):RecyclerView.ViewHolder(binding.root)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val inflater = parent.context.getSystemService(LayoutInflater::class.java)
        val binding = StoryViewBinding.inflate(inflater,parent,false)
        _context=parent.context
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
            val image = getItem(position)
           Glide.with(_context!!).load(image.PATH).into(holder.binding.ivStory)

    }
}