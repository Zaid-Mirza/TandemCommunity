package com.tandem.community.ui.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tandem.community.domain.models.CommunityMember
import coil3.compose.AsyncImage
import com.tandem.community.R
import com.tandem.community.ui.theme.TandemCommunityTheme


@Composable
fun CommunityScreen(
    modifier: Modifier = Modifier, communityViewModel: CommunityViewModel = hiltViewModel()
) {

    val members = communityViewModel.members.collectAsLazyPagingItems()
    CommunityContent(modifier = modifier, members = members, onThumbClick = { member ->
        communityViewModel.onMemberCardClicked(member.id, member.isLiked)
    })
}

@Composable
fun CommunityContent(
    modifier: Modifier = Modifier,
    members: LazyPagingItems<CommunityMember>,
    onThumbClick: (CommunityMember) -> Unit
) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(
            count = members.itemCount, key = members.itemKey { it.id }) { index ->
            val member = members[index]
            member?.let {
                CommunityMemberCard(modifier, member, onThumbClick)
            }

        }
    }
}


@Composable
fun CommunityMemberCard(
    modifier: Modifier = Modifier, member: CommunityMember, onThumbClick: (CommunityMember) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.outline)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar
            MemberAvatar(
                avatarUrl = member.pictureUrl ?: "", name = member.firstName ?: "--"
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Middle: name, topic, badges
            Column(modifier = Modifier.weight(1f)) {
                // Name row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Name
                    Text(
                        text = member.firstName ?: "",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    // Ref Count
                    ReferenceCount(member)
                }
                Spacer(modifier = Modifier.height(4.dp))

                // Topic
                Text(
                    text = member.topic ?: "",
                    fontSize = 13.sp,
                    color = colors.onSurfaceVariant,
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Language badges + thumb button
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LanguageBadge(
                        label = stringResource(R.string.native_txt),
                        value = member.natives.joinToString(" ").uppercase() ?: "--"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    LanguageBadge(
                        label = stringResource(R.string.learns_txt),
                        value = member.learns.joinToString(" ").uppercase() ?: "--"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ThumbUpButton(onClick = onThumbClick, member)
                }
            }
        }
    }
}


@Composable
private fun MemberAvatar(
    avatarUrl: String, name: String, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE0E0E0)), contentAlignment = Alignment.Center
    ) {
        if (avatarUrl.isNotBlank()) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "$name's avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Fallback to initials
            Text(
                text = name.firstOrNull()?.uppercase() ?: "?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ThumbUpButton(
    onClick: (CommunityMember) -> Unit, member: CommunityMember, modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    IconButton(
        onClick = {
            onClick(member)
        }, modifier = modifier.size(32.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.ThumbUp,
            contentDescription = "Like tutor",
            tint = if (member.isLiked) colors.primary else colors.outlineVariant,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun LanguageBadge(
    label: String, value: String, modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(colors.onSurfaceVariant)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = colors.outlineVariant,
            letterSpacing = 0.5.sp
        )
        Text(
            text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = colors.onSurface
        )
    }
}

@Composable
private fun ReferenceCount(member: CommunityMember, modifier: Modifier = Modifier) {

    val colors = MaterialTheme.colorScheme
    member.referenceCnt?.let {
        if (it > 0) Text(
            text = member.referenceCnt.toString(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = colors.onSurfaceVariant
        )
        else Text(
            modifier = Modifier
                .background(
                    color = colors.onSurfaceVariant,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
                .padding(8.dp),
            text = "New",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = colors.outlineVariant
        )

    }


}


// ---------------------------------------------------------------------------
// Previews (light + dark)
// ---------------------------------------------------------------------------

private val sampleMember = CommunityMember(
    id = 1,
    firstName = "Zaid",
    topic = "I can help you learn Urdu and German.",
    natives = arrayListOf("UR"),
    learns = arrayListOf("GE"),
    referenceCnt = 12
)

@Preview(showBackground = true, name = "Light")
@Composable
fun MemberCardLightPreview() {
    TandemCommunityTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CommunityMemberCard(modifier = Modifier, sampleMember, onThumbClick = {})
        }
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
fun MemberCardDarkPreview() {
    TandemCommunityTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CommunityMemberCard(modifier = Modifier, sampleMember, onThumbClick = {})
        }
    }
}