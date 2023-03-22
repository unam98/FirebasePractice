package com.example.firebasepractice.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasepractice.data.MainData

class MainViewModel : ViewModel() {

    val mainData = MutableLiveData<MainData>()

    val imageUrl = MutableLiveData<String>()
    val placeName = MutableLiveData<String>()

}