package com.tandem.community.ui.community

import com.tandem.community.domain.models.CommunityMember

data class CommunityState(
    val isLoading: Boolean = false,
    val members: ArrayList<CommunityMember>? = null,
    val errorMessage: String? = null,
)
