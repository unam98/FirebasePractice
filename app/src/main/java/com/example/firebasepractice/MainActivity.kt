package com.example.firebasepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getFirebaseData(){
        val db = Firebase.firestore
        val query = db.collectionGroup("cities").whereEqualTo("name", "San Francisco")
    }
}