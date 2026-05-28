package com.tandem.community.data.remote.api

import com.tandem.community.data.remote.dto.TandemResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TandemApi {

    @GET("community_{page}.json")
    suspend fun getCommunityMembers(@Path("page") page: Int): TandemResponseDto
}