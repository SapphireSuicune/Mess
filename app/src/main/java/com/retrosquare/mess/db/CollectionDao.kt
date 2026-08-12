package com.retrosquare.mess

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Insert
    suspend fun addCollection(coll: MessCollection)

    @Delete
    suspend fun deleteCollection(coll: MessCollection)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMessToCollection(link: MessInCollection)

    @Delete
    suspend fun removeMessFromCollection(link: MessInCollection)

    @Query("SELECT * FROM collections ORDER BY name")
    fun getAllCollections(): Flow<List<MessCollection>>

    @Query("""
        SELECT m.* from messes m
        INNER JOIN mess_collections mc ON m.id = mc.messId
        WHERE mc.collectionId = :collectionId
        ORDER BY m.timestamp DESC
    """)
    fun getMessesInCollection(collectionId: Int): Flow<List<Mess>>
}