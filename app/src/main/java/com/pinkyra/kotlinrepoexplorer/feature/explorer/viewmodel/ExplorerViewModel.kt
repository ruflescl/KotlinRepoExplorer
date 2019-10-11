package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.model.ExplorerUseCase
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail
import kotlinx.coroutines.launch

class ExplorerViewModel(private val useCase: ExplorerUseCase) : ViewModel() {
    private val state: MutableLiveData<List<RepositoryDetail>> = MutableLiveData()
    val viewState: LiveData<List<RepositoryDetail>> = state

    private val repositoryDetailList: ArrayList<RepositoryDetail> = arrayListOf()

    init {
        fetchFirstPage()
    }

    fun interpret(interactorValue: ExplorerInteractor) {
        when (interactorValue) {
            is ExplorerInteractor.FetchNextPage -> fetchNextPage()
            is ExplorerInteractor.ReloadData -> reloadData()
        }
    }

    private fun reloadData() {
        viewModelScope.launch {
            repositoryDetailList.clear()
            state.value = repositoryDetailList
            fetchFirstPage()
        }
    }

    private fun fetchNextPage() {
        viewModelScope.launch {
            repositoryDetailList.addAll(useCase.fetchNextPage().items)
            state.value = repositoryDetailList
        }
    }

    private fun fetchFirstPage() {
        viewModelScope.launch {
            repositoryDetailList.addAll(useCase.fetchFirstPage().items)
            state.value = repositoryDetailList
        }
    }
}