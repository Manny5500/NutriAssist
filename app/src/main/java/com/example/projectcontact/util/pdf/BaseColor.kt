package com.example.projectcontact.util.pdf

import com.itextpdf.text.BaseColor

object BaseColor {

    val yellowColor = getBaseColor("#fffcae")
    val greenColor = getBaseColor("#77DD77")
    val redColor = getBaseColor("#F8858B")
    val orangeColor = getBaseColor("#FFAC4A")
    val purpleColor = getBaseColor("#f0e4f4")


    fun getBaseColor(hexColor: String): BaseColor {
        val red = hexColor.substring(1, 3).toInt(16)
        val green = hexColor.substring(3, 5).toInt(16)
        val blue = hexColor.substring(5, 7).toInt(16)
        return BaseColor(red, green, blue)
    }

}