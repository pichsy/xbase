package com.pichs.xbase.xlog

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

object XLogFileUtils {

    private const val BUFFER_SIZE = 500 * 1024 // 500KB
    fun writeToFile(filePath: String, content: MutableList<String>) {
        val file = File(filePath)
        var bw: BufferedWriter? = null
        try {
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileOutputStream = FileOutputStream(file.absoluteFile, true)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream, Charsets.UTF_8)
            bw = BufferedWriter(outputStreamWriter, BUFFER_SIZE)
            for (line in content) {
                bw.write(line)
            }
            bw.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                bw?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun getAllFilesWithModifiedDate(context: Context, directoryName: String): List<Pair<String, Long>> {
        val resolver = context.contentResolver
        val projection = arrayOf(MediaStore.Files.FileColumns.DISPLAY_NAME, MediaStore.Files.FileColumns.DATE_MODIFIED)
        val selection = "${MediaStore.Files.FileColumns.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("vnd.android.document%$directoryName%")

        val cursor = resolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            selectionArgs,
            null
        )

        val fileDetails = mutableListOf<Pair<String, Long>>()
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                val fileName = it.getString(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                val modifiedDate = it.getLong(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED))
                val fileUri = ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), id)
                fileDetails.add(Pair(fileName, modifiedDate))
            }
        }

        return fileDetails
    }




}