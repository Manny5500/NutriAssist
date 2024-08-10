package com.example.projectcontact.util.chart

import com.example.projectcontact.model.Child

object DashboardPieChart {
     fun dataList(childList: List<Child>): List<Float>{
         val (normalChildren, malChildren) = childList.partition { it.statusdb.contains("Normal") }
         val cN = normalChildren.size.toFloat()
         val cM = malChildren.size.toFloat()
         return listOf(cN, cM)
     }

    val colorList = listOf("#3498db", "#2980b9")
    const val centerText = "Normal vs Malnourished"
    val labelList = listOf( "N", "M")
}