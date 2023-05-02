package com.millsjustin.prontocodingassignment.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.millsjustin.prontocodingassignment.data.BenfordsLaw
import com.millsjustin.prontocodingassignment.data.DigitPercentages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * I determined this default deviation by averaging the probable errors found on this table of
 *  datasets used by Benford in his 1938 study:
 *
 *  https://i0.wp.com/statisticsbyjim.com/wp-content/uploads/2022/10/Benfords_law_table.png?w=598&ssl=1
 */
const val DEFAULT_ALLOWED_DEVIATION = 3

class GraphViewModel(
    private val state: SavedStateHandle
) : ViewModel() {

    data class UiState(
        val digitPercentages: DigitPercentages,
        val maxPercent: Double,
        val allowedDeviation: Int,
        val isPass: Boolean,
    )

    private val _allowedDeviation = MutableStateFlow(DEFAULT_ALLOWED_DEVIATION)

    private val _args = GraphFragmentArgs.fromSavedStateHandle(state)
    private val _digitPercentages: DigitPercentages
        get() {
            return _args.digitPercentages
        }

    private val _initialUiState: UiState = UiState(
        _digitPercentages,
        maxPercent = BenfordsLaw.maxPercent(_digitPercentages),
        allowedDeviation = _allowedDeviation.value,
        isPass = BenfordsLaw.conforms(
            actual = _digitPercentages,
            allowedDeviation = allowedDeviationAsDouble(_allowedDeviation.value),
        ),
    )

    val uiState: StateFlow<UiState> = _allowedDeviation.map {
        _initialUiState.copy(
            allowedDeviation = it,
            isPass = BenfordsLaw.conforms(
                actual = _digitPercentages,
                allowedDeviation = allowedDeviationAsDouble(it),
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _initialUiState,
    )

    fun onClickDecrement() {
        _allowedDeviation.value = (_allowedDeviation.value - 1).coerceAtLeast(0)
    }

    fun onClickIncrement() {
        _allowedDeviation.value = (_allowedDeviation.value + 1).coerceAtMost(100)
    }

    private fun allowedDeviationAsDouble(value: Int): Double {
        return value.toDouble() / 100
    }

}