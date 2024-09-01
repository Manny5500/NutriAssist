package com.example.projectcontact.util.pdf.summary_report

import android.annotation.SuppressLint
import com.example.projectcontact.model.Child
import com.example.projectcontact.util.data_analysis.summary_report.ConsolidatedData
import com.example.projectcontact.util.data_analysis.summary_report.ListData
import com.example.projectcontact.util.data_analysis.summary_report.SummaryData


class SRPdfTableMaker(
    var childList: List<Child>
) {
    var tfAges: Array<String> = Array(19){""}
    private val sSum = SummaryData(childList)
    private val sConso = ConsolidatedData(childList)
    private val sList = ListData(childList)
    var masterData: Array<Array<String>> = Array(13) {
        Array(26){""}
    }
    var sumData: Array<Array<String>> = Array(5) {
        Array(6){""}
    }

    init {
        setMasterData()
        setSumData()
        this.tfAges = getTfAgess()
    }

    private fun getTfAgess(): Array<String> {
        val tfAgesTemp = Array(19){""}
        val tfAgesInt: IntArray = sList.tfAges()
        tfAgesTemp[0] = "Total"
        for (i in 1..18) {
            tfAgesTemp[i] = tfAgesInt[i - 1].toString()
        }
        return tfAgesTemp
    }

    private fun setMasterData() {
        listDataPart()
        consolidatedDataPart()
        ipChildrenAndCategoryPart()
    }

    private fun listDataPart(){
        val listData = sList.countNow()

        for (i in 0..3) {
            var k = 0
            var l = 0
            for (j in 0..5) {
                //--WFA
                masterData[i][k + 1] = listData[i + l][0].toString()
                masterData[i][k + 2] = listData[i + l][1].toString()
                masterData[i][k + 3] = (listData[i + l][0] + listData[i + l][1]).toString()
                k += 3
                l += 4
            }
        }

        for (i in 4..7) {
            var k = 0
            var l = 20
            for (j in 0..5) {
                //--HFA
                masterData[i][k + 1] = listData[i + l][0].toString()
                masterData[i][k + 2] = listData[i + l][1].toString()
                masterData[i][k + 3] = (listData[i + l][0] + listData[i + l][1]).toString()

                k +=3
                l +=4
            }
        }

        for (i in 8..12) {
            var k = 0
            var l = 40
            for (j in 0..5) {
                //--WFH
                masterData[i][k + 1] = listData[i + l][0].toString()
                masterData[i][k + 2] = listData[i + l][1].toString()
                masterData[i][k + 3] = (listData[i + l][0] + listData[i + l][1]).toString()
                k += 3
                l += 5
            }
        }

    }

    @SuppressLint("DefaultLocale")
    private fun consolidatedDataPart(){
        val consoNowData = sConso.countNow()
        val consoData = consoNowData.first
        val consoPerc = consoNowData.second.map{
            val formatted = String.format("%.2f", it*100f)
            if(formatted == "NaN") "0.00" else formatted
        }.toTypedArray()
        for (i in 0..12) {
            var k = 0
            for (j in 19..22) {
                if (j % 2 == 0) {
                    masterData[i][j] = consoPerc[i]
                } else {
                    masterData[i][j] = consoData[i].toString()
                }
                k += 13
            }
        }
    }

    private fun ipChildrenAndCategoryPart(){
        val category: Array<String> = arrayOf(
            "WFA - Normal", "WFA - OW", "WFA - UW", "WFA - SUW",
            "HFA - Normal", "HFA - Tall", "HFA - St", "HFA - Sst",
            "WFL/H - Normal", "WFL/H - OW", "WFL/H - Ob", "WFL/H - MW", "WFL/H - Sw"
        )
        for (i in 0..12) {
            masterData[i][0] = category[i]
            for (j in 23..25) {
                masterData[i][j] = "0"
            }
        }
    }

    private fun setSumData() {
        val motherLabels = sSum.motherDataLabels
        val eOPTLabels = sSum.eOPTLabels
        val summaryLabels = sSum.summaryDataLabels
        val motherData = sSum.motherData().map{it.toString()}
        val eOPTData = sSum.eOPTData().map{it.toString()}
        val summaryData = sSum.summaryData().map{it.toString()}
        for (i in 0..4) {
            if (i == 0) {
                continue
            } else {
                sumData[i][0] = eOPTLabels[i-1]
                sumData[i][1] = eOPTData[i-1]
            }
        }
        for (i in 0..4) {
            sumData[i][2] = motherLabels[i]
            sumData[i][3] = motherData[i]
            sumData[i][4] = summaryLabels[i]
            sumData[i][5] = summaryData[i]
        }
    }

}