package com.example.firebasepractice.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.firebasepractice.R
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ActivityMainBinding
import com.example.firebasepractice.presentation.adapter.MainAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private val viewModel: MainViewModel by viewModels()


    val db = Firebase.firestore // Cloud Firestore 인스턴스 초기화

    val mainDataList = arrayListOf<MainData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.model = viewModel
        binding.lifecycleOwner = this

        mainAdapter = MainAdapter()
        binding.recyclerViewMain.adapter = mainAdapter

        addObserver()
        getFirebaseData()

    }

    private fun getFirebaseData() {
        db.collection("data")
            .get()
            .addOnSuccessListener { result -> // data 일괄 수신
                for (document in result) {
                    val imageUrl = document.data.get("imageUrl") // attribute 별 data 수신
                    val placeName = document.data.get("name")

                    val inputData =
                        MainData(imageUrl = imageUrl.toString(), placeName = placeName.toString())
                    mainDataList.add(inputData)
                }
                viewModel.mainDataList.value = mainDataList
                Log.d(TAG, "mainDataList 값 : $mainDataList")
                Log.d(TAG, "뷰모델 mainDataList 값 : ${viewModel.mainDataList.value}")
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun addObserver() {
        viewModel.mainDataList.observe(this) {
            mainAdapter.submitList(viewModel.mainDataList.value)
        }
    }
}