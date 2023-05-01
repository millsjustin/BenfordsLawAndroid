package com.millsjustin.prontocodingassignment.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    data class UiState(
        val foo: String = "Justin"
    )

    val uiState: StateFlow<UiState> = MutableStateFlow(UiState())

}