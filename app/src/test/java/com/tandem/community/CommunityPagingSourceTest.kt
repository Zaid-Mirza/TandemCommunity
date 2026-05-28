package com.tandem.community

import androidx.paging.PagingSource
import com.tandem.community.data.local.dao.MembersDao
import com.tandem.community.data.local.entities.MembersEntity
import com.tandem.community.data.remote.api.TandemApi
import com.tandem.community.data.remote.dto.CommunityMemberDto
import com.tandem.community.data.remote.dto.TandemResponseDto
import com.tandem.community.data.remote.paging.CommunityPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CommunityPagingSourceTest {

    private lateinit var fakeApi: FakeTandemApi
    private lateinit var fakeDao: FakeMembersDao
    private lateinit var pagingSource: CommunityPagingSource

    @Before
    fun setUp() {
        fakeApi = FakeTandemApi()
        fakeDao = FakeMembersDao()
        pagingSource = CommunityPagingSource(fakeApi, fakeDao)
    }

    @Test
    fun `load should return Page when API call is successful`() = runTest {
        // Given: API returns 2 items, DAO says ID 1 is liked
        fakeApi.responseToReturn = createMockDto(listOf(1, 2))
        fakeDao.likedIdsToReturn = listOf(1)

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1, loadSize = 2, placeholdersEnabled = false
            )
        )
        // Then
        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(2, page.data.size)

        // Verify mapping logic (isLiked enrichment)
        assertTrue("Member 1 should be liked", page.data.find { it.id == 1 }?.isLiked == true)
        assertTrue("Member 2 should not be liked", page.data.find { it.id == 2 }?.isLiked == false)
    }

    @Test
    fun `load should return Error when API call fails`() = runTest {
        // Given
        fakeApi.shouldThrowError = true

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 2, placeholdersEnabled = false)
        )

        // Then
        assertTrue(result is PagingSource.LoadResult.Error)
    }

    // --- Helpers and Fakes ---

    private fun createMockDto(ids: List<Int>): TandemResponseDto {
        return TandemResponseDto(
            response = ArrayList(ids.map { id ->
                CommunityMemberDto(
                    id = id,
                    firstName = "Test Zaid $id",
                    pictureUrl = "",
                    topic = "",

                    )
            })
        )
    }

    private class FakeTandemApi : TandemApi {
        var responseToReturn: TandemResponseDto? = null
        var shouldThrowError = false

        override suspend fun getCommunityMembers(page: Int): TandemResponseDto {
            if (shouldThrowError) throw Exception("Network error")
            return responseToReturn!!
        }
    }

    private class FakeMembersDao : MembersDao {
        var likedIdsToReturn = listOf<Int>()

        override fun getMembersLikes(): Flow<List<MembersEntity>> {
            return flowOf(likedIdsToReturn.map { MembersEntity(memberId = it) })
        }

        override suspend fun insertLike(member: MembersEntity) {}
        override suspend fun removeLike(memberId: Int) {}

    }
}