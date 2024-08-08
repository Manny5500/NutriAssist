package com.example.projectcontact.util.status

interface StatusFinder {

    fun WFAStatus(
        age: Int, weight: Double,
        nsd3: DoubleArray, nsd2: DoubleArray,
        nsd1: DoubleArray, median: DoubleArray,
        sd1: DoubleArray, sd2: DoubleArray, sd3: DoubleArray
    ) : String

    fun HFAStatus(
        age: Int, height: Double,
        nsd3: DoubleArray, nsd2: DoubleArray,
        nsd1: DoubleArray, median: DoubleArray,
        sd1: DoubleArray, sd2: DoubleArray, sd3: DoubleArray
    ) : String

    fun WFHStatus(
        weight: Double, height: Double,
        nsd3: DoubleArray, nsd2: DoubleArray,
        nsd1: DoubleArray, median: DoubleArray,
        sd1: DoubleArray, sd2: DoubleArray, sd3: DoubleArray,
        position: Int
    ) : String



}