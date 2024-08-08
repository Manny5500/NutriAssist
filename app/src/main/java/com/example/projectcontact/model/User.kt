package com.example.projectcontact.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    var firstName: String = "",
    var middleName: String  = "",
    var birthdate: String = "",
    var sex: String = "",
    var barangay: String = "",
    var lastName: String = "",
    var user: String = "",
    var contact: String = "",
    var id: String = "",
    var imageUrl: String = "",
    var isArchive: String = "",
    var verified: String = "",
    var deletionRequest: String = "",
    var email:String = ""


):Parcelable