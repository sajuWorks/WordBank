package com.mobileapps.wordbank.data

import com.mobileapps.wordbank.model.ColorPalette

enum class SortType {
    ALPHANUMERIC,
    REVALPHANUMERIC,
    DATE,
    REVDATE,
}

data class OutfitGeneratorUiState(
    val colorPaletteList:List<ColorPalette> = listOf(),
    val selectedIndex:Int = 0,
    val navOpen:Boolean = false
//    val sortType:SortType = SortType.ALPHANUMERIC
)
