package com.tandem.community.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class TandemResponse(
    @SerialName("response") var response: ArrayList<CommunityMember> = arrayListOf(),
    @SerialName("errorCode") var errorCode: String? = null,
    @SerialName("type") var type: String? = null

)