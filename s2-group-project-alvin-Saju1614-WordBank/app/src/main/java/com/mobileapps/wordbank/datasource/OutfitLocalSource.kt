package com.mobileapps.wordbank.datasource

import com.mobileapps.wordbank.Outfit
import com.mobileapps.wordbank.data.AppDao
import com.mobileapps.wordbank.model.ColorPalette
import kotlinx.coroutines.flow.Flow

class OutfitLocalSource(private val appDao: AppDao) {


    suspend fun insert(outfit: Outfit) {
        appDao.insertKey(outfit)
    }

    suspend fun delete(outfit: Outfit) {
        appDao.deleteKey(outfit)
    }

    fun getAll(): Flow<List<Outfit>> {
        return appDao.getAll()
    }

    suspend fun getColorPaletteByKey(key: Int):ColorPalette{
        return appDao.getColorPaletteByKey(key)
    }

    suspend fun getNameByKey(key: Int):String{
        return appDao.getNameByKey(key)
    }

    suspend fun getAllKeys(key: Int):List<Int>{
        return appDao.getAllKeys()
    }
}
