package com.mobileapps.wordbank.ui.screens
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup.*
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.mobileapps.wordbank.R
import com.mobileapps.wordbank.ui.OutfitGeneratorViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <DrawView : View> OutfitGeneratorScreen(screenPadding:PaddingValues, viewModel: OutfitGeneratorViewModel) {
        val context = LocalContext.current
        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
        var recognizedText by remember { mutableStateOf("Recognizing...") }

        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                    )
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }
            },
            modifier = Modifier
                .width(260.dp)
                .height(900.dp),
            update = { previewView ->
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val drawView = (context as Activity).findViewById<DrawView>(R.id.drawView)

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->

                        }
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        context as LifecycleOwner, cameraSelector, preview, imageAnalyzer
                    )
                } catch (e: Exception) {
                    Log.e("CameraX", "Camera binding failed", e)
                }
            }
        )

        // Display recognized text
        Text(text = recognizedText, modifier = Modifier.padding(16.dp))
    }

class drawView(context: Context) : View(context) {
    val paint = Paint().apply {
        color = android.graphics.Color.RED
    }
    var boundingBoxes: List<Rect> = emptyList()

    fun setBoundingBoxes(boxes: List<Rect>) {
        boundingBoxes = boxes
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (box in boundingBoxes) {
            canvas.drawRect(box, paint)
        }
    }


}



