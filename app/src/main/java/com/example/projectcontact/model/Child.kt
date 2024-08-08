package com.example.projectcontact.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Child(
    var childFirstName: String = "",
    var childMiddleName: String = "",
    var childLastName: String = "",
    var status: List<String> = emptyList(),
    var statusdb: List<String> = emptyList(),
    var expectedDate: String = "",
    var id: String = "",
    var parentFirstName: String = "",
    var parentLastName: String = "",
    var parentMiddleName: String = "",
    var gmail: String = "",
    var houseNumber: String = "",
    var sex: String = "",
    var birthDate: String = "",
    var forfeeding: String = "",
    var forgulayan: String = "",
    var phoneNumber: String = "",
    var monthAdded: String = "",
    var weight:Double = 0.0,
    var height:Double = 0.0,
    var sitio: String = "",
    var dateAdded: Date = Date(),
    var belongtoIP: String = "",
    var barangay: String = ""

):Parcelable{
    val fullName: String
        get() = "$childFirstName $childLastName"

    val fullestName: String
        get() = "$childFirstName $childMiddleName $childLastName"

    val formattedStatus: String
        get() = statusdb.joinToString(separator = ", ")

    val parentFullName: String
        get() = "$parentFirstName $parentMiddleName $parentLastName"

}

