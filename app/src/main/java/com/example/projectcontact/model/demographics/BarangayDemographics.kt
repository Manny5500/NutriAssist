package com.example.projectcontact.model.demographics

data class BarangayDemographics(
    var tableData : Array<Array<String>> = Array(6) { arrayOf()}
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BarangayDemographics

        return tableData.contentDeepEquals(other.tableData)
    }

    override fun hashCode(): Int {
        return tableData.contentDeepHashCode()
    }
}
