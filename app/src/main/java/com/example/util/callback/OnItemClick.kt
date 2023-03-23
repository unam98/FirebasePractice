package com.example.util.callback

import com.example.firebasepractice.data.MainData

interface OnItemClick {
    fun selectItem(id: MainData)
}