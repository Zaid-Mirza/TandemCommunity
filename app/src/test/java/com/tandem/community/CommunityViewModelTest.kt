package com.tandem.community

import androidx.paging.PagingData
import com.tandem.community.domain.models.CommunityMember
import com.tandem.community.domain.repository.CommunityRepository
import com.tandem.community.domain.usecases.GetCommunityMembersUseCase
import com.tandem.community.domain.usecases.ToggleLikeUseCase
import com.tandem.community.ui.community.CommunityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class CommunityViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CommunityViewModel
    private lateinit var fakeRepository: FakeCommunityRepository
    private lateinit var getUseCase: GetCommunityMembersUseCase
    private lateinit var toggleUseCase: ToggleLikeUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeCommunityRepository()
        getUseCase = GetCommunityMembersUseCase(fakeRepository)
        toggleUseCase = ToggleLikeUseCase(fakeRepository)
        viewModel = CommunityViewModel(getUseCase, toggleUseCase)
    }

    @Test
    fun `onMemberCardClicked should call toggleLikeUseCase with correct parameters`() = runTest {
        // Given
        val memberId = 42
        val isLiked = true

        // When
        viewModel.onMemberCardClicked(memberId, isLiked)

        // Then
        assertTrue(fakeRepository.toggleLikeCalled)
        assertEquals(memberId, fakeRepository.lastMemberId)
        assertEquals(isLiked, fakeRepository.lastIsLiked)
    }

    private class FakeCommunityRepository : CommunityRepository {
        var toggleLikeCalled = false
        var lastMemberId = -1
        var lastIsLiked = false

        override fun getCommunityMembers(): Flow<PagingData<CommunityMember>> = flowOf(PagingData.empty())

        override suspend fun toggleLike(memberId: Int, isCurrentlyLiked: Boolean) {
            toggleLikeCalled = true
            lastMemberId = memberId
            lastIsLiked = isCurrentlyLiked
        }
    }
}