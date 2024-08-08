package com.example.projectcontact.util.pdf.malnourished_list

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import com.example.projectcontact.ContentUtil.addCell
import com.example.projectcontact.ContentUtil.addCellML
import com.example.projectcontact.ContentUtil.addCellMLLogo
import com.example.projectcontact.model.Child
import com.example.projectcontact.util.AgeUtil.monthsBetweenToday
import com.example.projectcontact.util.pdf.BaseColor.getBaseColor
import com.example.projectcontact.util.pdf.BaseColor.greenColor
import com.example.projectcontact.util.pdf.BaseColor.orangeColor
import com.example.projectcontact.util.pdf.BaseColor.purpleColor
import com.example.projectcontact.util.pdf.BaseColor.redColor
import com.example.projectcontact.util.pdf.BaseColor.yellowColor
import com.example.projectcontact.util.DateUtil.toDateFormate
import com.example.projectcontact.util.DateUtil.month
import com.example.projectcontact.util.DateUtil.sdf
import com.example.projectcontact.util.Notation.genderNotation
import com.example.projectcontact.util.pdf.PDFContentMaker
import com.example.projectcontact.util.Notation.statsNotation
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream

class MLPdf(
    private val drawable: Drawable,
    private val barangay: String,
    private val arrayList: List<Child>
    ) : PDFContentMaker {

    private val baseColor = getBaseColor("#2596be")
    private val customSize = Rectangle(
        13.0f * 72,
        8.5f * 72
    )

    override fun content(byteArrayOutputStream: ByteArrayOutputStream) {
        val document = Document(customSize)
        PdfWriter.getInstance(document, byteArrayOutputStream)
        document.open()
        document.add(header())
        document.add(body())
        document.close()
    }

    override fun header(): PdfPTable {
        val tableHead = PdfPTable(25)
        tableHead.widthPercentage = 100f
        val border4: Int = Rectangle.NO_BORDER

        var colSpanArr = intArrayOf(5, 15, 1, 2, 2)
        val rowsSpanArr = intArrayOf(1, 1, 1, 1, 2)

        var headerArr1 = arrayOf(
            "", "Community Level e-OPT PLUS Tool", "", month, ""
        )

        for (i in 0..3) {
            addCellML(
                tableHead, headerArr1[i],
                yellowColor, BaseColor.BLACK, colSpanArr[i],
                rowsSpanArr[i], 6, 1, border4, 14
            )
        }

        addCellMLLogo(
            tableHead, "",
            yellowColor, BaseColor.BLACK,
            2, 2, 6, 1, border4,
            14, drawable
        )

        headerArr1 = arrayOf("", "", "", "", "")
        headerArr1[1] = "For a maximum of 100 children in a small or medium sized barangay, " +
                "use this file for a purok, section or part of the barangay"
        for (i in 0..3) {
            addCellML(
                tableHead, headerArr1[i],
                yellowColor, BaseColor.BLACK, colSpanArr[i], rowsSpanArr[i],
                4, 0, border4, 9
            )
        }

        headerArr1[1] = "WEIGHT FOR AGE, HEIGHT FOR AGE & WEIGHT FOR LENGTH STATUS"
        headerArr1[2] = "Region: IVA CALABARZON"
        colSpanArr = intArrayOf(5, 15, 5, 0, 0)
        for (i in 0..2) {
            addCellML(
                tableHead, headerArr1[i],
                yellowColor, BaseColor.BLACK, colSpanArr[i], rowsSpanArr[i],
                4, 1, border4, 12
            )
        }

        addCellML(
            tableHead, "",
            BaseColor.DARK_GRAY, BaseColor.WHITE,
            25, 1, 4, 1, border4, 11
        )
        addCellML(
            tableHead, " ",
            BaseColor.DARK_GRAY, BaseColor.WHITE,
            25, 1, 4, 1, border4, 9
        )

        val colSpanArr2 = intArrayOf(7, 5, 1, 5, 1, 5, 1)

        val headerArr2 = arrayOf("", "", "", "", "", "", "")
        headerArr2[1] = "Barangay: $barangay"
        headerArr2[3] = "Municipality: MAGDALENA"
        headerArr2[5] = "Province: Laguna"
        for (i in 0..6) {
            addCellML(
                tableHead, headerArr2[i],
                purpleColor, BaseColor.BLACK, colSpanArr2[i],
                1, 4, 1, border4, 12
            )
        }

        return tableHead

    }

    @SuppressLint("SimpleDateFormat")
    override fun body(): PdfPTable {
        val table = PdfPTable(14)
        table.widthPercentage = 100f
        val columnWidths = floatArrayOf(
            12f, 25f, 40f, 40f, 15f,
            15f, 18f, 18f, 12f, 12f,
            13f, 14f, 14f, 14f
        )
        table.setWidths(columnWidths)
        val thLabel = arrayOf(
            "Child Seq", "Address or Location of Residence",
            "Name of mother or caregiver", "Full Name of Child",
            "Belong to IP Group", "Sex",
            "Date of Birth", "Actual Date of Weighing",
            "Height", "Weight", "Age in Mos",
            "WFA Status", "HFA Status", "WFL/H Status"
        )

        for(i in 0..13){
            if(i>10){
                addCellML(table, thLabel[i], BaseColor.BLACK, BaseColor.WHITE)
            }else{
                addCellML(table, thLabel[i], baseColor, BaseColor.WHITE)
            }
        }
        for (child: Child in arrayList) {
            val position: Int = arrayList.indexOf(child) + 1

            val dataValue = arrayOf(
                "" + position, barangay, child.parentFullName,
                child.fullestName, child.belongtoIP, genderNotation(child.sex),
                child.birthDate, sdf.format(child.dateAdded), "" + child.height,
                "" + child.weight, ""+ monthsBetweenToday(toDateFormate(child.birthDate))
            )

            for(str in dataValue){
                table.addCell(Phrase(str))
            }

            val statsValue = child.status.map { statsNotation(it) }.toTypedArray()
            for(str in statsValue){
                addCell(table, str, colorCodeStatus(str), BaseColor.BLACK)
            }

        }
        return table
    }

    override fun pdfSetter(): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        content(byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    private fun colorCodeStatus(status:String):BaseColor{
        return when(status){
            "N"  -> greenColor
            "" -> greenColor
            "T" -> greenColor
            "UW"  -> yellowColor
            "ST" -> yellowColor
            "MW" -> yellowColor
            "SUW" -> redColor
            "SST" -> redColor
            "SW" -> redColor
            "OW" -> orangeColor
            "OB" -> orangeColor
            else -> BaseColor.WHITE
        }
    }
}