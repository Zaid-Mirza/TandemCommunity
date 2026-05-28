package com.tandem.community.data.mappers

import com.tandem.community.data.remote.dto.CommunityMemberDto
import com.tandem.community.data.remote.dto.TandemResponseDto
import com.tandem.community.domain.models.CommunityMember
import com.tandem.community.domain.models.TandemResponse


/* Note: Although both DTO and Domain objects looks same but it always a best practice
to create DTO objects to communicate with API because API response or field names could
change, and this should not affect domain models since the domain models are the ones
used by UI Layer. So any change from backend would require change only in DTOs and mappers
* */

/*------------- DTO to Domain --------*/


fun CommunityMemberDto.toCommunityMember(): CommunityMember {
    return CommunityMember(
        id = id ?: 0,
        topic = topic,
        firstName = firstName,
        pictureUrl = pictureUrl,
        natives = natives,
        learns = learns,
        referenceCnt = referenceCnt

    )
}

fun ArrayList<CommunityMemberDto>.toCommunityMembers() =
    ArrayList<CommunityMember>(this.map { it.toCommunityMember() })

fun TandemResponseDto.toTandemResponse(): TandemResponse {
    return TandemResponse(
        response = response.toCommunityMembers(),
        errorCode,
        type,


        )
}

