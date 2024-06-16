package com.capstone.mageiras.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.mageiras.data.repository.PredictRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ResultViewModel (
    private val predictRepository: PredictRepository
) : ViewModel() {

    fun predictImage(image: MultipartBody.Part) = predictRepository.predictImage(image)

}