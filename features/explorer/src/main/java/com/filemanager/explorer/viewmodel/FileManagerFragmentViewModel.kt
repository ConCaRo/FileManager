package com.filemanager.explorer.viewmodel

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.filemanager.core.entity.FileModel
import com.filemanager.core.entity.FileType
import com.filemanager.core.ultils.FileUtils
import javax.inject.Inject

class FileManagerFragmentViewModel @Inject constructor() : ViewModel() {

    private val _stackFiles: MutableLiveData<List<FileModel>> = MutableLiveData<List<FileModel>>().apply { value = listOf(FileModel(
        Environment.getExternalStorageDirectory().absolutePath, FileType.FOLDER, "/", 0.0)) }
    val stackFiles: LiveData<List<FileModel>> = _stackFiles

    fun addToStackFiles(fileModel: FileModel) {
        _stackFiles.value = _stackFiles.value?.toMutableList()?.apply { add(fileModel) }
    }

    fun popFromStack() {
        if (!_stackFiles.value.isNullOrEmpty()) {
            _stackFiles.value = _stackFiles.value?.toMutableList()?.apply { removeAt(_stackFiles.value!!.size - 1) }
        }
    }

    fun popFromStackTill(fileModel: FileModel) {
        val index = _stackFiles.value?.indexOf(fileModel) ?: -1
        if (index != -1) {
            _stackFiles.value =
                _stackFiles.value?.toMutableList()?.let { it.subList(0, index.plus(1)) }
        }
    }
}