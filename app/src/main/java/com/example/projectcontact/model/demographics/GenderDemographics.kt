package com.example.projectcontact.model.demographics

data class GenderDemographics(
    var pieChartData : Pair<Float, Float> = Pair(0f, 0f),
    var tableData : Array<Array<String>> = Array(2) { arrayOf()}
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenderDemographics

        if (pieChartData != other.pieChartData) return false
        if (!tableData.contentDeepEquals(other.tableData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pieChartData.hashCode()
        result = 31 * result + tableData.contentDeepHashCode()
        return result
    }
}