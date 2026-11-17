package com.mobileapps.wordbank.ui

import androidx.lifecycle.ViewModel
import com.mobileapps.wordbank.data.OutfitGeneratorUiState
import com.mobileapps.wordbank.model.ColorPalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OutfitGeneratorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OutfitGeneratorUiState())
    val uiState: StateFlow<OutfitGeneratorUiState> = _uiState.asStateFlow()

    fun updateColorPaletteList(newColorPaletteList:List<ColorPalette>) {
        _uiState.update { currentState ->
            currentState.copy(
                colorPaletteList = newColorPaletteList
            )
        }
    }

    fun updateSelectedIndex(newSelectedIndex:Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedIndex = newSelectedIndex
            )
        }
    }
}