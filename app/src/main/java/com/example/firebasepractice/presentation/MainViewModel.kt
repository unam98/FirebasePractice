package com.example.firebasepractice.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {


    val imageUrl = MutableLiveData<String>()
    val placeName = MutableLiveData<String>()


}