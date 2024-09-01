package com.example.projectcontact.ui.admin.prevalance_report

data class PrevalanceReportValues(
    var statuses:String = "Underweight and Severe Underweight",
    var themeColorString:String = "#51ADE5",
    var methodType: String = "WEIGHT FOR AGE"
) {
    fun updateValues(status: String) {
        when (status) {
            "Stunting" -> {
                this.statuses = "Stunted and Severe Stunted"
                this.themeColorString = "#449E48"
                this.methodType = "HEIGHT FOR AGE"
            }
            "Overweight" -> {
                this.statuses = "Overweight and Obese"
                this.themeColorString = "#FF6961"
                this.methodType = "WEIGHT FOR LENGTH / HEIGHT"
            }
            "Wasting" -> {
                this.statuses = "Wasted and Severe Wasted"
                this.themeColorString = "#FFB347"
                this.methodType = "WEIGHT FOR LENGTH/HEIGHT"
            }
            else -> {
                this.statuses = "Underweight and Severe Underweight"
                this.themeColorString = "#51ADE5"
                this.methodType  = "WEIGHT FOR AGE"
            }
        }
    }

}