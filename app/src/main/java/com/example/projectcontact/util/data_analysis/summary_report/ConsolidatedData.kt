package com.example.projectcontact.util.data_analysis.summary_report

import android.annotation.SuppressLint
import com.example.projectcontact.model.Child
import com.example.projectcontact.util.AgeUtil
import com.example.projectcontact.util.DateUtil

class ConsolidatedData (
    private val childList: List<Child> = emptyList()
) {
    private val wfaStatus = arrayOf("Normal", "Overweight", "Underweight", "Severe Underweight")
    private val hfAStatus = arrayOf("Normal", "Tall", "Stunted", "Severe Stunted")
    private val wfhStatus = arrayOf("Normal", "Overweight", "Obese", "Wasted", "Severe Wasted")

    private fun filterAges(ageThreshold : Int ) : List<Child>{
        return childList.filter {
            val ageinMonth = AgeUtil.monthsBetweenToday(DateUtil.toDateFormate(it.birthDate))
            ageinMonth <= ageThreshold
        }
    }

    fun countNow(): Pair<Array<Int>, Array<Float>>{
        val childMasterList = arrayOf(filterAges(23),
            filterAges(59))
        val dataArray = Array(26){0}
        val prevArray = Array(26){0f}
        var i =0
        for(childList in childMasterList){
            for((a, s) in wfaStatus.withIndex()){
                dataArray[i+a] = childList.count{ it.status[0] == s}
                prevArray[i+a] = dataArray[i+a]/childList.size.toFloat()
            }
            for((b, s) in hfAStatus.withIndex()){
                dataArray[i+4+b] = childList.count{it.status[1] == s}
                prevArray[i+4+b] = dataArray[i+4+b]/childList.size.toFloat()
            }
            for((c, s) in wfhStatus.withIndex()){
                dataArray[i+8+c] = childList.count{it.status[2] == s}
                prevArray[i+8+c] = dataArray[i+8+c]/childList.size.toFloat()
            }
            i+=13
        }
        return Pair(dataArray, prevArray)
    }

    fun generateTableData(dataArr: Array<Int>, prevArr: Array<Float>): List<Array<Array<String>>>{
        val statusLabels = arrayOf("WFA - Normal", "OW", "UW", "SUW",
            "HFA - Normal", "Tall", "ST", "SST",
            "WFH - Normal", "OW", "OB", "MW", "SW"
            )
        return listOf(
        createTableContent(statusLabels, dataArr, prevArr, 0),
        createTableContent(statusLabels, dataArr, prevArr, 13)
        )
    }

    @SuppressLint("DefaultLocale")
    private fun createTableContent(
        statusLabels: Array<String>, dataArray: Array<Int>, prevArr: Array<Float>,
        startIndex: Int
    ): Array<Array<String>> {
        val tableData = Array(14) { Array(3) { "" } }
        tableData[0][1] = "Total"
        tableData[0][2] = "Prev"
        //this loop is for the table data
        for (i in tableData.indices) {
            if(i==0) continue
            val index = i + startIndex - 1
            tableData[i][0] = statusLabels[i-1]
            tableData[i][1] = dataArray[index].toString()
            tableData[i][2] = String.format("%.2f", (prevArr[index]*100.0f))
            if(tableData[i][2] == "NaN") tableData[i][2] = "0.00"
        }
        return tableData
    }
}