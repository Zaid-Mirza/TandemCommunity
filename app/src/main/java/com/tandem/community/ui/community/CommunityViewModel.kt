package com.tandem.community.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

import com.tandem.community.domain.usecases.GetCommunityMembersUseCase
import com.tandem.community.domain.usecases.ToggleLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    getCommunityMembersUseCase: GetCommunityMembersUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase
) : ViewModel() {

    val members = getCommunityMembersUseCase()
        .cachedIn(viewModelScope)

    fun onMemberCardClicked(memberId: Int, isCurrentlyLiked: Boolean) {

        viewModelScope.launch {
            toggleLikeUseCase(memberId, isCurrentlyLiked)
        }
    }

}