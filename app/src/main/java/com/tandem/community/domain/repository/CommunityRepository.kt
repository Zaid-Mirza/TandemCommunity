package com.tandem.community.domain.repository

import androidx.paging.PagingData
import com.tandem.community.domain.models.CommunityMember
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {

    fun getCommunityMembers() : Flow<PagingData<CommunityMember>>
    suspend fun toggleLike(memberId: Int,isCurrentlyLiked: Boolean)
}