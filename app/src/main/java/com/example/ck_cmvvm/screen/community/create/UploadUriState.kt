package com.example.ck_cmvvm.screen.community.create

import android.net.Uri
import com.example.ck_cmvvm.model.community.CommunityModel

sealed class UploadUriState {

    object UnUninitialized : UploadUriState()

    object Loading: UploadUriState()

    object Success: UploadUriState()

    object Error: UploadUriState()

}