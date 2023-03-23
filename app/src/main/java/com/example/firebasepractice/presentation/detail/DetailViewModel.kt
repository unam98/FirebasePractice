package com.example.firebasepractice.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasepractice.data.MainData

class DetailViewModel : ViewModel() {

    val mainData = MutableLiveData<MainData>()
}