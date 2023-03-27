package com.example.firebasepractice.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.firebasepractice.R
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ActivityDetailBinding
import com.example.firebasepractice.presentation.report.ReportActivity


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    lateinit var mainData: MainData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.lifecycleOwner = this

        getIntentValue()
        addObserver()
        startReport()
    }

    private fun startReport() {
        binding.btnStartReport.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }
            startActivity(intent)
        }
    }

    private fun getIntentValue() {
        mainData = intent.getParcelableExtra("MainData")!!
        viewModel.mainData.value = mainData
    }

    private fun addObserver() {

        viewModel.mainData.observe(this) {
            with(binding) {
                tvDetail.text = it.placeName
                Glide.with(this@DetailActivity)
                    .load(it.imageUrl)
                    .centerCrop()
                    .into(ivDetail)
            }
        }

    }

}