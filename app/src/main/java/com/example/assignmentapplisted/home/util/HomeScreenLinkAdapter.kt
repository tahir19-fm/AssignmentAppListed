package com.example.assignmentapplisted.home.util

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentapplisted.R
import com.example.assignmentapplisted.databinding.LinkCardBinding
import com.example.assignmentapplisted.home.data.RvLinkModalClass

class HomeScreenAdapter(private var items : List<RvLinkModalClass>) : RecyclerView.Adapter<HomeScreenAdapter.HomeViewHolder>()
{
    class HomeViewHolder(val binding : LinkCardBinding)  : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
      return HomeViewHolder(LinkCardBinding.inflate(LayoutInflater.from(parent.context ) , parent , false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item=items[position]
        holder.binding.tvHeading.text = item.heading
        holder.binding.tvDescription.text= item.description.split("T")[0]
        holder.binding.tvLink.text = item.link
        Glide.with(holder.binding.ivCardLink.context)
            .load(item.imageurl)
            .centerCrop()
            .placeholder(R.drawable.add_icon)
            .into(holder.binding.ivCardLink)

    }

    override fun getItemCount(): Int {
       return items.size
    }
    fun updateData(data : MutableList<RvLinkModalClass>){
        items = data
        notifyItemRangeChanged(0, itemCount)
    }



}
class WrapContentLinearLayoutManager(context:Context) : LinearLayoutManager(context) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("TAG", " in RecyclerView")
        }
    }
}