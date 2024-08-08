package com.example.projectcontact.util.chart

import android.graphics.Color
import com.example.projectcontact.model.Child

object DashboardPieChart {
    val lightBlueColor: Int = Color.parseColor("#3498db")
    val darkBlueColor: Int = Color.parseColor("#2980b9")
    val whiteColor: Int = Color.parseColor("#FFFFFFFF")
    val colors1 = intArrayOf(lightBlueColor, darkBlueColor, whiteColor)
    var cNor = 0
    var cMal = 0
    fun count(childList: List<Child>){
        cNor = 0
        cMal = 0
        childList.forEach { child ->
            if (child.statusdb.contains("Normal")) cNor++ else cMal++
        }
    }
}