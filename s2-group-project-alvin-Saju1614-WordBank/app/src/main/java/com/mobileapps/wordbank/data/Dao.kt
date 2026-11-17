package com.mobileapps.wordbank.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mobileapps.wordbank.Outfit
import com.mobileapps.wordbank.model.ColorPalette
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert
    suspend fun insertKey(outfit: Outfit)

    @Query("SELECT * FROM Outfits")
    fun getAll(): Flow<List<Outfit>>

    @Delete
    suspend fun deleteKey(outfit: Outfit)


    @Query("SELECT colorPalette FROM Outfits WHERE `key` = :key")
    suspend fun getColorPaletteByKey(key: Int): ColorPalette

    @Query("SELECT name FROM Outfits WHERE `key` = :key")
    suspend fun getNameByKey(key: Int): String

    @Query("SELECT `key` FROM Outfits")
    suspend fun getAllKeys(): List<Int>
}