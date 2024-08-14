package com.example.projectcontact.util.chart

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


object LineChartMaker {

    fun simpleLineChart(lineChart: LineChart, dataList: List<Float>,  labelList: List<String>) {
        val n = dataList.size
        val countList = (1..n).map { it.toFloat()}
        val entries = createLineEntries(
            dataList.zip(countList) { data, count ->
                data to count
            }
        )
        val lineDataSet = LineDataSet(entries, "")
        setupXAxis(lineChart, labelList)
        integerValues(lineChart, lineDataSet)
        descriptionRemover(lineChart)
        costumizeDataSet(lineDataSet, Color.parseColor("#3498db"))

        val lineData = LineData(lineDataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    private fun costumizeDataSet(dataSet: LineDataSet, color: Int) {
        dataSet.color = color
        dataSet.lineWidth = 3f
        dataSet.valueTextColor = color
        dataSet.setCircleColor(color)
        dataSet.circleRadius = 1f
    }
    private fun integerValues(lineChart: LineChart, vararg lineDataSets: LineDataSet) {
        for (dataSet in lineDataSets) {
            dataSet.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }
        lineChart.axisLeft.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return value.toInt().toString()
            }
        }
        lineChart.axisRight.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return value.toInt().toString()
            }
        }
    }
    private fun setupXAxis(lineChart: LineChart, labelList: List<String>){
        lineChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labelList)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }
    }
    private fun createLineEntries(values: List<Pair<Float, Float>>): List<Entry> {
        return values.map { (value, i) -> Entry(i, value) }
    }



}
