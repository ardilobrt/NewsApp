package com.and.news.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.and.news.data.entity.Users

interface UserDao {

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<Users>

    @Query("SELECT * FROM Users WHERE username LIKE :username")
    fun getUsername(username: String?): Users

    @Insert(onConflict = REPLACE)
    fun insertUser(userNews: Users): Long

    @Update
    fun updateUser(userNews: Users): Int

    @Delete
    fun deleteUser(userNews: Users): Int
}