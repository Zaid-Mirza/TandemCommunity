package com.tandem.community.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TandemResponseDto(
    @SerialName("response") var response: ArrayList<CommunityMemberDto> = arrayListOf(),
    @SerialName("errorCode") var errorCode: String? = null,
    @SerialName("type") var type: String? = null
)