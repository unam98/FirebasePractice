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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainAdapter :
    ListAdapter<MainData, MainAdapter.MainItemViewHolder>(Differ) {

    inner class MainItemViewHolder(
        private val binding: ItemMainBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: MainData) {
            with(binding) {
                tvMain.text = data.placeName
                Glide.with(itemView)
                    .load(data.imageUrl)
                    .thumbnail(0.3f).format(DecodeFormat.PREFER_RGB_565)
                    .centerCrop()
                    .into(ivMain)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val view = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bindData(currentList[position])
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
