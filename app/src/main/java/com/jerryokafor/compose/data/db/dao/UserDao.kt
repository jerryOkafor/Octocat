package com.jerryokafor.compose.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jerryokafor.compose.data.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * @Author <Author>
 * @Project <Project>
 */
@Dao
interface UserDao {
    @Insert
    fun insertUser(vararg userEntity: UserEntity)

    @Insert
    fun insertUser(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE id=:id")
    fun getUser(id: Int): Flow<UserEntity>

    @Query("SELECT * FROM users")
    fun users(): Flow<List<UserEntity>>
}