package com.example.firebasepractice.presentation.main.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasepractice.R
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ItemMainBinding
import com.example.util.callback.OnItemClick
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainAdapter(private val itemClickListener: OnItemClick) :
    ListAdapter<MainData, MainAdapter.ItemViewHolder>(Differ) {
    //context 있길래 따라 적긴 했는데 필요 없는 듯..?

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

    fun removeItem(selection: Selection<Long>) {
        val currentList = currentList.toMutableList()
        val itemList = mutableListOf<MainData>()
        selection.forEach {
            itemList.add(currentList[it.toInt()])
        }
        currentList.removeAll(itemList)
        submitList(currentList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(currentList[position])
        holder.bindViews(currentList[position])

        with(holder) {
            binding.setVariable(BR.model, getItem(position))
            binding.root.setOnClickListener {
                selectionTracker.select(position.toLong())
                //선택한 item 로그, 근데 최초 선택한 것만 출력됨. 이후 중복 선택된 것들은 안 나옴
                Log.d(ContentValues.TAG, "선택된 itemId 값 : $itemId")

            }
            binding
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
