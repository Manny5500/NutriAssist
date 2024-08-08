package com.example.projectcontact.util.status


import com.example.projectcontact.util.growth_chart.HFA_Boys
import com.example.projectcontact.util.growth_chart.HFA_Girls
import com.example.projectcontact.util.growth_chart.LFA_Boys
import com.example.projectcontact.util.growth_chart.LFA_Girls
import com.example.projectcontact.util.growth_chart.WFA_Boys
import com.example.projectcontact.util.growth_chart.WFA_Girls
import com.example.projectcontact.util.growth_chart.WFH_Boys
import com.example.projectcontact.util.growth_chart.WFH_Girls
import com.example.projectcontact.util.growth_chart.WFL_Boys
import com.example.projectcontact.util.growth_chart.WFL_Girls

class StatusFetcher {

    fun individualTest(
        age: Int,
        weight: Double,
        height: Double,
        sex: String
    ): Array<String> {
        if(age < 0 || age > 59) return emptyArray()
        val status = arrayOf("Normal", "Normal", "Normal")
        if (sex == "Male") {
            val wfa = WFA_Boys()
            if (wfa.WFA_Boys_M(age, weight) != "") {
                status[0] = wfa.WFA_Boys_M(age, weight)
            }
            if (age < 24) {
                val lfaBoys = LFA_Boys()
                if (lfaBoys.LFA_Boys_M(height, age) != "") {
                    status[1] = lfaBoys.LFA_Boys_M(height, age)
                }

                val wflBoys = WFL_Boys()
                if (wflBoys.WFL_Boys_M(weight, height) != "") {
                    status[2] = wflBoys.WFL_Boys_M(weight, height)
                }
            } else{
                val hfaBoys = HFA_Boys()
                if (hfaBoys.HFA_Boys_M(height, age) != "") {
                    status[1] = hfaBoys.HFA_Boys_M(height, age)
                }
                val wfhBoys = WFH_Boys()
                if (wfhBoys.WFH_Boys_M(weight, height) != "") {
                    status[2] = wfhBoys.WFH_Boys_M(weight, height)
                }
            }
        } else if (sex == "Female") {
            val wfag = WFA_Girls()
            if (wfag.WFA_Girls_M(age, weight) != "") {
                status[0] = wfag.WFA_Girls_M(age, weight)
            }
            if (age < 24) {
                val lfaGirls = LFA_Girls()
                if (lfaGirls.LFA_Girls_M(height, age) != "") {
                    status[1] = lfaGirls.LFA_Girls_M(height, age)
                }
                val wflGirls = WFL_Girls()
                if (wflGirls.WFL_Girls_M(weight, height) != "") {
                    status[2] = wflGirls.WFL_Girls_M(weight, height)
                }
            } else{
                val hfaGirls = HFA_Girls()
                if (hfaGirls.HFA_Girls_M(height, age) != "") {
                    status[1] = hfaGirls.HFA_Girls_M(height, age)
                }
                val wfhGirls = WFH_Girls()
                if (wfhGirls.WFH_Girls_M(weight, height) != "") {
                    status[2] = wfhGirls.WFH_Girls_M(weight, height)
                }
            }
        }
        return status
    }

    fun toSimpleList(status:List<String>): List<String> {
        val _status = status.toMutableList()
        if(status.all{it=="Normal"}){
            return listOf("Normal")
        }

        val statusesToCheck = setOf("Overweight", "Obese", "Underweight", "Severe Underweight")
        val count = status.count { it in statusesToCheck }

        if (count > 1 && status.firstOrNull() in setOf("Underweight", "Overweight")){
            _status.removeFirstOrNull()
        }
        _status.remove("Normal")
        _status.remove("Normal")

        return _status
    }


}