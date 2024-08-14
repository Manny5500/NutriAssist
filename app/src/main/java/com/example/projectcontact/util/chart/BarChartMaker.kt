package com.example.projectcontact.util.chart

import android.util.Log
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

object BarChartMaker {

    fun simpleBarChart(barChart: BarChart, dataList: List<Float>, labelList: List<String>){
        val n = dataList.count()
        val countList = (0..<n).map{it.toFloat()}
        val entries = createBarEntries(
            dataList.zip(countList){ data, count ->
                data to count
            }
        )

        val barDataSet = BarDataSet(entries, "Bar Chart")
        barDataSet.colors = ColorTemplate.PASTEL_COLORS.toList()
        val barData = BarData(barDataSet)
        barChart.setData(barData)

        setupXAxis(barChart, labelList)
        setupYAxis(barChart)
        integerValues(barChart, barDataSet)

        barChart.legend.isEnabled = false
        barChart.description.text = ""
        barChart.setFitBars(true)
        barChart.invalidate()
    }

    private fun setupYAxis(barChart: BarChart) {
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.axisMinimum = 0f
    }

    private fun setupXAxis(barChart: BarChart , labelList: List<String>){
        barChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labelList)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }
    }

    private fun createBarEntries(values: List<Pair<Float, Float>>):List<BarEntry>{
        return values.map{ (value, i) -> BarEntry(i, value) }
    }

    private fun integerValues(barChart: BarChart, vararg barDataSets: BarDataSet) {
        for (barDataSet in barDataSets) {
            barDataSet.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    // Format the value as an integer
                    return value.toInt().toString()
                }
            }
        }
        barChart.axisLeft.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                // Format the value as an integer
                return value.toInt().toString()
            }
        }
        barChart.axisRight.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                // Format the value as an integer
                return value.toInt().toString()
            }
        }
    }
}