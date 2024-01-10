package com.example.ck_cmvvm.screen.community.create

import com.example.ck_cmvvm.model.community.CommunityModel

sealed class CreateCommunityState{

    object UnUninitialized : CreateCommunityState()

    object Loading: CreateCommunityState()

    data class Success(
        val communityData: CommunityModel
    ): CreateCommunityState()

    object Error: CreateCommunityState()
}
