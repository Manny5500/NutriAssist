package com.example.projectcontact.util.pdf

import com.itextpdf.text.pdf.PdfPTable
import java.io.ByteArrayOutputStream

interface PDFContentMaker {
    fun content(byteArrayOutputStream: ByteArrayOutputStream)
    fun header():PdfPTable
    fun body():PdfPTable
    fun pdfSetter():ByteArray
}