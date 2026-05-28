package com.tandem.community.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tandem.community.data.local.dao.MembersDao
import com.tandem.community.data.local.entities.MembersEntity
import com.tandem.community.data.remote.api.TandemApi
import com.tandem.community.data.remote.paging.CommunityPagingSource
import com.tandem.community.domain.models.CommunityMember
import com.tandem.community.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val tandemApi: TandemApi, private val membersDao: MembersDao
) : CommunityRepository {

    private var currentPagingSource: CommunityPagingSource? = null

    override fun getCommunityMembers(): Flow<PagingData<CommunityMember>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20
            ), pagingSourceFactory = {
                CommunityPagingSource(tandemApi, membersDao).also {
                    currentPagingSource = it
                }
            }).flow
    }

    override suspend fun toggleLike(memberId: Int, isCurrentlyLiked: Boolean) {

        if (isCurrentlyLiked) {
            membersDao.removeLike(memberId)
        } else {
            membersDao.insertLike(MembersEntity(memberId = memberId))
        }
        currentPagingSource?.invalidate()
    }
}
