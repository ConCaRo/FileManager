package com.filemanager.explorer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.filemanager.core.entity.FileModel
import com.filemanager.core.entity.FileType
import com.filemanager.core.ultils.FileUtils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class ListFileFragmentViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @SpyK
    var fileUtils = FileUtils()

    private val path = "/"
    private val listFileModel = listOf(
        FileModel("/Picture/DCIMM", FileType.FOLDER, "DCIMM", 0.0),
        FileModel("/Picture/image.png", FileType.FILE, "image.png", 0.0)
    )
    private lateinit var listFileFragmentViewModel: ListFileFragmentViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `init list file model`() {
        val file = File("Picture")
        val listSystemFile = listOf(file)
        every { fileUtils.getFilesFromPath(path) } returns listSystemFile
        every { fileUtils.getFileModelsFromFiles(listSystemFile) } returns listFileModel

        val observeQuery = mockk<Observer<List<FileModel>>>(relaxed = true)

        listFileFragmentViewModel = ListFileFragmentViewModel(fileUtils, path)
        listFileFragmentViewModel.files.observeForever(observeQuery)

        verify { fileUtils.getFilesFromPath(path) }
        verify { fileUtils.getFileModelsFromFiles(listSystemFile) }
        verify { observeQuery.onChanged(listFileModel) }
    }
}