package com.tandem.community.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tandem.community.data.local.dao.MembersDao
import com.tandem.community.data.mappers.toCommunityMembers
import com.tandem.community.data.remote.api.TandemApi
import com.tandem.community.domain.models.CommunityMember
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CommunityPagingSource @Inject constructor(
    private val tandemApi: TandemApi,
    private val membersDao: MembersDao
) : PagingSource<Int, CommunityMember>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CommunityMember> {

        return try {

            val page = params.key ?: 1

            val response = tandemApi.getCommunityMembers(page)

            val items = response.response.toCommunityMembers()

            val likedMemberIds = membersDao.getMembersLikes().first().map { it.memberId }.toHashSet()

            val mappedItems = items.map { member ->
                member.copy(
                    isLiked = member.id in likedMemberIds
                )
            }

            LoadResult.Page(
                data = mappedItems,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.size < 20) null else page + 1
            )

        } catch (e: Exception) {

            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, CommunityMember>
    ): Int? {

        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}