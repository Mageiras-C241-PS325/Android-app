package com.capstone.mageiras.ui.result

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import com.capstone.mageiras.R
import com.capstone.mageiras.databinding.ActivityResultBinding
import com.capstone.mageiras.ui.PredictViewModelFactory
import com.capstone.mageiras.ui.camerax.CameraXActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import com.capstone.mageiras.data.Result

class ResultActivity : AppCompatActivity() {

    private var currentImageUri: Uri? = null
    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel

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

        val factory: PredictViewModelFactory = PredictViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[ResultViewModel::class.java]

        val localCurrentImageUri = currentImageUri
        if (localCurrentImageUri != null) {
            val file = File(localCurrentImageUri.path.toString())
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            viewModel.predictImage(body).observe(this@ResultActivity) { result ->
                when (result) {
                    is Result.Loading -> {
                        showToast("Loading...")
                    }

                    is Result.Success -> {
                        val response = result.data
                        showToast(response.toString())
                    }

                    is Result.Error -> {
                        showToast(result.error)
                    }

                    else -> {}
                }
            }
            showBottomSheetDialog()
        }

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