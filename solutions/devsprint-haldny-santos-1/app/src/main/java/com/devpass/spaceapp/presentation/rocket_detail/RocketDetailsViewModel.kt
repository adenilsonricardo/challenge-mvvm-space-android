package com.devpass.spaceapp.presentation.rocket_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpass.spaceapp.model.RocketDetail
import com.devpass.spaceapp.repository.RocketDetailRepository
import com.devpass.spaceapp.repository.RocketDetailRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RocketDetailsViewModel(
    private val rocketDetailRepository: RocketDetailRepository = RocketDetailRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableLiveData<RocketDetailsUiState>()
    val uiState: LiveData<RocketDetailsUiState> = _uiState

    init {
        RocketDetailsUiState.Loading
    }

    fun loadRocketDetails(id: String) {
        viewModelScope.launch {
            delay(3000)
            try {
                val data: RocketDetail = rocketDetailRepository.fetchRocketDetail(id)
                _uiState.value = RocketDetailsUiState.Success(data)
            } catch (exception: Exception) {
                _uiState.value = RocketDetailsUiState.Error(exception)
            }
        }
    }
}

sealed class RocketDetailsUiState {
    data class Success(val data: RocketDetail?) : RocketDetailsUiState()
    data class Error(val exception: Throwable) : RocketDetailsUiState()
    object Loading : RocketDetailsUiState()
}
