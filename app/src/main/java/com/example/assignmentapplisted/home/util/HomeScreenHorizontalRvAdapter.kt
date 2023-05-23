package com.example.assignmentapplisted.home.util

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentapplisted.databinding.HorizontalItemBinding
import com.example.assignmentapplisted.home.data.RvModalHorizontal

class HomeScreenHorizontalRvAdapter(private val items : List<RvModalHorizontal>)  : RecyclerView.Adapter<HomeScreenHorizontalRvAdapter.HomeViewHolder>() {

    private val cardColors = arrayOf("#1F5C33CF", "#1F0E6FFF", "#0000FF", "#1Fffff00")
    class  HomeViewHolder(val binding: HorizontalItemBinding ) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenHorizontalRvAdapter.HomeViewHolder{
        return HomeViewHolder(HorizontalItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
      val item=items[position]
        holder.binding.tvDataValue.text=item.content
        holder.binding.tvDataTheme.text=item.title
        holder.binding.ivCard.setImageResource(item.img)
        val color = Color.parseColor(cardColors[position % cardColors.size])
    }

    override fun getItemCount(): Int {
     return items.size
    }
}
