package com.example.projectcontact.model.demographics

data class HistoricalDataDemographics(
    var lineChartData : Pair<List<Float>, List<String>> = Pair(emptyList(), emptyList()),
    var currentTotalCase : Int = 0,
    var previousTotalCase : Int = 0,
    val observation : String = "",
    val observationColorString : String = "#000000"
)
