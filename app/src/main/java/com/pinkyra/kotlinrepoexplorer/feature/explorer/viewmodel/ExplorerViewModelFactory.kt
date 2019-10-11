package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.model.ExplorerUseCase

@Suppress("UNCHECKED_CAST")
class ExplorerViewModelFactory(private val useCase: ExplorerUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExplorerViewModel(useCase) as T
    }
}