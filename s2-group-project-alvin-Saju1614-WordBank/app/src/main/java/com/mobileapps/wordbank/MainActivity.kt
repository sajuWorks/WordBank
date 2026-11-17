package com.mobileapps.wordbank

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.compose.FiggysFitsTheme

class MainActivity : ComponentActivity() {

    // Permission launcher
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val permissionGranted = permissions.all { it.key in REQUIRED_PERMISSIONS && it.value }

            if (permissionGranted) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Light nav bar for light theme
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = true

        // Full screen layout
        enableEdgeToEdge()

        // Ask for permissions if needed
        requestPermissionsIfNeeded()

        // Load Jetpack Compose content
        setContent {
            FiggysFitsTheme {
                OutfitGeneratorApp()
            }
        }
    }

    private fun requestPermissionsIfNeeded() {
        if (!hasAllPermissions()) {
            activityResultLauncher.launch(REQUIRED_PERMISSIONS)
        } else {
            startCamera()
        }
    }

    private fun hasAllPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun startCamera() {
        // TODO: Implement camera startup logic here
        Toast.makeText(this, "Camera started", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    FiggysFitsTheme {
        OutfitGeneratorApp()
    }
}
