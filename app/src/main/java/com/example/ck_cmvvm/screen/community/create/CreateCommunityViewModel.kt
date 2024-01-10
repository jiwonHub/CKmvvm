package com.example.ck_cmvvm.screen.community.create

import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ck_cmvvm.data.DBKey.Companion.DB_COMMUNITY
import com.example.ck_cmvvm.model.community.CommunityModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class CreateCommunityViewModel: BaseViewModel() {

    private val _uploadUriState = MutableLiveData<UploadUriState>(UploadUriState.UnUninitialized)
    val uploadUriState: LiveData<UploadUriState> = _uploadUriState

    private fun uploadPhoto(
        uri: Uri,
    ){
        val fileName = "${System.currentTimeMillis()}.png"
        val storage: FirebaseStorage = Firebase.storage
        _uploadUriState.value = UploadUriState.Loading
        storage.reference.child("community/photo").child(fileName)
            .putFile(uri)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    storage.reference.child("community/photo").child(fileName)
                        .downloadUrl
                        .addOnSuccessListener {
                            _uploadUriState.value = UploadUriState.Success
                        }
                        .addOnFailureListener {
                            _uploadUriState.value = UploadUriState.Error
                        }
                }
            }
    }

    fun uploadCommunity( // 게시글 업로드 함수
        title: String,      // 제목
        content: String,    // 내용
        imageUri: String,   // 사진 uri
        time: Long,
        name: String
    ) { // 게시글 업로드
        uploadPhoto(imageUri.toUri())
        val model = CommunityModel(
            title,
            content,
            imageUri,
            time,
            name
        ) // 게시글 데이터 형식으로 받아온 값들을 model에 저장
        val communityDB = FirebaseDatabase.getInstance().reference.child(DB_COMMUNITY)
        communityDB.push().setValue(model) // 최종적으로 DB에 푸쉬
    }

}