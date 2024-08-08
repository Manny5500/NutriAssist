package com.example.projectcontact.model

import android.os.Parcelable
import com.example.projectcontact.util.DateUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class History (
    var height: Double = 0.0,
    var weight: Double = 0.0,
    var id: String = "",
    var statusdb: List<String> = emptyList(),
    var status: List<String> = emptyList(),
    val statusProgress: List<String> = emptyList(),
    var sex: String = ""
):Parcelable{
    val formattedHeight: String get() = "Height: $height cm"
    val formattedWeight: String get() = "Weight: $weight kg"
    val formattedDate: String get() = DateUtil.toLongDayFormat(id)

    fun toFormattedStatus():String{
        return "Status: " + statusdb.joinToString( separator = ", " )
    }
}