package com.example.firebasepractice.presentation.main.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ItemMainBinding
import com.example.util.callback.OnItemClick

class MainAdapter(private val itemClickListener: OnItemClick) :
    ListAdapter<MainData, MainAdapter.ItemViewHolder>(Differ) {


    inner class ItemViewHolder(
        val binding: ItemMainBinding,
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bindData(data: MainData) {
            with(binding) {
                tvMain.text = data.placeName
                Glide.with(itemView)
                    .load(data.imageUrl)
                    .centerCrop()
                    .into(ivMain)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(currentList[position])

        with(holder) {
            binding.root.setOnClickListener {
                itemClickListener.selectItem(currentList[position])
                Log.d(ContentValues.TAG, "선택된 itemId 값 : $itemId")

            }
        }
    }

    object Differ : DiffUtil.ItemCallback<MainData>() {
        override fun areItemsTheSame(
            oldItem: MainData,
            newItem: MainData,
        ): Boolean {
            return oldItem.placeName == newItem.placeName
        }

        override fun areContentsTheSame(
            oldItem: MainData,
            newItem: MainData,
        ): Boolean {
            return oldItem == newItem
        }

    }


}
