package com.jerryokafor.compose.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author <Author>
 * @Project <Project>
 */


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)