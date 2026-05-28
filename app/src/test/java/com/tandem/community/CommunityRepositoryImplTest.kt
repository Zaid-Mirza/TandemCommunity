package com.tandem.community

import com.tandem.community.data.local.dao.MembersDao
import com.tandem.community.data.local.entities.MembersEntity
import com.tandem.community.data.remote.api.TandemApi
import com.tandem.community.data.remote.dto.TandemResponseDto
import com.tandem.community.data.repository.CommunityRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CommunityRepositoryImplTest {

    private lateinit var fakeDao : FakeMembersDao

    private lateinit var fakeApi: TandemApi
    private lateinit var repository: CommunityRepositoryImpl

    @Before
    fun setUp() {
        fakeDao = FakeMembersDao()
        fakeApi = mockk()
        repository = CommunityRepositoryImpl(fakeApi, fakeDao)
    }

    @Test
    fun `toggleLike should call insertLike when isCurrentlyLiked is false`() = runTest {
        // Given
        val memberId = 1
        val isCurrentlyLiked = false

        // When
        repository.toggleLike(memberId, isCurrentlyLiked)

        // Then
        Assert.assertEquals(memberId, fakeDao.insertedId)
    }

    @Test
    fun `toggleLike should call removeLike when isCurrentlyLiked is true`() = runTest {
        // Given
        val memberId = 1
        val isCurrentlyLiked = true

        // When
        repository.toggleLike(memberId, isCurrentlyLiked)

        // Then
        Assert.assertEquals(memberId, fakeDao.removedId)
    }


    // Created to avoid any changes in real MembersDao class, for sake of this project
    private class FakeMembersDao : MembersDao {
        var insertedId: Int? = null
        var removedId: Int? = null

        override suspend fun insertLike(member: MembersEntity) {
            insertedId = member.memberId
        }

        override suspend fun removeLike(memberId: Int) {
            removedId = memberId
        }

        override fun getMembersLikes(): Flow<List<MembersEntity>> = flowOf(emptyList())
    }

    private class FakeTandemApi : TandemApi {
        override suspend fun getCommunityMembers(page: Int): TandemResponseDto {
            throw NotImplementedError("Not needed for toggleLike tests")
        }
    }
}