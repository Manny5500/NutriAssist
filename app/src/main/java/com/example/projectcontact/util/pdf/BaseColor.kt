package com.example.projectcontact.util.pdf

import com.itextpdf.text.BaseColor

object BaseColor {

    val yellowColor = getBaseColor("#fffcae")
    val greenColor = getBaseColor("#77DD77")
    val redColor = getBaseColor("#F8858B")
    val orangeColor = getBaseColor("#FFAC4A")
    val purpleColor = getBaseColor("#f0e4f4")
    val grayColor = getBaseColor("#d3d3d3")
    val darkerGray = getBaseColor("#c0c0c0")
    val darkestGray = getBaseColor("#808080")
    val blueColor = getBaseColor("#2600ff")
    val peachColor = getBaseColor("#d39885")
    val paleSilver = getBaseColor("#c4c6bb")
    val laurelGreen = getBaseColor("#a6aa91")
    val black: BaseColor = BaseColor.BLACK


    fun getBaseColor(hexColor: String): BaseColor {
        val red = hexColor.substring(1, 3).toInt(16)
        val green = hexColor.substring(3, 5).toInt(16)
        val blue = hexColor.substring(5, 7).toInt(16)
        return BaseColor(red, green, blue)
    }

}