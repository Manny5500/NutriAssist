package com.example.projectcontact.model

import android.os.Parcelable
import com.example.projectcontact.util.AgeUtil
import com.example.projectcontact.util.DateUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    var archive : String? = null,
    var firstName: String = "",
    var middleName: String  = "",
    var birthdate: String = "",
    var sex: String = "",
    var barangay: String = "",
    var lastName: String = "",
    var user: String = "",
    var contact: String = "",
    var id: String? = null ,
    var imageUrl: String? = null,
    var verified: String = "",
    var deletionRequest: String? = null,
    var email:String = ""

):Parcelable{
    fun getFullName():String{
        return "$firstName $lastName"
    }

    fun getUserAge(): Int{
        return AgeUtil.yearsBetweenToday(DateUtil.toDateFormate(birthdate))
    }

    fun getAgeString() : String{
        return "${getUserAge()} yrs. old"
    }
}