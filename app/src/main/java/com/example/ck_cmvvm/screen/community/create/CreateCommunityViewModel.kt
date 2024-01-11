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

    fun uploadImageToFirebaseStorage(imageUri: Uri, title: String, content: String, time: Long, name: String) {
        val fileName = "images/${System.currentTimeMillis()}.jpg"
        val storageReference = FirebaseStorage.getInstance().getReference(fileName)

        _uploadUriState.value = UploadUriState.Loading
        storageReference.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUrl ->
                uploadCommunity(title, content, downloadUrl.toString(), time, name)
                _uploadUriState.value = UploadUriState.Success
            }
        }.addOnFailureListener {
            // 업로드 실패 처리
            _uploadUriState.value = UploadUriState.Error
        }
    }

    private fun uploadCommunity(title: String, content: String, imageUri: String, time: Long, name: String) {
        val model = CommunityModel(
            title,
            content,
            imageUri,
            time,
            name
        )
        val communityDB = FirebaseDatabase.getInstance().reference.child(DB_COMMUNITY)
        communityDB.push().setValue(model)
    }

}