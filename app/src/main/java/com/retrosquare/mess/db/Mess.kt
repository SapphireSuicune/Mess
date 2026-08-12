package com.retrosquare.mess

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messes")
data class Mess(
    @PrimaryKey(autoGenerate = true) 
    val id: Int = 0,
    val sender: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)