package com.millsjustin.prontocodingassignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {

    data class UiState(
        val data: String,
    )

    private val _data = mutableListOf<Int>()
    private val _count = MutableStateFlow(0)

    val uiState: StateFlow<UiState> = _count.map {
        UiState(data = _data.joinToString())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState(""),
    )

    fun onClickAddNumber(value: String?) {
        val number = value?.trim()?.toIntOrNull() ?: return // TODO handle invalid input
        _data.add(number)
        _count.value += 1
    }

    fun onClickDone() {
        // TODO
    }

}