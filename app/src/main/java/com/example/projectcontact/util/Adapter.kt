package com.example.projectcontact.util


object Adapter {
    fun type(type: Int):Array<String>{
        return when(type) {
            1 -> arrayOf("Purok 1", "Purok 2", "Purok 3", "Purok 4", "Purok 5")
            2 -> arrayOf("Male", "Female")
            3 -> arrayOf("Less than 9,100", "9,100 to 18,200", "18,200 to 36,400",
                "36,400 to 63,700", "63,700 to 109,200", "109,200 to 182,000")
            4 -> arrayOf("More than 1 child", "Low Income", "Have a Malnourished Child",
                "All")
            else -> emptyArray()
        }
    }
}