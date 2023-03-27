package com.example.firebasepractice.presentation.report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasepractice.data.MainData

class ReportViewModel : ViewModel() {

    val mainDataList = MutableLiveData<List<MainData>>()


}