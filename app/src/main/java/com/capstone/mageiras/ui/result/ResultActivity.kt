package com.capstone.mageiras.ui.result

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.capstone.mageiras.databinding.DialogBottomSheetBinding
import com.capstone.mageiras.adapter.ListIngredientsAdapter
import com.capstone.mageiras.data.remote.response.IngredientsItem
import com.capstone.mageiras.ui.success.SuccessActivity
import com.google.gson.Gson
import okhttp3.RequestBody

class ResultActivity : AppCompatActivity() {

    private var currentImageUri: Uri? = null
    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel
    private lateinit var file: File
    private var output: List<String> = emptyList()

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
            if (intent.hasExtra(CameraXActivity.FROM_CAMERA)) {
                file = File(localCurrentImageUri.path.toString())
            }else{
                val imagePath = getPathFromUri(this, localCurrentImageUri)
                file = File(imagePath)
            }

            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)


            viewModel.predictImage(body).observe(this@ResultActivity) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.loadingScan.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.loadingScan.visibility = View.GONE
                        output = result.data
                        showBottomSheetDialog()
                    }
                    is Result.Error -> {
                        binding.loadingScan.visibility = View.GONE
                        showToast(result.error)
                    }
                }
            }
        }
    }

    private fun getPathFromUri(context: Context, uri: Uri): String {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val path = cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
        cursor?.close()
        return path ?: ""
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)

        val binding = DialogBottomSheetBinding.inflate(layoutInflater)

        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels

        val layoutParams = CoordinatorLayout.LayoutParams(
            CoordinatorLayout.LayoutParams.MATCH_PARENT,
            screenHeight
        )
        binding.root.layoutParams = layoutParams

        bottomSheetDialog.setContentView(binding.root)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.setExpandedOffset(0)

        bottomSheetDialog.show()

        val what = output.groupingBy { it }.eachCount()
        val list = what.map { (name, amount) ->
            IngredientsItem(amount, null, name, null)
        }

        binding.rvScanResult.layoutManager = LinearLayoutManager(this)
        val listIngredientsAdapter = ListIngredientsAdapter(list as ArrayList<IngredientsItem>)
        binding.rvScanResult.adapter = listIngredientsAdapter

        if (list.isNullOrEmpty()) {
            binding.rvScanResult.visibility = View.GONE
            binding.ivEmptyState.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.rvScanResult.visibility = View.VISIBLE
            binding.ivEmptyState.visibility = View.GONE
            binding.tvEmptyState.visibility = View.GONE
        }

        binding.buttonAddScan.setOnClickListener {

            val gson = Gson()
            val json = gson.toJson(list)

            val ingredient = RequestBody.create("text/plain".toMediaTypeOrNull(), json)

            Log.d("resultttt", json.toString())

            viewModel.addManyIngredients(ingredient).observe(this@ResultActivity) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.loading.visibility = View.GONE
                        val intent = Intent(this, SuccessActivity::class.java)
                        startActivity(intent)
                    }
                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        showToast(result.error)
                    }
                }
            }
        }
    }
}