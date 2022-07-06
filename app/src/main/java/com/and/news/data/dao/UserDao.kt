package com.and.news.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.and.news.data.entity.Users

@Dao
interface UserDao {

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<Users>

    @Query("SELECT * FROM Users WHERE email LIKE :email")
    fun getUsername(email: String?): Users

    @Insert(onConflict = REPLACE)
    fun insertUser(userNews: Users): Long

    @Update
    fun updateUser(userNews: Users): Int

    @Delete
    fun deleteUser(userNews: Users): Int
}