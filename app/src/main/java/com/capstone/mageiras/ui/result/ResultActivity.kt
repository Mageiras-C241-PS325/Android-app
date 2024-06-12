package com.capstone.mageiras.ui.result

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.capstone.mageiras.R
import com.capstone.mageiras.databinding.ActivityResultBinding
import com.capstone.mageiras.helper.ImageClassifierHelper
import com.capstone.mageiras.helper.TensorFlowLiteModel
import com.capstone.mageiras.ui.camerax.CameraXActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.detector.Detection

class ResultActivity : AppCompatActivity() {

    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private var currentImageUri: Uri? = null
    private lateinit var tfliteModel: TensorFlowLiteModel
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra(CameraXActivity.EXTRA_CAMERAX_IMAGE)
        currentImageUri = Uri.parse(imageUriString)

        binding.fabCancel.setOnClickListener {
            finish()
        }

        binding.ivPreview.setImageURI(currentImageUri)
//        currentImageUri.let {
//            analyzeImage()
//        }

        showBottomSheetDialog()

        tfliteModel = TensorFlowLiteModel(this, "best_float32.tflite")
//        var output  = FloatArray(2)
        CoroutineScope(Dispatchers.Main).launch {
            val imageBitmap = tfliteModel.toBitmap(currentImageUri!!)
            val output = tfliteModel.runModelOnImage(imageBitmap)
            println(output)
        }
//        Toast.makeText(this, output.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun analyzeImage() {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResults(results: List<Detection>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let {
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                println(it)
                                showToast(it.toString())
//                                Log.i("resultScan", it[0].categories[0].displayName)
//                                    it[0].categories.sortedByDescending { it?.score }
//                                val displayResult =
//                                    sortedCategories.joinToString("\n") {
//                                        "${it.label} " + NumberFormat.getPercentInstance()
//                                            .format(it.score).trim()
//                                    }
//                                moveToResult(displayResult)
                            } else {
                                showToast(getString(R.string.no_result_found))
                            }
                        }
                    }
                }
            }
        )
        currentImageUri?.let { this.imageClassifierHelper.classifyStaticImage(it) }
//        intent.putExtra(ResultActivity.EXTRA_RESULT, result)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_bottom_sheet, null)

        // Dapatkan tinggi layar
        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels

        // Atur tinggi BottomSheetDialog menjadi sama dengan tinggi layar
        val layoutParams = CoordinatorLayout.LayoutParams(
            CoordinatorLayout.LayoutParams.MATCH_PARENT,
            screenHeight
        )
        view.layoutParams = layoutParams

        bottomSheetDialog.setContentView(view)

        // Dapatkan BottomSheetBehavior dan atur isFitToContents menjadi false dan setExpandedOffset menjadi 0
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.setExpandedOffset(0)

        bottomSheetDialog.show()
    }

}