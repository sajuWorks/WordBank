package com.mobileapps.wordbank.repository

import com.mobileapps.wordbank.Outfit
import com.mobileapps.wordbank.datasource.OutfitLocalSource

class OutfitRepository(private val OutfitLocalSource: OutfitLocalSource) {
    //Insert the element to the DB
    suspend fun insertOutfit(Outfit: Outfit) {
        OutfitLocalSource.insert(Outfit)
    }

}