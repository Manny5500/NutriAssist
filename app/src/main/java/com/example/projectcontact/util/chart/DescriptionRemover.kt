package com.example.projectcontact.util.chart

import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.Description

fun <T : Chart<*>?> descriptionRemover(chart: T) {
    val description = Description()
    description.text = ""
    chart!!.description = description
}
