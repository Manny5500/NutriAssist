package com.example.projectcontact.util.pdf.prevalance_report

import android.annotation.SuppressLint
import com.example.projectcontact.ContentUtil
import com.example.projectcontact.model.Barangay
import com.example.projectcontact.util.pdf.BaseColor.black
import com.example.projectcontact.util.pdf.BaseColor.laurelGreen
import com.example.projectcontact.util.pdf.BaseColor.paleSilver
import com.example.projectcontact.util.pdf.PDFContentMaker
import com.itextpdf.text.Document
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream

 class PRPdf(
     private var statuses: String = "",
     private var barangayList: List<Barangay> = emptyList(),
     private var methodType: String = "",
     private var statusList: List<String> = emptyList()
) : PDFContentMaker {
    private val customSize = Rectangle(
        8.5f * 72,
        13.0f * 72
    )
    override fun content(byteArrayOutputStream: ByteArrayOutputStream) {
        val document = Document(customSize)
        PdfWriter.getInstance(document, byteArrayOutputStream)
        document.open()
        header(document)
        document.add(body())
        document.close()
    }

     override fun header(): PdfPTable {
         return PdfPTable(0)
     }
     private fun header(document: Document){
         val header = listOf("Region IVA: CALABARZON", "MUNICIPALITY OF MAGDALENA",
             "PROVINCE: LAGUNA", "OPERATION TIMBANG PLUS 2023", statuses.uppercase(),
             "PREVALANCE AND NUMBER OF AFFECTED CHILDREN UNDER FIVE, BY BARANGAY",
             "\n")
         for(i in header.indices){
             when{
                 i==0 || i==4 -> ContentUtil.addText(header[i], document, true)
                 else -> ContentUtil.addText(header[i],document)
             }
         }
    }

    @SuppressLint("DefaultLocale")
    override fun body(): PdfPTable {
        val table = PdfPTable(6)
        table.widthPercentage = 100f
        val columnWidths = floatArrayOf(10f, 24f, 15f, 17f, 17f, 17f)
        table.setWidths(columnWidths)

        //table-header
        val normalLabel = "Normal (%)"
        val statusPercentageLabel = "${statusList[0]} ${statusList[1]} (%)"
        val statusNumberLabel = "Number of ${statusList[0]} + ${statusList[1]}"
        val header = listOf("Rank", "Barangay", "0-59 Months OPT Coverage (%)",
            methodType, normalLabel, statusPercentageLabel, statusNumberLabel)

        for(i in header.indices){
            when{
                i<3 ->ContentUtil.addCell(table, header[i], laurelGreen, black, 0, 2)
                i==3 -> ContentUtil.addCell(table, header[i], paleSilver, black, 3, 1)
                else -> ContentUtil.addCell(table, header[i], laurelGreen, black)
            }
        }

        //table-data
        for (barangay in barangayList) {

            val position = barangayList.indexOf(barangay) + 1
            val optCoverage =  String.format("%.2f%%", barangay.optCoverage)
            val normalRate =   String.format("%.2f%%", barangay.normalRate)
            val totalRate =  String.format("%.2f%%", barangay.totalRate)
            val data = listOf(position.toString(), barangay.barangay, optCoverage,
                normalRate, totalRate, barangay.totalCase.toString())

            for(j in data.indices){
                when (j) {
                    1 -> ContentUtil.leftAlignedCell(table, data[j])
                    4 -> ContentUtil.specificCell(table, data[j], paleSilver)
                    else -> ContentUtil.addCell(table, data[j])
                }
            }

        }
        return table
    }

    override fun pdfSetter(): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        content(byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}