package com.example.projectcontact.util.pdf.summary_report

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.itextpdf.text.BadElementException
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import java.io.ByteArrayOutputStream
import java.io.IOException


object SRPDF_CU {


    fun setAlign(paragraph: Paragraph, align: String?) {
        when (align) {
            "left" -> paragraph.alignment = Element.ALIGN_LEFT
            "right" -> paragraph.alignment = Element.ALIGN_RIGHT
            "justify" -> paragraph.alignment = Element.ALIGN_JUSTIFIED
            else -> paragraph.alignment = Element.ALIGN_CENTER
        }
    }

    fun addCell(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int,
        rowspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(4f)
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.borderColor = BaseColor.BLACK
        table.addCell(cell)
    }


    fun percentage(number1: Int, number2: Int): String {
        val result = number1.toFloat() / number2
        val percentage = result * 100.0f
        return String.format("%.2f", percentage) + " %"
    }

    fun addCell(table: PdfPTable, text: String?) {
        val cell = PdfPCell(Phrase(text))
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(2f)
        table.addCell(cell)
    }

    fun specificCell(table: PdfPTable, text: String?, cellColor: BaseColor?) {
        val cell = PdfPCell(Phrase(text))
        cell.backgroundColor = cellColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(4f)
        table.addCell(cell)
    }

    fun leftAlignedCell(table: PdfPTable, text: String?) {
        val cell = PdfPCell(Phrase(text))
        cell.horizontalAlignment = Element.ALIGN_LEFT
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(4f)
        table.addCell(cell)
    }

    fun getBaseColor(hexColor: String): BaseColor {
        val red = hexColor.substring(1, 3).toInt(16)
        val green = hexColor.substring(3, 5).toInt(16)
        val blue = hexColor.substring(5, 7).toInt(16)
        val baseColor = BaseColor(red, green, blue)
        return baseColor
    }

    fun addCell(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_RIGHT
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        cell.paddingLeft = 0f
        cell.paddingRight = 0f
        cell.colspan = colspan
        cell.border = Rectangle.TOP or Rectangle.LEFT or Rectangle.BOTTOM
        table.addCell(cell)
    }

    fun addCellTitle(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(4f)
        cell.colspan = colspan
        cell.borderColor = BaseColor.BLACK
        table.addCell(cell)
    }


    fun addCellR(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        rowspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_RIGHT
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        cell.paddingLeft = 0f
        cell.paddingRight = 0f
        cell.rowspan = rowspan
        cell.border = Rectangle.TOP or Rectangle.LEFT or Rectangle.BOTTOM
        table.addCell(cell)
    }

    fun addCellwRotates(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        rowspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_RIGHT
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        cell.paddingLeft = 4f
        cell.paddingRight = 4f
        cell.rowspan = rowspan
        cell.border = Rectangle.TOP or Rectangle.LEFT or Rectangle.BOTTOM or Rectangle.RIGHT
        cell.rotation = 90
        table.addCell(cell)
    }

    fun addCellGender(table: PdfPTable, text: String?, textColor: BaseColor?) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL, textColor)))
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(4f)
        cell.paddingRight = 0f
        cell.paddingLeft = 0f
        table.addCell(cell)
    }

    fun addCellPrev(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(4f)
        cell.paddingRight = 0f
        cell.paddingLeft = 0f
        cell.backgroundColor = backgroundColor
        cell.borderColor = textColor
        table.addCell(cell)
    }

    fun addCellTH(table: PdfPTable, text: String?) {
        val cell = PdfPCell(Phrase(text))
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(2f)
        table.addCell(cell)
    }

    fun addCellColTH(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_RIGHT
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(2f)
        cell.colspan = colspan
        cell.border = Rectangle.NO_BORDER
        table.addCell(cell)
    }

    fun addCellCRTH(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int,
        rowspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(4f)
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.borderColor = BaseColor.GRAY
        table.addCell(cell)
    }

    fun addCellColTHBL(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(2f)
        cell.colspan = colspan
        cell.border = Rectangle.BOTTOM
        table.addCell(cell)
    }

    @Throws(BadElementException::class, IOException::class)
    fun addCellCRTHLogo(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int,
        rowspan: Int,
        drawable: Drawable
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.setPadding(4f)
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.border = Rectangle.NO_BORDER

        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()

        val img = Image.getInstance(byteArray)
        val phrase =
            Phrase("OPT PLUS 2024", Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD, textColor))
        cell.addElement(img)
        cell.addElement(phrase)
        table.addCell(cell)
    }

    fun addCellColor(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_RIGHT
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.colspan = colspan
        cell.border = Rectangle.NO_BORDER
        cell.setPadding(2f)
        table.addCell(cell)
    }

    fun addCellColor(table: PdfPTable, text: String?, backgroundColor: BaseColor?) {
        val cell = PdfPCell(Phrase(text))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.border = Rectangle.NO_BORDER
        cell.setPadding(2f)
        table.addCell(cell)
    }

    fun addCellColorCenter(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int,
        rowspan: Int,
        fontstyle: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, fontstyle, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        cell.paddingLeft = 0f
        cell.paddingRight = 0f
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.border = Rectangle.TOP or Rectangle.LEFT or Rectangle.BOTTOM or Rectangle.RIGHT
        table.addCell(cell)
    }

    fun addCCCReg(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        cell.paddingLeft = 0f
        cell.paddingRight = 0f
        cell.colspan = colspan
        cell.border = Rectangle.TOP or Rectangle.LEFT or Rectangle.BOTTOM
        table.addCell(cell)
    }

    fun addCellReg(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?
    ) {
        val cell =
            PdfPCell(Phrase(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.borderColor = textColor
        cell.setPadding(2f)
        table.addCell(cell)
    }

    fun addCellRegLeft(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?
    ) {
        val cell =
            PdfPCell(Phrase(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_LEFT
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.borderColor = textColor
        cell.setPadding(2f)
        table.addCell(cell)
    }

    fun addCellRegRight(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?
    ) {
        val cell =
            PdfPCell(Phrase(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_RIGHT
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.borderColor = textColor
        cell.setPadding(2f)
        table.addCell(cell)
    }

    fun addCellCenter(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        cell.paddingLeft = 0f
        cell.paddingRight = 0f
        cell.colspan = colspan
        cell.border = Rectangle.TOP or Rectangle.BOTTOM
        table.addCell(cell)
    }

    fun addCellCenterLast(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 11f, Font.NORMAL, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        cell.paddingLeft = 0f
        cell.paddingRight = 0f
        cell.colspan = colspan
        cell.border = Rectangle.TOP or Rectangle.BOTTOM or Rectangle.RIGHT
        table.addCell(cell)
    }
}
