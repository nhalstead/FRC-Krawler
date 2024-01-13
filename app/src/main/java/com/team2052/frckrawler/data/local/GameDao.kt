package com.team2052.frckrawler.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Delete
    suspend fun delete(game: Game)

    @Upsert
    suspend fun insert(game: Game)

    @Query("SELECT * FROM game WHERE id = :id")
    suspend fun get(id: Int): Game

    @Query("SELECT * FROM game WHERE id = :id")
    fun getWithUpdates(id: Int): Flow<Game>

    @Query("SELECT * FROM game")
    fun getAll(): Flow<List<Game>>
}