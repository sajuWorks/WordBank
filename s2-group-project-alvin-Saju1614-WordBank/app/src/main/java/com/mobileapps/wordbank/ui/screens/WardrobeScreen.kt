package com.mobileapps.wordbank.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.mobileapps.wordbank.data.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




@Composable
fun WardrobeScreen(screenPadding: PaddingValues) {
    val context = LocalContext.current
    val db = AppDataBase.getDatabase(context).Dao()
    val keysAndColors = remember { mutableStateOf(emptyMap<Int, List<Color>>()) }
    val name = remember {mutableStateOf("")}


    // Fetch outfits and update size and keys/colors
    LaunchedEffect(Unit) {
        db.getAll().collect { outfits ->
            //size.value = outfits.size
            Log.d("DatabaseLog", "Outfits: $outfits")

            val keys = db.getAllKeys()
            val colorMap = keys.associateWith { key -> db.getColorPaletteByKey(key).outfitColors }
            keysAndColors.value = keys.associateWith { key ->
                colorMap[key] ?: emptyList()
            }
        }
    }

    Surface(modifier = Modifier.padding(screenPadding)) {
        // Your main UI content
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(screenPadding),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(keysAndColors.value.keys.size) { index ->
                val key = keysAndColors.value.keys.toList()[index]
                val colors = keysAndColors.value[key] ?: emptyList()

                LaunchedEffect(key) {
                    CoroutineScope(Dispatchers.IO).launch {
                        name.value = db.getNameByKey(key)
                        Log.d("name",db.getNameByKey(key))
                    }
                }

                //WardrobeRow(key = key, name = name.value ,colors = colors)
            }

        }
    }
}
