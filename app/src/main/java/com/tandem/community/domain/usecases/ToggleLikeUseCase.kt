package com.tandem.community.domain.usecases

import com.tandem.community.domain.repository.CommunityRepository
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(
    private val repository: CommunityRepository
) {

    suspend operator fun invoke(memberId: Int, isCurrentlyLiked: Boolean) =
        repository.toggleLike(memberId, isCurrentlyLiked)
}