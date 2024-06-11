package com.capstone.mageiras.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.nfc.Tag
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.task.vision.detector.Detection
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TensorFlowLiteModel(private val context: Context, private val modelName: String) {

    private var tflite: Interpreter? = null
    private var imageSizeX: Int = 224
    private var imageSizeY: Int = 224

    val modelFile: MappedByteBuffer = FileUtil.loadMappedFile(context, modelName)

    init {
        tflite = Interpreter(modelFile)
    }

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    suspend fun runModelOnImage(bitmap: Bitmap): FloatArray = withContext(Dispatchers.Default) {

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR)) // Resize image
            .add(NormalizeOp(0f, 255f)) // Normalize image
            .add(CastOp(DataType.FLOAT32))
            .build()
        val tImage = imageProcessor.process(tensorImage)
        val tfliteModel = tflite ?: throw RuntimeException("TFLite model is not initialized.")
        val output = FloatArray(2)
        try {
            tfliteModel.run(tImage.buffer.rewind(), output)
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error running model", Toast.LENGTH_SHORT).show()
            }
        }
        println(output)
        Log.d("output", output.joinToString(prefix = "[", postfix = "]") { it.toString() })
        return@withContext output
    }

    fun toBitmap(uri: Uri): Bitmap {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
        return bitmap.copy(Bitmap.Config.ARGB_8888, true)
    }
}