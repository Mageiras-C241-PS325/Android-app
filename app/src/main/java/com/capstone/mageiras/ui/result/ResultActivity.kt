package com.capstone.mageiras.ui.result

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.mageiras.R
import com.capstone.mageiras.databinding.ActivityResultBinding
import com.capstone.mageiras.ui.camerax.CameraXActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class ResultActivity : AppCompatActivity() {


    private var currentImageUri: Uri? = null

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra(CameraXActivity.EXTRA_CAMERAX_IMAGE)
        currentImageUri = Uri.parse(imageUriString)

        binding.ivPreview.setImageURI(currentImageUri)

        showBottomSheetDialog()

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