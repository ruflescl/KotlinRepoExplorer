package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.OwnerResult
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.RepositoryDetailResult
import com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.model.ExplorerUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExplorerViewModelTest {

    private companion object {
        val REPO_DETAIL_FIRST_DATA =
            RepositoryDetailResult("first", OwnerResult("first", "first"), 1, 1)
        val REPO_DETAIL_SECOND_DATA =
            RepositoryDetailResult("second", OwnerResult("second", "second"), 2, 2)

        val LIST_REPO_DETAIL_WITH_FIRST_DATA = arrayListOf(REPO_DETAIL_FIRST_DATA)
        val LIST_REPO_DETAIL_WITH_SECOND_DATA = arrayListOf(REPO_DETAIL_SECOND_DATA)
        val LIST_REPO_DETAIL_WITH_BOTH_DATA =
            arrayListOf(REPO_DETAIL_FIRST_DATA, REPO_DETAIL_SECOND_DATA)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ExplorerViewModel
    @MockK
    private lateinit var mockUseCase: ExplorerUseCase

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onInit_fetchesFirstPage() = runBlocking {
        mockFetchFirstPage_returnsListWithFirstData()

        viewModel = ExplorerViewModel(mockUseCase)

        Assert.assertEquals(viewModel.viewState.value, LIST_REPO_DETAIL_WITH_FIRST_DATA)
        coVerifyUseCase(fetchFirstPageCount = 1)
    }

    @Test
    fun fetchNextPage_sendFullList() = runBlocking {
        mockFetchFirstPage_returnsListWithFirstData()
        mockFetchNextPage_returnsListWithSecondData()

        viewModel = ExplorerViewModel(mockUseCase)
        viewModel.interpret(ExplorerInteractor.FetchNextPage)

        Assert.assertEquals(viewModel.viewState.value, LIST_REPO_DETAIL_WITH_BOTH_DATA)
        coVerifyUseCase(fetchFirstPageCount = 1, fetchNextPageCount = 1)
    }

    @Test
    fun reloadData_noForceRenewal_sendFirstListTwice() = runBlocking {
        mockFetchFirstPage_returnsListWithFirstData()

        viewModel = ExplorerViewModel(mockUseCase)
        viewModel.interpret(ExplorerInteractor.ReloadData(false))

        Assert.assertEquals(viewModel.viewState.value, LIST_REPO_DETAIL_WITH_FIRST_DATA)
        coVerifyUseCase(fetchFirstPageCount = 2)
    }

    @Test
    fun reloadData_withForceRenewal_sendFirstListTwice_andClearData() = runBlocking {
        mockFetchFirstPage_returnsListWithFirstData()
        mockClearAllData_returnsUnit()

        viewModel = ExplorerViewModel(mockUseCase)
        viewModel.interpret(ExplorerInteractor.ReloadData(true))

        Assert.assertEquals(viewModel.viewState.value, LIST_REPO_DETAIL_WITH_FIRST_DATA)
        coVerifyUseCase(fetchFirstPageCount = 2, clearAllDataCount = 1)
    }

    private fun coVerifyUseCase(
        fetchFirstPageCount: Int = 0,
        fetchNextPageCount: Int = 0,
        clearAllDataCount: Int = 0,
        filterOldRepositoryDetailsCount: Int = 0
    ) {
        coVerify(exactly = fetchFirstPageCount) {
            mockUseCase.fetchFirstPage()
        }
        coVerify(exactly = fetchNextPageCount) {
            mockUseCase.fetchNextPage()
        }
        coVerify(exactly = clearAllDataCount) {
            mockUseCase.clearAllData()
        }
        coVerify(exactly = filterOldRepositoryDetailsCount) {
            mockUseCase.filterOldRepositoryDetails(any(), any(), any())
        }
    }

    private fun mockFetchFirstPage_returnsListWithFirstData() {
        coEvery { mockUseCase.fetchFirstPage() } returns LIST_REPO_DETAIL_WITH_FIRST_DATA
    }

    private fun mockFetchNextPage_returnsListWithSecondData() {
        coEvery { mockUseCase.fetchNextPage() } returns LIST_REPO_DETAIL_WITH_SECOND_DATA
    }

    private fun mockClearAllData_returnsUnit() {
        coEvery { mockUseCase.clearAllData() } returns Unit
    }
}