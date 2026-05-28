package com.tandem.community.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "members", indices = [Index(value = ["memberId"], unique = true)])
data class MembersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val memberId: Int,
)