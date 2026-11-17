package com.mobileapps.wordbank.model

import android.icu.text.SimpleDateFormat
import androidx.compose.ui.graphics.Color
import java.util.Date
import java.util.Locale

enum class OutfitComponent {
    SHIRT,
    TROUSERS,
    JACKET,
    TIE,
    SHOES
}

data class ClothingColor(
    val color:Color,
    val component:OutfitComponent
)

data class ColorPalette(val outfitColors:List<Color>, val dateCreated:Date) {

    val palette = listOf(
        ClothingColor(outfitColors[0], OutfitComponent.SHIRT),
        ClothingColor(outfitColors[1], OutfitComponent.JACKET),
        ClothingColor(outfitColors[2], OutfitComponent.TROUSERS),
        ClothingColor(outfitColors[3], OutfitComponent.TIE),
        ClothingColor(outfitColors[4], OutfitComponent.SHOES)
    )
    fun getFormattedDate() : String {
        val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z", Locale.US)
        return sdf.format(dateCreated)
    }
}