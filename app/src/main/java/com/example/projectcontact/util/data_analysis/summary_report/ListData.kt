package com.example.projectcontact.util.data_analysis.summary_report

import com.example.projectcontact.model.Child
import com.example.projectcontact.util.AgeUtil.monthsBetweenToday
import com.example.projectcontact.util.DateUtil.toDateFormate

class ListData (
    var childList: List<Child> = emptyList()
) {
    private val wfaStatus = arrayOf("Normal", "Overweight", "Underweight", "Severe Underweight")
    private val hfAStatus = arrayOf("Normal", "Tall", "Stunted", "Severe Stunted")
    private val wfhStatus = arrayOf("Normal", "Overweight", "Obese", "Wasted", "Severe Wasted")

    private fun monthFilter() : Array<List<Child>>{
        return arrayOf(filterAges(0,5), filterAges(6,11), filterAges(12,23),
            filterAges(24, 35), filterAges(36, 47), filterAges(48,59))
    }

    private fun filterAges(startMonth: Int, endMonth: Int): List<Child>{
        return childList.filter { child ->
            val monthDiff = monthsBetweenToday(toDateFormate(child.birthDate))
            monthDiff in startMonth .. endMonth
        }
    }

    fun countNow(): Array<IntArray>{
        val childMasterList = monthFilter()
        val dataArr = Array(78) { IntArray(3)}
        var count = 0
        var wfhCount = 0
        for(childList in childMasterList){
            for((a, s) in wfaStatus.withIndex()){
                dataArr[count+a][0] = childList.count { it.status[0] == s && it.sex == "Male" }
                dataArr[count+a][1] = childList.count { it.status[0] == s && it.sex == "Female"}
                dataArr[count+a][2] = dataArr[count+a][0] + dataArr[count+a][1]
            }
            for((b, s) in hfAStatus.withIndex()){
                dataArr[count+b+24][0] = childList.count{ it.status[1] == s && it.sex == "Male"}
                dataArr[count+b+24][1] = childList.count{ it.status[1] == s && it.sex == "Female"}
                dataArr[count+b+24][2] = dataArr[count+b+24][0] + dataArr[count+b+24][1]
            }
            for((c, s) in wfhStatus.withIndex()){
                dataArr[wfhCount+c+48][0] = childList.count{it.status[2] == s && it.sex == "Male"}
                dataArr[wfhCount+c+48][1] = childList.count{it.status[2] == s && it.sex == "Female"}
                dataArr[wfhCount+c+48][2] = dataArr[wfhCount+c+48][0] + dataArr[wfhCount+c+48][1]
            }
            count+=4
            wfhCount+=5
        }
        return dataArr
    }

    fun generateTableData(dataArr: Array<IntArray>): List<Array<Array<String>>> {
        val statusGroups = arrayOf(
            arrayOf("Normal", "OW", "UW", "SUW"),
            arrayOf("Normal", "Tall", "ST", "SST"),
            arrayOf("Normal", "OW", "OB", "MW", "SW")
        )
        val startIndices = arrayOf(0, 24, 48)
        return statusGroups.zip(startIndices) { statuses, startIndex ->
            createTableContent(statuses, dataArr, startIndex)
        }
    }

    private fun createTableContent(
        statusLabels: Array<String>, dataArray: Array<IntArray>,
        startIndex: Int
    ): Array<Array<String>> {
        val totalRows = (statusLabels.size+1) * 6
        val tableData = Array(totalRows) { Array(4) { "" } }
        val ageRanges = arrayOf("0-5 mo", "6-11 mo",
            "12-23 mo", "24-35 mo", "36-47 mo", "48-59 mo")
        val stepSize = totalRows / 6
        var statusLabelIndex = 0
        var offset = 0

        //this loop is for the table header
        for ((headerIndex, i) in (tableData.indices step stepSize).withIndex()) {
            tableData[i][0] = ageRanges[headerIndex]
            tableData[i][1] = "Boys"
            tableData[i][2] = "Girls"
            tableData[i][3] = "Total"
        }
        //this loop is for the table data
        for (i in tableData.indices) {
            if (i % stepSize == 0) {
                offset++
                continue
            }
            if (statusLabelIndex > stepSize - 2) statusLabelIndex = 0
            val dataIndex = i - offset + startIndex
            tableData[i][0] = statusLabels[statusLabelIndex]
            tableData[i][1] = dataArray[dataIndex][0].toString()
            tableData[i][2] = dataArray[dataIndex][1].toString()
            tableData[i][3] = dataArray[dataIndex][2].toString()
            statusLabelIndex++
        }
        return tableData
    }

    fun tfAges(): IntArray {
        val childList = monthFilter()
        val dataArr = IntArray(18)
        for (i in 0..5) {
            for ((_, _, _, _, _, _, _, _, _, _, _, _, sex) in childList[i]) {
                val isMale = sex == "Male"
                val isFemale = sex == "Female"
                if (isMale) {
                    dataArr[i * 3]++
                }
                if (isFemale) {
                    dataArr[i * 3 + 1]++
                }
            }

            dataArr[i * 3 + 2] = dataArr[i * 3] + dataArr[i * 3 + 1]
        }
        return dataArr
    }


}