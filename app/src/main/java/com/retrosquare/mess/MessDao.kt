package com.retrosquare.mess

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessDao {

    // Saves a new memory when shared from Google Messages
    @Insert
    suspend fun insertMess(mess: Mess)

    @Delete
    suspend fun deleteMess(mess: Mess)

    // Automatically emits an updated list whenever a new message is saved!
    @Query("SELECT * FROM messes ORDER BY timestamp DESC")
    fun getAllMesses(): Flow<List<Mess>>
}