package com.example.projectcontact.util.pdf

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.IOException


object SavePDF {
    @SuppressLint("Recycle")
    fun toStorage(filename: String, resolver:ContentResolver, byteArray:ByteArray) {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "NutriAssist")
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH,
            Environment.DIRECTORY_DOCUMENTS + File.separator + "NutriAssist"
        )
        val externalUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val pdfUri: Uri? = resolver.insert(externalUri, values)
        try {
            if (pdfUri != null) {
                resolver.openOutputStream(pdfUri)?.write(byteArray)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}