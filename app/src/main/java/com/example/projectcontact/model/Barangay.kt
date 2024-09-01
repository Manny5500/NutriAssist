package com.example.projectcontact.model

data class Barangay(
    var population: Int = 0,
    var estimatedChildren: Int = 0,
    var barangay:String = "",
    var totalCase : Int = 0,
    var normalCase: Int = 0,
    var optCoverage: Float = 0.0f,
    var normalRate : Float = 0.0f,
    var totalRate: Float = 0.0f,
    var caseLabel: String = "U & SU",
    var position: Int = 1,
)
