package com.example.firebasepractice.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasepractice.data.MainData

class MainViewModel : ViewModel() {

    val mainDataList = MutableLiveData<List<MainData>>()


}