package com.example.projectcontact.util.pdf.summary_report

import android.graphics.drawable.Drawable
import com.example.projectcontact.util.pdf.BaseColor.blueColor
import com.example.projectcontact.util.pdf.BaseColor.darkerGray
import com.example.projectcontact.util.pdf.BaseColor.darkestGray
import com.example.projectcontact.util.pdf.BaseColor.grayColor
import com.example.projectcontact.util.pdf.BaseColor.peachColor
import com.example.projectcontact.util.pdf.BaseColor.yellowColor
import com.example.projectcontact.util.pdf.PDFContentMaker
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCCCReg
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCell
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellCenter
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellCenterLast
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellColTH
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellColTHBL
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellColor
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellColorCenter
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellGender
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellPrev
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellReg
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellRegLeft
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellRegRight
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellTitle
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.addCellwRotates
import com.example.projectcontact.util.pdf.summary_report.SRPDF_CU.percentage
import com.itextpdf.text.Document
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream


class SRPdf (
    private val drawable: Drawable,
    private val barangay: String,
    private val estimatedChildren: Int,
    private val childrenMeasured: Int,
    private val population: Int,
    private val srdpPdf: SRPdfTableMaker
): PDFContentMaker {
    private val customSize = Rectangle(
        13.0f * 72,
        8.5f * 72
    )
    val textColor = com.itextpdf.text.BaseColor.WHITE
    val textColor2 = com.itextpdf.text.BaseColor.BLACK

    override fun content(byteArrayOutputStream: ByteArrayOutputStream) {
        val document = Document(customSize)
        PdfWriter.getInstance(document, byteArrayOutputStream)
        document.open()
        document.add(header())
        document.add(body())
        document.close()
    }

    override fun header(): PdfPTable {
        val tableheader = PdfPTable(26)
        tableheader.widthPercentage = 100f

        val masterHeader1 = arrayOf<String>(
            "Province: ", "Laguna", "Regn:", "IV-A CALABARZON", "OPT Plus Coverage:",
            percentage(childrenMeasured, estimatedChildren)
        )
        val masterHeader2 = arrayOf<String>(
            "Barangay: ",
            barangay,
            "Total Popn Barangay:",
            java.lang.String.valueOf(population)
        )
        val masterHeader3 = arrayOf<String>(
            "Municipality: ",
            "MAGDALENA",
            "Estimated Popn of Children 0-59 mos in Barangay:",
            java.lang.String.valueOf(estimatedChildren)
        )


        //--header 1st row
        for (i in 0..5) {
            SRPDF_CU.addCellColTH(tableheader, " ", textColor, textColor2, 0)
        }
        var k = 0
        for (i in 0..1) {
            SRPDF_CU.addCellColTH(
                tableheader,
                masterHeader1.get(0 + i + k),
                textColor,
                textColor2,
                2
            )
            SRPDF_CU.addCellColTHBL(
                tableheader,
                masterHeader1.get(1 + i + k),
                textColor,
                textColor2,
                3
            )
            k++
        }
        SRPDF_CU.addCellColTH(tableheader, masterHeader1.get(4), textColor, textColor2, 2)
        SRPDF_CU.addCellColTHBL(tableheader, masterHeader1.get(5), textColor, textColor2, 2)
        SRPDF_CU.addCellCRTH(
            tableheader,
            "Total # indigenous Preschool Children Measured\n\n 0-59 mos:  0",
            textColor,
            textColor2,
            3,
            2
        )
        SRPDF_CU.addCellCRTHLogo(tableheader, "", textColor, textColor2, 3, 3, drawable)

        //--header 2nd row
        k = 0
        for (i in 0..1) {
            SRPDF_CU.addCellColTH(
                tableheader,
                masterHeader2.get(i + 0 + k),
                textColor,
                textColor2,
                4
            )
            SRPDF_CU.addCellColTHBL(
                tableheader,
                masterHeader2.get(i + 1 + k),
                textColor,
                textColor2,
                3
            )
            k++
        }
        for (i in 0..5) {
            SRPDF_CU.addCellColTH(tableheader, "", textColor, textColor, 0)
        }

        //--header 3rd row
        k = 0
        for (i in 0..1) {
            SRPDF_CU.addCellColTH(
                tableheader,
                masterHeader3.get(i + 0 + k),
                textColor,
                textColor2,
                4
            )
            SRPDF_CU.addCellColTHBL(
                tableheader,
                masterHeader3.get(i + 1 + k),
                textColor,
                textColor2,
                3
            )
            k++
        }
        for (i in 0..8) {
            SRPDF_CU.addCellColTH(tableheader, "", textColor, textColor, 0)
        }
        return tableheader
    }

    override fun body(): PdfPTable {
        val totalHeader = arrayOf(
            "PSGC:",
            "0403416019",
            "Total WFA:",
            srdpPdf.sumData.get(0).get(3),
            "Total HFA:",
            srdpPdf.sumData.get(0).get(3),
            "Total WFL/H:",
            srdpPdf.sumData.get(0).get(3),
            "Birth to 5 years",
            "F1K",
            "#IP Children"
        )
        val header1 = arrayOf(
            "ACRONYMS & ABBREVATIONS",
            " 0-5 Months",
            "6- 11 Months",
            "12-23 Months",
            "24-35 Months",
            "36-47 Months",
            "48-59 Months",
            "0-59 Months",
            "0-23 Months",
            "Boys",
            "Girls",
            "Total"
        )
        val header2 = arrayOf("Boys", "Girls", "Total")
        val header2_1 = arrayOf("Total", "Prev")

        val table = PdfPTable(26)
        table.widthPercentage = 100f
        val columnWidths = floatArrayOf(
            12.11f,
            3.17f, 3.17f, 3.17f,
            3.17f, 3.17f, 3.17f,
            3.17f, 3.17f, 3.17f,
            3.17f, 3.17f, 3.17f,
            3.17f, 3.17f, 3.17f,
            3.17f, 3.17f, 3.17f,
            3.76f, 6.80f,
            3.76f, 6.80f,
            3.17f, 3.17f, 3.17f
        )
        table.setWidths(columnWidths)


        var k = 0
        //table-header - PSGC
        addCellColTH(table, totalHeader.get(0), textColor, textColor2, 2)
        addCellColTHBL(table, totalHeader.get(1), textColor, textColor2, 4)
        addCellColTH(table, "", textColor, textColor, 4)
        for (i in 0..2) {
            addCellColor(table, totalHeader.get(i + 2 + k), grayColor, textColor2, 2)
            addCellColor(table, totalHeader.get(i + 3 + k), yellowColor)
            k++
        }
        addCellColorCenter(table, totalHeader.get(8), textColor2, textColor, 2, 0, 1)
        addCellColorCenter(table, totalHeader.get(9), grayColor, textColor2, 2, 0, 1)
        addCellColorCenter(table, totalHeader.get(10), textColor, textColor2, 11, 0, 1)


        //table-header - ACRO
        addCellColorCenter(table, header1.get(0), textColor, blueColor, 0, 2, 0)
        for (i in 1..6) {
            addCellColorCenter(table, header1.get(i), textColor, textColor2, 3, 0, 0)
        }
        addCCCReg(table, header1.get(7), textColor2, textColor, 2)
        addCCCReg(table, header1.get(8), grayColor, textColor2, 2)
        for (i in 9..11) {
            addCellwRotates(table, header1.get(i), textColor, textColor2, 2)
        }


        //table-header - Boys Girls
        for (i in 0..5) {
            for (j in 0..2) {
                addCellGender(table, header2.get(j), textColor2)
            }
        }

        for (i in 0..1) {
            for (j in 0..1) {
                if (i == 0) {
                    addCellPrev(table, header2_1.get(j), textColor2, textColor)
                } else {
                    addCellPrev(table, header2_1.get(j), grayColor, textColor2)
                }
            }
        }


        //table-data
        for (i in 0..12) {
            for (j in 0..25) {
                if (i < 4 || (i > 7 && i < 13)) {
                    if ((j == 19 || j == 20)) {
                        addCellReg(table, srdpPdf.masterData.get(i).get(j), textColor2, textColor)
                    } else if (j == 21 || j == 22) {
                        addCellReg(table, srdpPdf.masterData.get(i).get(j), grayColor, textColor2)
                    } else {
                        if (j == 0) {
                            addCellRegLeft(
                                table,
                                srdpPdf.masterData.get(i).get(j),
                                textColor,
                                textColor2
                            )
                        } else {
                            addCellReg(
                                table,
                                srdpPdf.masterData.get(i).get(j),
                                textColor,
                                textColor2
                            )
                        }
                    }
                } else if (i > 3 && i < 8) {
                    if ((j == 19 || j == 20)) {
                        addCellReg(table, srdpPdf.masterData.get(i).get(j), darkestGray, textColor2)
                    } else if (j == 21 || j == 22) {
                        addCellReg(table, srdpPdf.masterData.get(i).get(j), darkerGray, textColor2)
                    } else {
                        if (j == 0) {
                            addCellRegLeft(
                                table,
                                srdpPdf.masterData.get(i).get(j),
                                grayColor,
                                textColor2
                            )
                        } else {
                            addCellReg(
                                table,
                                srdpPdf.masterData.get(i).get(j),
                                grayColor,
                                textColor2
                            )
                        }
                    }
                } else {
                    addCellReg(table, srdpPdf.masterData.get(i).get(j), textColor, textColor2)
                }
            }
        }


        for (i in 0..19) {
            if (i == 0) {
                addCellRegRight(table, srdpPdf.tfAges.get(i), textColor2, textColor)
            } else if (i > 0 && i < 19) {
                addCellReg(table, srdpPdf.tfAges.get(i), textColor2, textColor)
            } else {
                addCell(table, "", darkestGray, textColor, 7, 0)
            }
        }

        val colspan = intArrayOf(7, 10, 6)

        val sumHeader = arrayOf(
            "Summary of Children covered by e-OPT Plus",
            "Mothers/Caregivers Summary",
            "Data Summary"
        )
        for (i in 0..2) {
            addCellTitle(table, sumHeader[i], peachColor, textColor2, colspan[i] + 1)
        }

        for (i in 0..4) {
            for (j in 0..5) {
                if (j == 0 || j == 2) {
                    if (i > 2) {
                        addCell(
                            table, srdpPdf.sumData[i][j], grayColor, textColor2,
                            colspan[Math.round((j / 2).toFloat())]
                        )
                    } else {
                        addCell(
                            table, srdpPdf.sumData.get(i).get(j), textColor, textColor2,
                            colspan[Math.round((j / 2).toFloat())]
                        )
                    }
                } else if (j == 4) {
                    addCell(table, srdpPdf.sumData.get(i).get(j), textColor, textColor2, 6)
                } else {
                    if (i > 2 && j < 4) {
                        addCellCenter(
                            table,
                            srdpPdf.sumData.get(i).get(j),
                            grayColor,
                            textColor2,
                            0
                        )
                    } else {
                        addCellCenterLast(
                            table,
                            srdpPdf.sumData.get(i).get(j),
                            textColor,
                            textColor2,
                            0
                        )
                    }
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