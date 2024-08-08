package com.example.projectcontact.util.growth_chart

import com.example.projectcontact.util.NumUtil
import com.example.projectcontact.util.PositionUtil
import com.example.projectcontact.util.status.StatusFinderImpl

class WFL_Boys {
    fun WFL_Boys_M(weight: Double, heights: Double): String {
        var status = "Null"
        val numUtils = NumUtil
        val height = numUtils.roundToNearestEnding(heights)
        val heights_array = doubleArrayOf(
            45.0, 45.5, 46.0, 46.5, 47.0, 47.5, 48.0, 48.5, 49.0, 49.5,
            50.0, 50.5, 51.0, 51.5, 52.0, 52.5, 53.0, 53.5, 54.0, 54.5,
            55.0, 55.5, 56.0, 56.5, 57.0, 57.5, 58.0, 58.5, 59.0, 59.5,
            60.0, 60.5, 61.0, 61.5, 62.0, 62.5, 63.0, 63.5, 64.0, 64.5,
            65.0, 65.5, 66.0, 66.5, 67.0, 67.5, 68.0, 68.5, 69.0, 69.5,
            70.0, 70.5, 71.0, 71.5, 72.0, 72.5, 73.0, 73.5, 74.0, 74.5,
            75.0, 75.5, 76.0, 76.5, 77.0, 77.5, 78.0, 78.5, 79.0, 79.5,
            80.0, 80.5, 81.0, 81.5, 82.0, 82.5, 83.0, 83.5, 84.0, 84.5,
            85.0, 85.5, 86.0, 86.5, 87.0, 87.5, 88.0, 88.5, 89.0, 89.5,
            90.0, 90.5, 91.0, 91.5, 92.0, 92.5, 93.0, 93.5, 94.0, 94.5,
            95.0, 95.5, 96.0, 96.5, 97.0, 97.5, 98.0, 98.5, 99.0, 99.5,
            100.0, 100.5, 101.0, 101.5, 102.0, 102.5, 103.0, 103.5, 104.0, 104.5,
            105.0, 105.5, 106.0, 106.5, 107.0, 107.5, 108.0, 108.5, 109.0, 109.5, 110.0
        )
        val positionUtil = PositionUtil
        val position: Int = positionUtil.findElementPosition(heights_array, height)
        val negasd3 = doubleArrayOf(
            1.9, 1.9, 2.0, 2.1, 2.1, 2.2, 2.3, 2.3, 2.4, 2.5,
            2.6, 2.7, 2.7, 2.8, 2.9, 3.0, 3.1, 3.2, 3.3, 3.4,
            3.6, 3.7, 3.8, 3.9, 4.0, 4.1, 4.3, 4.4, 4.5, 4.6,
            4.7, 4.8, 4.9, 5.0, 5.1, 5.2, 5.3, 5.4, 5.5, 5.6,
            5.7, 5.8, 5.9, 6.0, 6.1, 6.2, 6.3, 6.4, 6.5, 6.6,
            6.6, 6.7, 6.8, 6.9, 7.0, 7.1, 7.2, 7.2, 7.3, 7.4,
            7.5, 7.6, 7.6, 7.7, 7.8, 7.9, 7.9, 8.0, 8.1, 8.2,
            8.2, 8.3, 8.4, 8.5, 8.5, 8.6, 8.7, 8.8, 8.9, 9.0,
            9.1, 9.2, 9.3, 9.4, 9.5, 9.6, 9.7, 9.8, 9.9, 10.0,
            10.1, 10.2, 10.3, 10.4, 10.5, 10.6, 10.7, 10.7, 10.8, 10.9,
            11.0, 11.1, 11.2, 11.3, 11.4, 11.5, 11.6, 11.7, 11.8, 11.9,
            12.0, 12.1, 12.2, 12.3, 12.4, 12.5, 12.6, 12.7, 12.8, 12.9,
            13.0, 13.2, 13.3, 13.4, 13.5, 13.6, 13.7, 13.8, 14.0, 14.1, 14.2
        )
        val negasd2 = doubleArrayOf(
            2.0, 2.1, 2.2, 2.3, 2.3, 2.4, 2.5, 2.6, 2.6, 2.7,
            2.8, 2.9, 3.0, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7,
            3.8, 4.0, 4.1, 4.2, 4.3, 4.5, 4.6, 4.7, 4.8, 5.0,
            5.1, 5.2, 5.3, 5.4, 5.6, 5.7, 5.8, 5.9, 6.0, 6.1,
            6.2, 6.3, 6.4, 6.5, 6.6, 6.7, 6.8, 6.9, 7.0, 7.1,
            7.2, 7.3, 7.4, 7.5, 7.6, 7.6, 7.7, 7.8, 7.9, 8.0,
            8.1, 8.2, 8.3, 8.3, 8.4, 8.5, 8.6, 8.7, 8.7, 8.8,
            8.9, 9.0, 9.1, 9.1, 9.2, 9.3, 9.4, 9.5, 9.6, 9.7,
            9.8, 9.9, 10.0, 10.1, 10.2, 10.4, 10.5, 10.6, 10.7, 10.8,
            10.9, 11.0, 11.1, 11.2, 11.3, 11.4, 11.5, 11.6, 11.7, 11.8,
            11.9, 12.0, 12.1, 12.2, 12.3, 12.4, 12.5, 12.6, 12.7, 12.8,
            12.9, 13.0, 13.2, 13.3, 13.4, 13.5, 13.6, 13.7, 13.9, 14.0,
            14.1, 14.2, 14.4, 14.5, 14.6, 14.7, 14.9, 15.0, 15.1, 15.3, 15.4
        )
        val negasd1 = doubleArrayOf(
            2.2, 2.3, 2.4, 2.5, 2.5, 2.6, 2.7, 2.8, 2.9, 3.0,
            3.0, 3.1, 3.2, 3.3, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0,
            4.2, 4.3, 4.4, 4.6, 4.7, 4.9, 5.0, 5.1, 5.3, 5.4,
            5.5, 5.6, 5.8, 5.9, 6.0, 6.1, 6.2, 6.4, 6.5, 6.6,
            6.7, 6.8, 6.9, 7.0, 7.1, 7.2, 7.3, 7.5, 7.6, 7.7,
            7.8, 7.9, 8.0, 8.1, 8.2, 8.3, 8.4, 8.5, 8.6, 8.7,
            8.8, 8.8, 8.9, 9.0, 9.1, 9.2, 9.3, 9.4, 9.5, 9.5,
            9.6, 9.7, 9.8, 9.9, 10.0, 10.1, 10.2, 10.3, 10.4, 10.5,
            10.6, 10.7, 10.8, 11.0, 11.1, 11.2, 11.3, 11.4, 11.5, 11.6,
            11.8, 11.9, 12.0, 12.1, 12.2, 12.3, 12.4, 12.5, 12.6, 12.7,
            12.8, 12.9, 13.1, 13.2, 13.3, 13.4, 13.5, 13.6, 13.7, 13.9,
            14.0, 14.1, 14.2, 14.4, 14.5, 14.6, 14.8, 14.9, 15.0, 15.2,
            15.3, 15.4, 15.6, 15.7, 15.9, 16.0, 16.2, 16.3, 16.5, 16.6, 16.8
        )
        val median = doubleArrayOf(
            2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 2.9, 3.0, 3.1, 3.2,
            3.3, 3.4, 3.5, 3.6, 3.8, 3.9, 4.0, 4.1, 4.3, 4.4,
            4.5, 4.7, 4.8, 5.0, 5.1, 5.3, 5.4, 5.6, 5.7, 5.9,
            6.0, 6.1, 6.3, 6.4, 6.5, 6.7, 6.8, 6.9, 7.0, 7.1,
            7.3, 7.4, 7.5, 7.6, 7.7, 7.9, 8.0, 8.1, 8.2, 8.3,
            8.4, 8.5, 8.6, 8.8, 8.9, 9.0, 9.1, 9.2, 9.3, 9.4,
            9.5, 9.6, 9.7, 9.8, 9.9, 10.0, 10.1, 10.2, 10.3, 10.4,
            10.4, 10.5, 10.6, 10.7, 10.8, 10.9, 11.0, 11.2, 11.3, 11.4,
            11.5, 11.6, 11.7, 11.9, 12.2, 12.1, 12.2, 12.4, 12.5, 12.6,
            12.7, 12.8, 13.0, 13.1, 13.2, 13.3, 13.4, 13.5, 13.7, 13.8,
            13.9, 14.0, 14.1, 14.3, 14.4, 14.5, 14.6, 14.8, 14.9, 15.5,
            15.2, 15.3, 15.4, 15.6, 15.7, 15.9, 16.0, 16.2, 16.3, 16.5,
            16.6, 16.8, 16.9, 17.1, 17.3, 17.4, 17.6, 17.8, 17.9, 18.1, 18.3
        )
        val posisd1 = doubleArrayOf(
            2.7, 2.8, 2.9, 3.0, 3.3, 3.1, 3.2, 3.3, 3.4, 3.5,
            3.6, 3.8, 3.9, 4.0, 4.1, 4.2, 4.4, 4.5, 4.7, 4.8,
            5.0, 5.1, 5.3, 5.4, 5.6, 5.7, 5.9, 6.1, 6.2, 6.4,
            6.5, 6.7, 6.8, 7.0, 7.1, 7.2, 7.4, 7.5, 7.6, 7.8,
            7.9, 8.0, 8.2, 8.3, 8.4, 8.5, 8.7, 8.8, 8.9, 9.0,
            9.2, 9.3, 9.4, 9.5, 9.6, 9.8, 9.9, 10.0, 10.1, 10.2,
            10.3, 10.4, 10.6, 10.7, 10.8, 10.9, 11.0, 11.1, 11.2, 11.3,
            11.4, 11.5, 11.6, 11.7, 11.8, 11.9, 12.0, 12.1, 12.2, 12.4,
            12.5, 12.6, 12.8, 12.9, 13.0, 13.2, 13.3, 13.4, 13.5, 13.7,
            13.8, 13.9, 14.1, 14.2, 14.3, 14.4, 14.6, 14.7, 14.8, 14.9,
            15.1, 15.2, 15.3, 15.5, 15.6, 15.7, 15.9, 16.0, 16.2, 16.3,
            16.5, 16.6, 16.8, 16.9, 17.1, 17.3, 17.4, 17.6, 17.8, 17.9,
            18.1, 18.3, 18.5, 18.6, 18.8, 19.0, 19.2, 19.4, 19.6, 19.8, 20.0
        )
        val posisd2 = doubleArrayOf(
            3.0, 3.1, 3.1, 3.2, 3.3, 3.4, 3.6, 3.7, 3.8, 3.9,
            4.0, 4.1, 4.2, 4.4, 4.5, 4.6, 4.8, 4.9, 5.1, 5.3,
            5.4, 5.6, 5.8, 5.9, 6.1, 6.3, 6.4, 6.6, 6.8, 7.0,
            7.1, 7.3, 7.4, 7.6, 7.7, 7.9, 8.0, 8.2, 8.3, 8.5,
            8.6, 8.7, 8.9, 9.0, 9.2, 9.3, 9.4, 9.6, 9.7, 9.8,
            10.0, 10.1, 10.2, 10.4, 10.5, 10.6, 10.8, 10.9, 11.0, 11.2,
            11.3, 11.4, 11.5, 11.6, 11.7, 11.9, 12.0, 12.1, 12.2, 12.3,
            12.4, 12.5, 12.6, 12.7, 12.8, 13.0, 13.1, 13.2, 13.3, 13.5,
            13.6, 13.7, 13.9, 14.0, 14.2, 14.3, 14.5, 14.6, 14.7, 14.9,
            15.0, 15.1, 15.3, 15.4, 15.6, 15.7, 15.8, 16.0, 16.1, 16.3,
            16.4, 16.5, 16.7, 16.8, 17.0, 17.1, 17.3, 17.5, 17.6, 17.8,
            18.0, 18.1, 18.3, 18.5, 18.7, 18.8, 19.0, 19.2, 19.4, 19.6,
            19.8, 20.0, 20.2, 20.4, 20.6, 20.8, 21.0, 21.2, 21.4, 21.7, 21.9
        )
        val posisd3 = doubleArrayOf(
            3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0, 4.2, 4.3,
            4.4, 4.5, 4.7, 4.8, 5.0, 5.1, 5.3, 5.4, 5.6, 5.8,
            6.0, 6.1, 6.3, 6.5, 6.7, 6.9, 7.1, 7.2, 7.4, 7.6,
            7.8, 8.0, 8.1, 8.3, 8.5, 8.6, 8.8, 8.9, 9.1, 9.3,
            9.4, 9.6, 9.7, 9.9, 10.0, 10.2, 10.3, 10.5, 10.6, 10.8,
            10.9, 11.1, 11.2, 11.3, 11.5, 11.6, 11.8, 11.9, 12.1, 12.2,
            12.3, 12.5, 12.6, 12.7, 12.8, 13.0, 13.1, 13.2, 13.3, 13.4,
            13.6, 13.7, 13.8, 13.9, 14.0, 14.2, 14.3, 14.4, 14.6, 14.7,
            14.9, 15.0, 15.2, 15.3, 15.5, 15.6, 15.8, 15.9, 16.1, 16.2,
            16.4, 16.5, 16.7, 16.8, 17.0, 17.1, 17.3, 17.4, 17.6, 17.7,
            17.9, 18.0, 18.2, 18.4, 18.5, 18.7, 18.9, 19.1, 19.2, 19.4,
            19.6, 19.8, 20.0, 20.2, 20.4, 20.6, 20.8, 21.0, 21.2, 21.5,
            21.7, 21.9, 22.1, 22.4, 22.6, 22.8, 23.1, 23.3, 23.6, 23.8, 24.1
        )
        val wasted = StatusFinderImpl()
        status = wasted.WFHStatus(
            weight, height, negasd3, negasd2, negasd1, median,
            posisd1, posisd2, posisd3, position
        )
        return status
    }
}