package com.example.firebasepractice.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasepractice.data.MainData

class MainViewModel : ViewModel() {

    val mainDataList = MutableLiveData<List<MainData>>()

    val inputName = MutableLiveData<String>()
    val inputImageUrl = MutableLiveData<String>()



}