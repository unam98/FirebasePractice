package com.example.firebasepractice.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.firebasepractice.R
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    lateinit var mainData: MainData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        //data binding variable
        //binding.model = viewModel
        binding.lifecycleOwner = this

        getIntentValue()
        addObserver()


    }

    private fun getIntentValue(){
        mainData = intent.getParcelableExtra("MainData")!!
        viewModel.mainData.value = mainData
    }

    private fun addObserver() {

        viewModel.mainData.observe(this){
            with(binding){
                tvDetail.text = it.placeName
                Glide.with(this@DetailActivity)
                    .load(it.imageUrl)
                    .centerCrop()
                    .into(ivDetail)
            }
        }

    }

}