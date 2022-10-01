package com.filemanager.explorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.filemanager.core.entity.FileModel
import com.filemanager.core.ultils.FileUtils
import javax.inject.Inject

class ListFileFragmentViewModel @Inject constructor(
    val fileUtils: FileUtils,
    path: String
) : ViewModel() {

    private val _files: MutableLiveData<List<FileModel>> = MutableLiveData()
    val files: LiveData<List<FileModel>> = _files

    init {
        _files.value = fileUtils.getFilesFromPath(path)?.let {
            fileUtils.getFileModelsFromFiles(it)
        }
    }
}