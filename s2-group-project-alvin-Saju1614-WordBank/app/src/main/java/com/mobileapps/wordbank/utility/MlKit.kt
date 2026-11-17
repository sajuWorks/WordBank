package com.mobileapps.wordbank.utility

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.util.Log
import android.view.View
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.ByteArrayOutputStream




@OptIn(ExperimentalGetImage::class)
fun mlKit(imageProxy: ImageProxy, onTextRecognized: (String) -> Unit) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        // Pass image to ML Kit for processing
        //NEVER CHANGE
        recognizer.process(image)

            //When the text is recognized successfully
            .addOnSuccessListener { visionText ->
                onTextRecognized(visionText.text) // Display detected text
            }

            //NEVER CHANGE
            .addOnFailureListener { e ->
                Log.e("ML Kit", "Text recognition failed", e)
            }
            .addOnCompleteListener {
                imageProxy.close() // Always close ImageProxy
            }
    }
}

@OptIn(ExperimentalGetImage::class)
fun highlightText(imageProxy: ImageProxy, DrawView: View, onTextRecognized: (String) -> Unit) {
    val bitmap = imageProxyToBitmap(imageProxy)
        val image = bitmap?.let { InputImage.fromBitmap(it, imageProxy.imageInfo.rotationDegrees) }
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)


    val mutableBitmap = bitmap?.copy(Bitmap.Config.ARGB_8888, true)
    val canvas = mutableBitmap?.let { Canvas(it) }
    var boundingBoxes= mutableListOf<Rect>()

        // Pass image to ML Kit for processing
        //NEVER CHANGE
    if (image != null) {
        recognizer.process(image)
            //When the text is recognized successfully
            .addOnSuccessListener { text ->

                    //Goes through each block and separates them into elements(words)
                    for (block in text.textBlocks) {
                        for (line in block.lines){
                            for(element in line.elements){
                                boundingBoxes.add(element.boundingBox ?: Rect())
                            }
                        }
                    }
                DrawView.setBoundingBoxes(boundingBoxes)
            }
            //NEVER CHANGE
            .addOnFailureListener { e ->
                Log.e("ML Kit", "Text recognition failed", e)
            }
            .addOnCompleteListener {
                imageProxy.close() // Always close ImageProxy(reset)
            }
    }
}




//Helper function to change image to bitmap
fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap? {
    val yBuffer = imageProxy.planes[0].buffer
    val uBuffer = imageProxy.planes[1].buffer
    val vBuffer = imageProxy.planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, imageProxy.width, imageProxy.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, imageProxy.width, imageProxy.height), 100, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}


