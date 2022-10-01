package com.filemanager.core.ultils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.filemanager.core.entity.FileModel
import com.filemanager.core.entity.FileType
import java.io.File

class FileUtils {
    fun getFileModelsFromFiles(files: List<File>): List<FileModel> {
        return files.map {
            FileModel(
                it.path,
                FileType.getFileType(it),
                it.name,
                convertFileSizeToMB(it.length()),
                it.extension,
                it.listFiles()?.size
                    ?: 0,
                it.lastModified()
            )
        }
    }

    fun getFilesFromPath(
        path: String,
        showHiddenFiles: Boolean = false,
        onlyFolders: Boolean = false
    ): List<File> {
        val file = File(path)
        return file?.listFiles()
            .filter { showHiddenFiles || !it.name.startsWith(".") }
            .filter { !onlyFolders || it.isDirectory }
            .toList()
    }

    fun launchFileIntent(context: Context, fileModel: FileModel, title: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = FileProvider.getUriForFile(context, context.packageName, File(fileModel.path))
        intent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION.or(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        context.startActivity(Intent.createChooser(intent, title))
    }

    private fun convertFileSizeToMB(sizeInBytes: Long): Double {
        return (sizeInBytes.toDouble()) / (1024 * 1024)
    }
}