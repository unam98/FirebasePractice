package com.example.firebasepractice.presentation.main.adapter

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView


class ItemMainLookUp(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(motionEvent: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)
        if (view != null) {
            val viewHolder  =
                recyclerView.getChildViewHolder(view) as MainAdapter.ItemViewHolder
            return viewHolder.getItemDetails()
        }
        return null
    }
}