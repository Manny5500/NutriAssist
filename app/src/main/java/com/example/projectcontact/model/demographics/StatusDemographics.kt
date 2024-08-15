package com.example.projectcontact.model.demographics

data class StatusDemographics(
    var barChartData: Pair<List<Float>, List<String>> = Pair(emptyList(), emptyList()),
    var tableData : Array<Array<String>> = Array(7) { arrayOf()}
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StatusDemographics

        if (barChartData != other.barChartData) return false
        if (!tableData.contentDeepEquals(other.tableData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = barChartData.hashCode()
        result = 31 * result + tableData.contentDeepHashCode()
        return result
    }
}