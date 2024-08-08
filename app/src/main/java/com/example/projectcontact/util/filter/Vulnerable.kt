package com.example.projectcontact.util.filter

import com.example.projectcontact.model.Parent

object Vulnerable  {

    fun a(parent: Parent):Boolean{
        return parent.children.size > 1
    }

    fun b(parent: Parent):Boolean{
        return parent.monthlyIncome in setOf(
            "Less than 9,100",
            "9,100 to 18,200",
            "18,200 to 36,400"
        )
    }

    fun c(parent: Parent):Boolean{
        return parent.children.any{child ->
            child.statusdb.any{
                it in listOf("Underweight",
                    "Overweight", "Severe Underweight",
                    "Wasted", "Severe Wasted", "Obese",
                    "Stunted", "Severe Stunted")
            }
        }
    }

    fun moreThanOneChild(parentList: List<Parent>):List<Parent>{
        return parentList.filter {a(it) }.toMutableList()
    }

    fun lowIncome(parentList: List<Parent>):List<Parent>{
        return parentList.filter {b(it)}.toMutableList()
    }

    fun haveMalnourished(parentList : List<Parent>) : List<Parent>{
        return parentList.filter {c(it)}.toMutableList()
    }

    fun vList(parentList : List<Parent>) : List<Parent>{
        return parentList.filter {a(it) || b(it) || c(it)}.toMutableList()
    }

}