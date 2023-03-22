package com.example.firebasepractice.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasepractice.R
import com.example.firebasepractice.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val db = Firebase.firestore // Cloud Firestore 인스턴스 초기화


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getFirebaseData()
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
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}