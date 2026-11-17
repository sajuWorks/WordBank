package com.mobileapps.wordbank

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobileapps.wordbank.model.ColorPalette

@Entity(tableName = "Outfits")
data class Outfit(
    @PrimaryKey(autoGenerate = true) val key: Int = 0,
    val name: String,
    val colorPalette: ColorPalette
)