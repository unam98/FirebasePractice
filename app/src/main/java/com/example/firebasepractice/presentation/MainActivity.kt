package com.example.firebasepractice.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.firebasepractice.R
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    val db = Firebase.firestore // Cloud Firestore 인스턴스 초기화


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getFirebaseData()
        addObserver()
    }


    private fun getFirebaseData() {
        db.collection("data")
            .get()
            .addOnSuccessListener { result -> // data 일괄 수신
                for (document in result) {
                    val imageUrl = document.data.get("image_url") // attribute 별 data 수신
                    val placeName = document.data.get("name")
                    Log.d(TAG, "image_url 값 : $imageUrl")
                    Log.d(TAG, "placeName 값 : $placeName")

                    viewModel.mainData.value = MainData(imageUrl = imageUrl.toString(), placeName = placeName.toString())
                    viewModel.imageUrl.value = imageUrl.toString()
                    viewModel.placeName.value = placeName.toString()

                    Log.d(TAG, "뷰모델 mainData 값 : ${viewModel.mainData.value}")
                    Log.d(TAG, "뷰모델 image_url 값 : ${viewModel.imageUrl.value}")
                    Log.d(TAG, "뷰모델 placeName 값 : ${viewModel.placeName.value}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun addObserver(){
        viewModel.mainData.observe(this){
            with(binding){
                tvMain.text = it.placeName

                Glide.with(this@MainActivity)
                    .load("https://dimg.donga.com/wps/NEWS/IMAGE/2022/01/28/111500268.2.jpg")
                    .into(ivMain)
            }
        }
    }
}