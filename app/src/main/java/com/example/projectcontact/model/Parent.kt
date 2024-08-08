package com.example.projectcontact.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parent (
    var gmail:String = "",
    var parentFirstName: String = "",
    var parentMiddleName: String = "",
    var parentLastName: String = "",
    var sitio: String= "",
    var monthlyIncome: String = "",
    var houseNumber: String = "",
    var belongtoIP: String = "",
    var barangay: String = "",
    var contactNo: String = "",
    var children: List<Child> = emptyList()
) : Parcelable {
    val fullName: String
        get() = "$parentFirstName $parentLastName"
}