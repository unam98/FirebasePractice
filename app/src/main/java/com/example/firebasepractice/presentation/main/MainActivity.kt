package com.example.firebasepractice.presentation.main

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.selection.SelectionTracker
import com.example.firebasepractice.R
import com.example.firebasepractice.data.MainData
import com.example.firebasepractice.databinding.ActivityMainBinding
import com.example.firebasepractice.presentation.detail.DetailActivity
import com.example.firebasepractice.presentation.main.adapter.MainAdapter
import com.example.firebasepractice.presentation.main.adapter.setSelectionTracker
import com.example.util.callback.OnItemClick
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber


class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    lateinit var selectionTracker: SelectionTracker<Long>


    val db = Firebase.firestore // Cloud Firestore 인스턴스 초기화

    val mainDataList = arrayListOf<MainData>()

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
//        deleteData()
        addTrackerObserver() //selection

    }

    //https://cdn.pixabay.com/photo/2017/09/25/13/12/puppy-2785074__480.jpg 강남 보호소

    //http://img.seoul.co.kr//img/upload/2021/04/30/SSI_20210430195948.jpg 제주도 보호소 5
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

    private fun addData() {
        binding.btnInputData.setOnClickListener {
            inputData = hashMapOf(
                "imageUrl" to "${viewModel.inputImageUrl.value}",
                "name" to "${viewModel.inputName.value}"
            )
            //add 메서드는 문서ID 값을 자동으로 생성하기 때문에 내가 설정한 문서ID 값으로 data를 추가하고 싶으면 set 메서드를 사용해야 함.
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


    private fun getFirebaseData() {
        db.collection("data")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                val mainDataList = mutableListOf<MainData>()
                for (document in snapshot!!) {//!! 안 붙이면 에러 떠서
                    val imageUrl = document.getString("imageUrl")
                    val placeName = document.getString("name")
                    val mainData = MainData(imageUrl = imageUrl.toString(), placeName = placeName.toString())
                    mainDataList.add(mainData)
                }
                viewModel.mainDataList.value = mainDataList
                Log.d(TAG, "뷰모델 mainDataList 값 : ${viewModel.mainDataList.value}")
            }
    }



    private fun addObserver() {
        viewModel.mainDataList.observe(this) {
            mainAdapter.submitList(viewModel.mainDataList.value)
        }
    }

    //SelectionTracker.kt로 빼준 함수 활용
    private fun addTrackerObserver() {
        selectionTracker =
            setSelectionTracker("StorageMyDrawSelectionTracker", binding.recyclerViewMain)
        selectionTracker.addObserver((object : SelectionTracker.SelectionObserver<Long>() {


            override fun onSelectionChanged() {
                super.onSelectionChanged()
                val items = selectionTracker.selection.size()
                Log.d(ContentValues.TAG, "items 사이즈?: $items")
                if (items == 0) {
//                    binding.btnDelete.setBackgroundResource(R.drawable.radius_10_g3_button)
                    binding.enabled = false
                } else if (items >= 1) {
//                    binding.btnDelete.setBackgroundResource(R.drawable.radius_10_m1_button)
                    binding.enabled = true
                } // 선택된 아이템이 1개 이상일 경우 button 활성화 (floating button하고 그냥 버튼하고 기본 지원 옵션이 좀 다른 듯함.)

            }
        }))
        mainAdapter.setSelectionTracker(selectionTracker) //어댑터 생성 후 할당해줘야 한다는 순서지킴
    }


//    private fun deleteData() {
//        binding.btnDelete.setOnClickListener {
//            mainAdapter.removeItem(selectionTracker.selection)
//            selectionTracker.clearSelection()
//        }
//    }


    override fun selectItem(id: MainData) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("MainData", id)
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        startActivity(intent)
    }
}