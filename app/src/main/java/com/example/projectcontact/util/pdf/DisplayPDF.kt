package com.example.projectcontact.util.pdf

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DisplayPDF {
    fun fromBytes(
        context: Context,
        pdfBytes: ByteArray,
        pdfView: PDFView,
        progressBar: ProgressBar
    ) {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (pdfBytes.isNotEmpty()) {
                    // Perform the PDF loading on the IO thread
                    withContext(Dispatchers.Main) {
                        pdfView.fromBytes(pdfBytes)
                            .scrollHandle(DefaultScrollHandle(context))
                            .load()
                        progressBar.visibility = View.GONE
                        Log.e("PDFViewer", "Success loading PDF")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("PDFViewer", "PDF bytes are empty")
                        progressBar.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    Log.e("PDFViewer", "Error loading PDF: ${e.message}")
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

}
