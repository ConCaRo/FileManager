package com.filemanager.explorer.viewmodel

import android.os.Environment
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.filemanager.core.entity.FileModel
import com.filemanager.core.entity.FileType
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class FileManagerFragmentViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val path = "/"
    private val rootFileModel = FileModel(path, FileType.FOLDER, "/", 0.0)
    private lateinit var fileFragmentManViewModel: FileManagerFragmentViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(Environment::class)
        every { Environment.getExternalStorageDirectory() } returns File(path)
        fileFragmentManViewModel = FileManagerFragmentViewModel()
    }

    @Test
    fun initStackFiles() {
        val observeQuery = mockk<Observer<List<FileModel>>>(relaxed = true)
        fileFragmentManViewModel.stackFiles.observeForever(observeQuery)

        verify { observeQuery.onChanged(listOf(rootFileModel)) }
    }

    @Test
    fun addToStackFiles() {
        val fileModel = FileModel("/Picture/DCIMM", FileType.FOLDER, "DCIMM", 0.0)
        val observeQuery = mockk<Observer<List<FileModel>>>(relaxed = true)
        fileFragmentManViewModel.stackFiles.observeForever(observeQuery)

        fileFragmentManViewModel.addToStackFiles(fileModel)

        verifyOrder {
            observeQuery.onChanged(listOf(rootFileModel))
            observeQuery.onChanged(listOf(rootFileModel, fileModel))
        }
    }

    @Test
    fun popFromStack() {
        val observeQuery = mockk<Observer<List<FileModel>>>(relaxed = true)
        fileFragmentManViewModel.stackFiles.observeForever(observeQuery)

        fileFragmentManViewModel.popFromStack()

        verifyOrder {
            observeQuery.onChanged(listOf(rootFileModel))
            observeQuery.onChanged(listOf())
        }
    }

    @Test
    fun popFromStackTill() {
        val fileModel = FileModel("/Picture/DCIMM", FileType.FOLDER, "DCIMM", 0.0)
        val observeQuery = mockk<Observer<List<FileModel>>>(relaxed = true)
        fileFragmentManViewModel.stackFiles.observeForever(observeQuery)

        fileFragmentManViewModel.addToStackFiles(fileModel)
        fileFragmentManViewModel.popFromStackTill(rootFileModel)

        verify {
            observeQuery.onChanged(listOf(rootFileModel))
            observeQuery.onChanged(listOf(rootFileModel, fileModel))
            observeQuery.onChanged(listOf(rootFileModel))
        }
    }
}