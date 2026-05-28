package com.tandem.community.domain.usecases

import androidx.compose.ui.unit.IntRect
import com.tandem.community.domain.repository.CommunityRepository
import javax.inject.Inject

class GetCommunityMembersUseCase  @Inject constructor(private val communityRepository: CommunityRepository ) {

    operator fun invoke() = communityRepository.getCommunityMembers()
}