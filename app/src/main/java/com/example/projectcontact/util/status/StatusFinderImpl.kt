package com.example.projectcontact.util.status


class StatusFinderImpl : StatusFinder {
    override fun WFAStatus(
        age: Int,
        weight: Double,
        nsd3: DoubleArray,
        nsd2: DoubleArray,
        nsd1: DoubleArray,
        median: DoubleArray,
        sd1: DoubleArray,
        sd2: DoubleArray,
        sd3: DoubleArray
    ) : String {
        for (i in 0..59) {
            if (weight < nsd2[age] && weight >= nsd3[age]) {
                return "Underweight"
            } else if (weight < nsd3[age]) {
                return "Severe Underweight"
            } else if (weight > sd2[age]) {
                return  "Overweight"
            }
        }
        return ""
    }

    override fun HFAStatus(
        age: Int,
        height: Double,
        nsd3: DoubleArray,
        nsd2: DoubleArray,
        nsd1: DoubleArray,
        median: DoubleArray,
        sd1: DoubleArray,
        sd2: DoubleArray,
        sd3: DoubleArray
    ): String {
        for (i in 0..59) {
            if (height < nsd2[age] && height >= nsd3[age]) {
                return "Stunted"
            } else if (height < nsd3[age]) {
                return "Severe Stunted"
            } else if (height > sd2[age]) {
                return "Tall"
            }
        }
        return ""
    }

    override fun WFHStatus(
        weight: Double,
        height: Double,
        nsd3: DoubleArray,
        nsd2: DoubleArray,
        nsd1: DoubleArray,
        median: DoubleArray,
        sd1: DoubleArray,
        sd2: DoubleArray,
        sd3: DoubleArray,
        position: Int
    ): String {
        for (i in 0..59) {
            if (weight < nsd2[position] && weight >= nsd3[position]) {
                return  "Wasted"
            } else if (weight < nsd3[position]) {
                return "Severe Wasted"
            } else if (weight > sd2[position] && weight <= sd3[position]) {
                return "Overweight"
            } else if (weight > sd3[position]) {
                return  "Obese"
            }
        }
        return ""
    }

}