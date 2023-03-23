package com.example.firebasepractice.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ItemMainBinding
import com.example.util.callback.OnItemClick
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainAdapter(private val itemClickListener: OnItemClick) :
    ListAdapter<MainData, MainAdapter.MainItemViewHolder>(Differ) {


    inner class MainItemViewHolder(
        private val binding: ItemMainBinding,
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

        fun bindViews(data: MainData) {
            binding.root.setOnClickListener {
                itemClickListener.selectItem(data)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val view = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bindData(currentList[position])
        holder.bindViews(currentList[position])
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
