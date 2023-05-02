package com.millsjustin.prontocodingassignment.data

import android.os.Parcel
import android.os.Parcelable

/**
 * A data model class to hold dataset percentages for digits 1-9
 */
data class DigitPercentages(
    val percentOne: Double,
    val percentTwo: Double,
    val percentThree: Double,
    val percentFour: Double,
    val percentFive: Double,
    val percentSix: Double,
    val percentSeven: Double,
    val percentEight: Double,
    val percentNine: Double,
) : Parcelable {

    operator fun get(key: Char): Double {
        return when (key) {
            '1' -> percentOne
            '2' -> percentTwo
            '3' -> percentThree
            '4' -> percentFour
            '5' -> percentFive
            '6' -> percentSix
            '7' -> percentSeven
            '8' -> percentEight
            '9' -> percentNine
            else -> error("Invalid key must be 1-9")
        }
    }

    constructor(percentages: Map<Char, Double>) : this(
        percentages.getOrDefault('1', 0.0),
        percentages.getOrDefault('2', 0.0),
        percentages.getOrDefault('3', 0.0),
        percentages.getOrDefault('4', 0.0),
        percentages.getOrDefault('5', 0.0),
        percentages.getOrDefault('6', 0.0),
        percentages.getOrDefault('7', 0.0),
        percentages.getOrDefault('8', 0.0),
        percentages.getOrDefault('9', 0.0),
    )

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(percentOne)
        parcel.writeDouble(percentTwo)
        parcel.writeDouble(percentThree)
        parcel.writeDouble(percentFour)
        parcel.writeDouble(percentFive)
        parcel.writeDouble(percentSix)
        parcel.writeDouble(percentSeven)
        parcel.writeDouble(percentEight)
        parcel.writeDouble(percentNine)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DigitPercentages> {
        override fun createFromParcel(parcel: Parcel): DigitPercentages {
            return DigitPercentages(parcel)
        }

        override fun newArray(size: Int): Array<DigitPercentages?> {
            return arrayOfNulls(size)
        }
    }
}
