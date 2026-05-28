package com.tandem.community.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommunityMemberDto(
    @SerialName("id") var id: Int,
    @SerialName("topic") var topic: String? = null,
    @SerialName("firstName") var firstName: String? = null,
    @SerialName("pictureUrl") var pictureUrl: String? = null,
    @SerialName("natives") var natives: ArrayList<String> = arrayListOf(),
    @SerialName("learns") var learns: ArrayList<String> = arrayListOf(),
    @SerialName("referenceCnt") var referenceCnt: Int? = null
)
