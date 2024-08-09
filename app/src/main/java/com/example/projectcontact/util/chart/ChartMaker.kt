package com.example.projectcontact.util.chart

import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


object ChartMaker {


    fun createPieChart(
        view: View, chartId: Int, colors1: IntArray, count_Male: Int,
        count_Female: Int, totalsize: Int, pieType: String?
    ): PieChart {

        val pieChart: PieChart = view.findViewById(chartId)
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(
            PieEntry(
                count_Male.toFloat(),
                String.format("%.2f", count_Male.toDouble() / totalsize * 100) + "% - N"
            )
        )
        pieEntries.add(
            PieEntry(
                count_Female.toFloat(),
                String.format("%.2f", count_Female.toDouble() / totalsize * 100) + "% - M"
            )
        )
        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(colors1[0], colors1[1])
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(false) // Enable drawing values (percentage)
        pieChart.setData(pieData)
        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(colors1[2])
        pieChart.transparentCircleRadius = 0f
        pieChart.holeRadius = 50f
        pieChart.setUsePercentValues(true)
        pieChart.setDrawCenterText(true)
        if (count_Male == 0 && count_Female == 0) {
            pieChart.centerText = "No Data is Available"
        } else {
            pieChart.centerText = pieType
        }
        pieChart.setCenterTextSize(14f)
        pieChart.animateY(1500)
        // Remove legend (color legend in the bottom part of the chart)
        pieChart.legend.isEnabled = false
        pieChart.invalidate()
        return pieChart
    }

    fun simplePieChart(pieChart : PieChart, textColor: Int, chartColor: Int){
        val entries = listOf(
            PieEntry(70f), // Green segment
            PieEntry(30f) // White segment
        )
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(chartColor, Color.WHITE)
        dataSet.valueTextColor = Color.TRANSPARENT
        dataSet.valueTextSize = 0f

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate()

        pieChart.setDrawEntryLabels(false)
        pieChart.setCenterText("50%")
        pieChart.legend.isEnabled = false
        pieChart.setCenterTextColor(textColor)
        pieChart.setCenterTextSize(18f)
        pieChart.description.isEnabled = false
        pieChart.holeRadius = 60f
    }

}