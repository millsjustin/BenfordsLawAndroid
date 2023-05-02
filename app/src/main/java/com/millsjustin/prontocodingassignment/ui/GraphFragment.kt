package com.millsjustin.prontocodingassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.millsjustin.prontocodingassignment.R
import com.millsjustin.prontocodingassignment.data.BenfordsLaw
import com.millsjustin.prontocodingassignment.databinding.FragmentGraphBinding
import kotlinx.coroutines.launch

class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    private fun FragmentGraphBinding.getDataView(key: Char): View {
        return when (key) {
            '1' -> dataOne
            '2' -> dataTwo
            '3' -> dataThree
            '4' -> dataFour
            '5' -> dataFive
            '6' -> dataSix
            '7' -> dataSeven
            '8' -> dataEight
            '9' -> dataNine
            else -> error("Invalid key must be 1-9")
        }
    }

    private fun FragmentGraphBinding.getBenfordsView(key: Char): View {
        return when (key) {
            '1' -> benfordsOne
            '2' -> benfordsTwo
            '3' -> benfordsThree
            '4' -> benfordsFour
            '5' -> benfordsFive
            '6' -> benfordsSix
            '7' -> benfordsSeven
            '8' -> benfordsEight
            '9' -> benfordsNine
            else -> error("Invalid key must be 1-9")
        }
    }

    private val viewModel: GraphViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.buttonDecrement.setOnClickListener {
            viewModel.onClickDecrement()
        }
        binding.buttonIncrement.setOnClickListener {
            viewModel.onClickIncrement()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    BenfordsLaw.digits.forEach { digit ->
                        binding.getDataView(digit)
                            .updateLayoutParams<ConstraintLayout.LayoutParams> {
                                matchConstraintPercentHeight =
                                    state.digitPercentages[digit]
                                        .toScreenPercentage(state.maxPercent)
                            }
                        binding.getBenfordsView(digit)
                            .updateLayoutParams<ConstraintLayout.LayoutParams> {
                                matchConstraintPercentHeight =
                                    BenfordsLaw.expectedPercentages[digit]
                                        .toScreenPercentage(state.maxPercent)
                            }
                    }
                    binding.allowedDeviation.text =
                        requireContext().getString(
                            R.string.allowed_deviation,
                            state.allowedDeviation
                        )
                    binding.result.setText(if (state.isPass) R.string.pass else R.string.fail)
                }
            }
        }
    }

    private fun Double.toScreenPercentage(max: Double): Float {
        // 1. multiply by 0.5 because the view is 50% of the screen
        // 2. divide by max to scale the chart to vertically fill the whole space
        return ((this * 0.5) / max).toFloat()
    }
}