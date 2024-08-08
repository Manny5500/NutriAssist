package com.example.projectcontact.util

object PositionUtil {
    fun findElementPosition(array: DoubleArray, target: Double): Int {

        if(target < 45) return 0
        if(target > 110) return array.size - 1

        for (i in array.indices) {
            if (array[i] == target) {
                return i
            }
        }

        return -1
    }
    fun findElementPosition_H(array: DoubleArray, target: Double): Int {

        if(target < 65) return 0
        if(target > 120) return array.size -1

        for(i in array.indices){
            if(array[i] == target){
                return i
            }
        }

        return -1
    }

}