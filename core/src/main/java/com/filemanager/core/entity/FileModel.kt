package com.filemanager.core.entity

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class FileModel(
    val path: String,
    val fileType: FileType,
    val name: String,
    val sizeInMB: Double,
    val extension: String = "",
    val subFiles: Int = 0,
    val lastModified: Long = 0L
) {
    val timeModified: String
        get() {
            val formatter = SimpleDateFormat("MM dd,yyyy HH:mm")
            return formatter.format(Date(lastModified))
        }
    val isFolder: Boolean
        get() = fileType == FileType.FOLDER
}

enum class FileType {
    FILE,
    FOLDER;

    companion object {
        fun getFileType(file: File) = when (file.isDirectory) {
            true -> FOLDER
            false -> FILE
        }
    }
}
