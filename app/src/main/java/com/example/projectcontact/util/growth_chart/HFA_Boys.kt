package com.example.projectcontact.util.growth_chart

import com.example.projectcontact.util.status.StatusFinderImpl

class HFA_Boys {
    fun HFA_Boys_M(height: Double, ages: Int): String {
        val age = ages - 24
        val negasd3 = doubleArrayOf(
            78.0, 78.6, 79.3, 79.9,
            80.5, 81.1, 81.7, 82.3,
            82.8, 83.4, 83.9, 84.4,
            85.0, 85.5, 86.0, 86.5,
            87.0, 87.5, 88.0, 88.4,
            88.9, 89.4, 89.8, 90.3,
            90.7, 91.2, 91.6, 92.1,
            92.5, 93.0, 93.4, 93.9,
            94.3, 94.7, 95.2, 95.6
        )
        val negasd2 = doubleArrayOf(
            81.0, 81.7, 82.5, 83.1,
            83.8, 84.5, 85.1, 85.7,
            86.4, 86.9, 87.5, 88.1,
            88.7, 89.2, 89.8, 90.3,
            90.9, 91.4, 91.9, 92.4,
            93.0, 93.5, 94.0, 94.4,
            94.9, 95.4, 95.9, 96.4,
            96.9, 97.4, 97.8, 98.3,
            98.8, 99.3, 99.7, 100.2
        )
        val negasd1 = doubleArrayOf(
            84.1, 84.9, 85.6, 86.4,
            87.1, 87.8, 88.5, 89.2,
            89.9, 90.5, 91.1, 91.8,
            92.4, 93.0, 93.6, 94.2,
            94.7, 95.3, 95.9, 96.4,
            97.0, 97.5, 98.1, 98.6,
            99.1, 99.7, 100.2, 100.7,
            101.2, 101.7, 102.3, 102.8,
            103.3, 103.8, 104.3, 104.8
        )
        val median = doubleArrayOf(
            87.1, 88.0, 88.8, 89.6,
            90.4, 91.2, 91.9, 92.7,
            93.4, 94.1, 94.8, 95.4,
            96.1, 96.7, 97.4, 98.0,
            98.6, 99.2, 99.9, 100.4,
            101.0, 101.6, 102.2, 102.8,
            103.3, 103.9, 104.4, 105.0,
            105.6, 106.1, 106.7, 107.2,
            107.8, 108.3, 108.9, 109.4
        )
        val posisd1 = doubleArrayOf(
            90.2, 91.1, 92.0, 92.9,
            93.7, 94.5, 95.3, 96.1,
            96.9, 97.6, 98.4, 99.1,
            99.8, 100.5, 101.2, 101.8,
            102.5, 103.2, 103.8, 104.5,
            105.1, 105.7, 106.3, 106.9,
            107.5, 108.1, 108.7, 109.3,
            109.9, 110.5, 111.1, 111.7,
            112.3, 112.8, 113.4, 114.0
        )
        val posisd2 = doubleArrayOf(
            93.2, 94.2, 95.2, 96.1,
            97.0, 97.9, 98.7, 99.6,
            100.4, 101.2, 102.0, 102.7,
            103.5, 104.2, 105.0, 105.7,
            106.4, 107.1, 107.8, 108.5,
            109.1, 109.8, 110.4, 111.1,
            111.7, 112.4, 113.0, 113.6,
            114.2, 114.9, 115.5, 116.1,
            116.7, 117.4, 118.0, 118.6
        )
        val posisd3 = doubleArrayOf(
            96.3, 97.3, 98.3, 99.3,
            100.3, 101.2, 102.1, 103.0,
            103.9, 104.8, 105.6, 106.4,
            107.2, 108.0, 108.8, 109.5,
            110.3, 111.0, 111.7, 112.5,
            113.2, 113.9, 114.6, 115.2,
            115.9, 116.6, 117.3, 117.9,
            118.6, 119.2, 119.9, 120.6,
            121.2, 121.9, 122.6, 123.2
        )
        val stunted = StatusFinderImpl()
        return stunted.HFAStatus(
            age, height, negasd3, negasd2, negasd1, median,
            posisd1, posisd2, posisd3
        )
    }
}