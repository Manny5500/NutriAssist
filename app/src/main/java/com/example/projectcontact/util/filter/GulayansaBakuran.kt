package com.example.projectcontact.util.filter

import com.example.projectcontact.model.Parent

object GulayansaBakuran {
    fun d(parent: Parent): Boolean{
        return  parent.children.any{child->
            child.forgulayan == "Yes"
        }
    }
    fun gsbList(parentList: List<Parent>):List<Parent>{
        return parentList.filter{d(it)}.toMutableList()
    }
}