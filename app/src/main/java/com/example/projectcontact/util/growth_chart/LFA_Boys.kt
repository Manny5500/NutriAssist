package com.example.projectcontact.util.growth_chart

import com.example.projectcontact.util.status.StatusFinderImpl

class LFA_Boys {
    fun LFA_Boys_M(height: Double, age: Int): String {
        var status = "Null"
        val negasd3 = doubleArrayOf(
            44.2, 48.9, 52.4, 55.3,
            57.6, 59.6, 61.2, 62.7,
            64.0, 65.2, 66.4, 67.6,
            68.6, 69.6, 70.6, 71.6,
            72.5, 73.3, 74.2, 75.0,
            75.8, 76.5, 77.2, 78.0
        )
        val negasd2 = doubleArrayOf(
            46.1, 50.8, 54.4, 57.3,
            59.7, 61.7, 63.3, 64.8,
            66.2, 67.5, 68.7, 69.9,
            71.0, 72.1, 73.1, 74.1,
            75.0, 76.0, 76.9, 77.7,
            78.6, 79.4, 80.2, 81.0
        )
        val negasd1 = doubleArrayOf(
            48.0, 52.8, 56.4, 59.4,
            61.8, 63.8, 65.5, 67.0,
            68.4, 69.7, 71.0, 72.2,
            73.4, 74.5, 75.6, 76.6,
            77.6, 78.6, 79.6, 80.5,
            81.4, 82.3, 83.1, 83.9
        )
        val median = doubleArrayOf(
            49.9, 54.7, 58.4, 61.4,
            63.9, 65.9, 67.6, 69.2,
            70.6, 72.0, 73.3, 74.5,
            75.7, 76.9, 78.0, 79.1,
            80.2, 81.2, 82.3, 83.2,
            84.2, 85.1, 86.0, 86.9
        )
        val posisd1 = doubleArrayOf(
            51.8, 56.7, 60.4, 63.5,
            66.8, 68.0, 69.8, 71.3,
            72.8, 74.2, 75.6, 76.9,
            78.1, 79.3, 80.5, 81.7,
            82.8, 83.9, 85.0, 86.0,
            87.0, 88.0, 89.0, 89.9
        )
        val posisd2 = doubleArrayOf(
            53.7, 58.6, 62.4, 65.5,
            68.0, 70.1, 71.9, 73.5,
            75.0, 76.5, 77.9, 79.2,
            80.5, 81.8, 83.0, 84.2,
            85.4, 86.5, 87.7, 88.8,
            89.8, 90.9, 91.9, 92.9
        )
        val posisd3 = doubleArrayOf(
            55.6, 60.6, 64.4, 67.6,
            70.1, 72.2, 74.0, 75.7,
            77.2, 78.7, 80.1, 81.5,
            82.9, 84.2, 85.5, 86.7,
            88.0, 89.2, 90.4, 91.5,
            92.6, 93.8, 94.9, 95.9
        )
        val stunted = StatusFinderImpl()
        status = stunted.HFAStatus(
            age, height, negasd3, negasd2, negasd1, median,
            posisd1, posisd2, posisd3
        )
        return status
    }
}