package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkyra.kotlinrepoexplorer.feature.explorer.model.ExplorerRemoteRepository
import kotlinx.coroutines.launch

class ExplorerViewModel(private val remoteRepository: ExplorerRemoteRepository) : ViewModel() {
    private val state: MutableLiveData<ExplorerViewState> = MutableLiveData()
    val viewState: LiveData<ExplorerViewState> = state

    fun interpret(interactorValue: ExplorerInteractor) {
        when (interactorValue) {
            is ExplorerInteractor.FetchData -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            state.value = ExplorerViewState.FetchSucessful(remoteRepository.fetchRepository().items)
        }
    }
}