package com.example.projectcontact.util.chart

import android.graphics.Color
import android.graphics.Color.parseColor
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


object PieChartMaker {
    fun dualPieChart(
        pieChart: PieChart, dataList: List<Float>,
        labelList: List<String>, colorStringList: List<String>,
        centerText: String
    ){
        val centerTextColor = parseColor("#000000")
        val totalSize = dataList.sum()
        val entries = createPieEntries(
            dataList.zip(labelList) { data, label ->
                data to String.format("%.2f", data/totalSize * 100) + "% - " + label
            }
        )
        val dataSet = createPieDataSet(entries, colorStringList.map {parseColor(it) })
        val pieData = PieData(dataSet)
        pieChart.data = pieData

        setupPieChartAppearance(pieChart, centerText, centerTextColor,  14f, 60f)
    }

    fun simplePieChart(pieChart: PieChart, dataValue: Float, chartColorString: String, textColorString: String) {
        val chartColor = parseColor(chartColorString)
        val textColor = parseColor(textColorString)
        val spaceColor = parseColor("#f4f4f4")

        val entries = createPieEntries(listOf(
            dataValue to "",
            100f-dataValue to ""
        ))
        val dataSet = createPieDataSet(entries, listOf(chartColor, spaceColor))

        val pieData = PieData(dataSet)
        pieChart.data = pieData

        setupPieChartAppearance(
            pieChart, String.format("%.2f%%", dataValue), textColor)
    }

    private fun createPieEntries(values: List<Pair<Float, String>>): List<PieEntry> {
        return values.map { (value, label) -> PieEntry(value, label) }
    }

    private fun createPieDataSet(entries: List<PieEntry>, colors: List<Int>): PieDataSet {
        return PieDataSet(entries, "").apply {
            this.colors = colors
            valueTextColor = Color.TRANSPARENT
            valueTextSize = 0f
        }
    }

    private fun setupPieChartAppearance(
        pieChart: PieChart, centerText: String,
        textColor: Int, textSize: Float = 18f,
        holeRadius: Float = 80f)
    {
        pieChart.setDrawEntryLabels(true)
        pieChart.centerText = centerText
        pieChart.legend.isEnabled = false
        pieChart.setCenterTextColor(textColor)
        pieChart.setCenterTextSize(textSize)
        pieChart.description.isEnabled = false
        pieChart.holeRadius = holeRadius
        pieChart.invalidate()
    }

}