package com.retrosquare.mess

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "collections")
data class MessCollection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity(
    tableName = "mess_collections",
    primaryKeys = ["messId", "collectionId"],
    indices = [Index("collectionId"), Index("messId")]
)
data class MessInCollection(
    val messId: Int,
    val collectionId: Int
)