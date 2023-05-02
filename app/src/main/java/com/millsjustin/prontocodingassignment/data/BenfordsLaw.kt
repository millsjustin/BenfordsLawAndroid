package com.millsjustin.prontocodingassignment.data

import com.millsjustin.prontocodingassignment.ui.DEFAULT_ALLOWED_DEVIATION
import kotlin.math.absoluteValue
import kotlin.math.max

/**
 * This class holds expected constants for Benford's Law and has logic for calculating percentages
 * given a dataset and testing whether a dataset conforms to Benford's Law.
 */
object BenfordsLaw {

    private const val percentOne = 0.301
    private const val percentTwo = 0.176
    private const val percentThree = 0.125
    private const val percentFour = 0.097
    private const val percentFive = 0.079
    private const val percentSix = 0.067
    private const val percentSeven = 0.058
    private const val percentEight = 0.051
    private const val percentNine = 0.046

    val digits = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9')

    val expectedPercentages = DigitPercentages(
        percentOne = percentOne,
        percentTwo = percentTwo,
        percentThree = percentThree,
        percentFour = percentFour,
        percentFive = percentFive,
        percentSix = percentSix,
        percentSeven = percentSeven,
        percentEight = percentEight,
        percentNine = percentNine,
    )

    /**
     * Given some [data] count how many times digits 1-9 appear as the first digit of the integer
     * and calculate the percentage of each according to the size of the data.
     */
    fun calculatePercentages(data: List<Int>): DigitPercentages {
        return data.map { it.toString().first() }
            .groupingBy { it }
            .eachCount()
            .mapValues { (_, count) ->
                count.toDouble() / data.size
            }.let {
                DigitPercentages(it)
            }
    }

    /**
     * I'm assuming a dataset conforms to Benford's law if all of the [actual] percentages are within
     * [allowedDeviation] of the expected percentages. The default [allowedDeviation] is defined at
     * [DEFAULT_ALLOWED_DEVIATION] and the UI allows to adjust the default as needed.
     */
    fun conforms(actual: DigitPercentages, allowedDeviation: Double): Boolean {
        return digits.all {
            val difference = expectedPercentages[it] - actual[it]
            difference.absoluteValue <= allowedDeviation
        }
    }

    /**
     * Calculate the max percentage of [actual] and [expectedPercentages]
     */
    fun maxPercent(actual: DigitPercentages): Double {
        val maxActual = digits.maxOfOrNull { actual[it] } ?: 0.0
        return max(maxActual, percentOne)
    }

}