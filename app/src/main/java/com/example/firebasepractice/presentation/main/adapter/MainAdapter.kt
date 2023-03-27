package com.example.firebasepractice.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ItemMainBinding
import com.example.util.callback.OnItemClick

class MainAdapter(context: Context, private val itemClickListener: OnItemClick) :
    ListAdapter<MainData, MainAdapter.ItemViewHolder>(Differ) {

    private lateinit var selectionTracker: SelectionTracker<Long>

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    inner class ItemViewHolder(
        val binding: ItemMainBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = absoluteAdapterPosition
                override fun getSelectionKey(): Long = itemId
            }

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

    fun setSelectionTracker(selectionTracker: SelectionTracker<Long>) {
        this.selectionTracker = selectionTracker
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(currentList[position])
        holder.bindViews(currentList[position])

        //근데 이걸 onBindViewHolder에 적어주는 게 맞나?
        with(holder) {
            binding.setVariable(BR.model, getItem(position))
            binding.root.setOnClickListener {
                selectionTracker.select(position.toLong())
            }
            binding.selected = selectionTracker.isSelected(position.toLong()) }
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
