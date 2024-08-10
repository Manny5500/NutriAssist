package com.example.projectcontact.util.filter

import com.example.projectcontact.model.Child

object StatusPercentage {

    private fun f(statusdb: List<String>):Boolean{
        return statusdb.all{
            it in listOf("Normal", "Tall")
        }
    }
    private fun g(statusdb: List<String>): Boolean{
        return statusdb.any{
            it in listOf("Underweight", "Wasted", "Overweight", "Stunted",
                "Severe Underweight", "Severe Wasted",
                "Obese", "Severe Stunted")
        }
    }

    private fun h(statusdb: List<String>): Boolean{
        return statusdb.any{
            it in listOf("Severe Underweight", "Severe Wasted",
                "Obese", "Severe Stunted")
        }
    }

    fun getNormalPercentage(childList: List<Child>) : Float{
        val childSize = childList.size
        return childList.filter { f(it.statusdb)}.size.toFloat() / childSize * 100
    }

    fun getMalnourishedPercentage(childList: List<Child>) : Float{
        val childSize = childList.size
        return childList.filter { g(it.statusdb)}.size.toFloat() / childSize * 100
    }

    fun getSevereCasesPercentage(childList: List<Child>) : Float{
        val childSize = childList.size
        return childList.filter { h(it.statusdb)}.size.toFloat() / childSize * 100
    }


}