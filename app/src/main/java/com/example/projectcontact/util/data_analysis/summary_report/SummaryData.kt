package com.example.projectcontact.util.data_analysis.summary_report

import com.example.projectcontact.model.Child


class SummaryData (
    private var childList : List<Child> = emptyList()
){

    private val ages023 = childList.filter{it.getAgeinMonth() in 0..23}
    private val ages059 = childList.filter { it.getAgeinMonth() in 0..59 }

    var eOPTLabels: Array<String> = arrayOf(
        "#Children 0-59 mos. affected by Undernutrition",
        "#Children 0-59 mos. with Overweight/Obesity:",
        "Total Number of Children 0-23 mos. old: ",
        "#Children 0-23 mos. affected by Undernutrition: "
    )

    var motherDataLabels: Array<String> = arrayOf(
        "Total Number of M/Cs of children 0-59 mos. old: ",
        "#M/Cs of 0-59 mos children affected by Undernutrition",
        "#M/Cs of 0-59 mos. children with Overweight/Obesity: ",
        "Total Number of M/Cs of children 0-23 mos. old ",
        "#M/Cs of 0-23 mos. children affected by Undernutrition: "
    )

    var summaryDataLabels: Array<String> = arrayOf(
        "#Children with weight but no height: ",
        "#Children with height but no weight: ",
        "#Children with missing information:",
        "#Children with names repeated: ",
        "#Children older than 59 months"
    )

    fun motherData(): Array<Int>{
        val count1 = ages059
            .distinctBy { it.gmail }
            .size
        val count2 = ages059
            .distinctBy{it.gmail}.
            count{it.status[0] == "Underweight"}
        val count3 = ages023
            .distinctBy { it.gmail }
            .count{it.status[2] == "Overweight" || it.status[2] == "Obese"}
        val count4 = ages023
            .distinctBy { it.gmail }
            .size
        val count5 = ages023
            .distinctBy { it.gmail }
            .count{it.status[0] == "Underweight"}
        return arrayOf(count1, count2, count3, count4, count5)
    }

    fun eOPTData() : Array<Int>{
        val count1 = ages059.count{it.status[0] == "Underweight"}
        val count2 = ages059.count{it.status[2] == "Overweight" || it.status[2] == "Obese"}
        val count3 = ages023.size
        val count4 = ages023.count{it.status[0] == "Underweight"}
        return arrayOf(count1, count2, count3, count4)
    }

    fun summaryData() : Array<Int>{
        val count5 = childList.filter{it.getAgeinMonth() > 59}.size
        return arrayOf(0, 0, 0, 0, count5)
    }

    fun getTableData() : List<Array<Array<String>>>
    {
        val eOPTTable = createTableContent(eOPTData(), eOPTLabels)
        val motherTable = createTableContent(motherData(), motherDataLabels)
        val summaryTable = createTableContent(summaryData(), summaryDataLabels)
        return listOf(eOPTTable, motherTable, summaryTable)
    }

    private fun createTableContent(
        data : Array<Int>, labels: Array<String>
    ): Array<Array<String>>{
        val tableContent = Array(data.size){Array(2){""}}
        for(i in tableContent.indices){
            tableContent[i][0] = labels[i]
            tableContent[i][1] = data[i].toString()
        }
        return tableContent
    }

}