package com.example.projectcontact.util.filter

import com.example.projectcontact.model.Child

object FeedingProgram {
    fun e(child: Child):Boolean{
        return child.forfeeding == "Yes"
    }
    fun fpList(childList : List<Child>): List<Child>{
        return childList.filter{e(it)}.toMutableList()
    }
}