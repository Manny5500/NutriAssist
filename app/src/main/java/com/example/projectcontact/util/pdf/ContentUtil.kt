package com.example.projectcontact

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.itextpdf.text.BadElementException
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import java.io.ByteArrayOutputStream
import java.io.IOException

object ContentUtil {
    fun addCell(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(6f)
        cell.borderColor = BaseColor.BLACK
        table.addCell(cell)
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
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(6f)
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.borderColor = BaseColor.BLACK
        table.addCell(cell)
    }

    @Throws(DocumentException::class)
    fun addText(text: String?, document: Document) {
        val paragraph = Paragraph(text, Font(Font.FontFamily.HELVETICA, 12f))
        paragraph.alignment = Element.ALIGN_CENTER
        document.add(paragraph)
    }

    @Throws(DocumentException::class)
    fun addText(text: String?, document: Document, isBold: Boolean) {
        val paragraph = Paragraph(text, Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD))
        paragraph.alignment = Element.ALIGN_CENTER
        document.add(paragraph)
    }

    @Throws(DocumentException::class)
    fun addTextRF(text: String?, document: Document, size: Int, font_type: Int) {
        val paragraph = Paragraph(text, Font(Font.FontFamily.HELVETICA, size.toFloat(), font_type))
        paragraph.alignment = Element.ALIGN_CENTER
        document.add(paragraph)
    }

    @Throws(DocumentException::class)
    fun addText(text: String?, document: Document, isBold: Boolean, align: String?) {
        val paragraph: Paragraph
        paragraph = if (isBold) {
            Paragraph(text, Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD))
        } else {
            Paragraph(text, Font(Font.FontFamily.HELVETICA, 12f))
        }
        setAlign(paragraph, align)
        document.add(paragraph)
    }

    fun setAlign(paragraph: Paragraph, align: String?) {
        when (align) {
            "left" -> paragraph.alignment = Element.ALIGN_LEFT
            "right" -> paragraph.alignment = Element.ALIGN_RIGHT
            "justify" -> paragraph.alignment = Element.ALIGN_JUSTIFIED
            else -> paragraph.alignment = Element.ALIGN_CENTER
        }
    }

    fun percentage(number1: Int, number2: Int): Float {
        val result = number1.toFloat() / number2
        return Math.round(result * 100.0f).toFloat()
    }

    fun addCell(table: PdfPTable, text: String?) {
        val cell = PdfPCell(Phrase(text))
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(6f)
        table.addCell(cell)
    }

    fun specificCell(table: PdfPTable, text: String?, cellColor: BaseColor?) {
        val cell = PdfPCell(Phrase(text))
        cell.backgroundColor = cellColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(6f)
        table.addCell(cell)
    }

    fun leftAlignedCell(table: PdfPTable, text: String?) {
        val cell = PdfPCell(Phrase(text))
        cell.horizontalAlignment = Element.ALIGN_LEFT
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(6f)
        table.addCell(cell)
    }


    fun addCell(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(6f)
        cell.colspan = colspan
        cell.borderColor = BaseColor.BLACK
        table.addCell(cell)
    }

    fun addCellML(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?
    ) {
        val cell =
            PdfPCell(Paragraph(text, Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD, textColor)))
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(6f)
        cell.borderColor = BaseColor.BLACK
        table.addCell(cell)
    }

    fun addCellML(
        table: PdfPTable, text: String?, backgroundColor: BaseColor?, textColor: BaseColor?,
        colspan: Int, rowspan: Int, padding: Int, boldness: Int, border: Int, fontsize: Int
    ) {
        val cell = PdfPCell(
            Paragraph(
                text,
                Font(Font.FontFamily.HELVETICA, fontsize.toFloat(), boldness, textColor)
            )
        )
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(padding.toFloat())
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.borderColor = BaseColor.BLACK
        cell.border = border
        table.addCell(cell)
    }

    @Throws(BadElementException::class, IOException::class)
    fun addCellMLLogo(
        table: PdfPTable,
        text: String?,
        backgroundColor: BaseColor?,
        textColor: BaseColor?,
        colspan: Int,
        rowspan: Int,
        padding: Int,
        boldness: Int,
        border: Int,
        fontsize: Int,
        drawable: Drawable
    ) {
        val cell = PdfPCell(
            Paragraph(
                text,
                Font(Font.FontFamily.HELVETICA, fontsize.toFloat(), boldness, textColor)
            )
        )
        cell.backgroundColor = backgroundColor
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE)
        cell.setPadding(padding.toFloat())
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.borderColor = BaseColor.BLACK
        cell.border = border
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        val img = Image.getInstance(byteArray)
        cell.addElement(img)
        table.addCell(cell)
    }
}