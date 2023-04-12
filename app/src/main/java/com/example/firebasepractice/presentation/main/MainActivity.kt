package com.example.firebasepractice.presentation.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.firebasepractice.R
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ActivityMainBinding
import com.example.firebasepractice.presentation.detail.DetailActivity
import com.example.firebasepractice.presentation.main.adapter.MainAdapter
import com.example.util.callback.OnItemClick
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber


class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    val db = Firebase.firestore // Cloud Firestore 인스턴스 초기화

    lateinit var inputData: HashMap<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.model = viewModel
        binding.lifecycleOwner = this

        initAdapter()
        addObserver()
        getFirebaseData()
        etController()
        addData()
        updateData()
        deleteData()

    }

    private fun initAdapter() {
        mainAdapter = MainAdapter(this)
        binding.recyclerViewMain.adapter = mainAdapter
    }

    private fun etController() {
        binding.etInputImage.addTextChangedListener {
            if (!binding.etInputImage.text.isNullOrEmpty()) {
                viewModel.inputImageUrl.value = binding.etInputImage.text.toString()
                Log.d(TAG, "inputImageUrl 값? : ${viewModel.inputImageUrl.value}")
            }
        }
        binding.etInputName.addTextChangedListener {
            if (!binding.etInputName.text.isNullOrEmpty()) {
                viewModel.inputName.value = binding.etInputName.text.toString()
                Log.d(TAG, "inputName 값? : ${viewModel.inputName.value}")
            }
        }
    }

    //firestore의 data를 갱신하는 함수
    private fun updateData() {
        binding.btnUpdateData.setOnClickListener {
            val washingtonRef = db.collection("data").document("5")

            //update 메서드의 field는 DB에서의 attribute, value는 새로 갱신할 값
            washingtonRef
                .update("name", "건대 보호소")
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
        }
    }

    //firestore의 data를 삭제하는 함수
    private fun deleteData() {
        binding.btnDeleteData.setOnClickListener {
            db.collection("data").document("5")
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        }
    }

    //firestore에 data를 추가하는 함수
    private fun addData() {
        binding.btnInputData.setOnClickListener {
            inputData = hashMapOf(
                "imageUrl" to "${viewModel.inputImageUrl.value}",
                "name" to "${viewModel.inputName.value}"
            )
            //지금 사용하고 싶은 add 메서드는 문서ID 값을 자동으로 생성하기 때문에 만약 내가 설정한 문서ID 값으로 data를 추가하고 싶으면 set 메서드를 사용해야 함.
            db.collection("data").add(inputData)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this@MainActivity, "data 추가 성공", Toast.LENGTH_SHORT).show()
                    Timber.tag(TAG).d("DocumentSnapshot written with ID: %s", documentReference.id)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@MainActivity, "data 추가 실패", Toast.LENGTH_SHORT).show()
                    Timber.tag(TAG).w(e, "Error adding document")
                }
        }
    }

    //firestore에서 data를 받아오는 함수
    private fun getFirebaseData() {
        db.collection("data")
            .addSnapshotListener { snapshot, e ->
                //get은 일회성 data 수신이지만 addSnapshotListener는 firestore의 data에 변경 사항이 있을 경우 실시간으로 받아옴
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (snapshot != null) { //snapshot은 변경 사항이 담긴 객체
                    val mainDataList = mutableListOf<MainData>()
                    for (document in snapshot) {
                        val imageUrl = document.getString("imageUrl")
                        val placeName = document.getString("name")
                        val mainData = MainData(
                            imageUrl = imageUrl.toString(),
                            placeName = placeName.toString()
                        )
                        mainDataList.add(mainData)
                    }
                    viewModel.mainDataList.value = mainDataList
                    Log.d(TAG, "뷰모델 mainDataList 값 : ${viewModel.mainDataList.value}")
                }


            }
    }


    private fun addObserver() {
        viewModel.mainDataList.observe(this) {
            mainAdapter.submitList(viewModel.mainDataList.value)
        }
    }


    override fun selectItem(id: MainData) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("MainData", id)
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        startActivity(intent)
    }
}