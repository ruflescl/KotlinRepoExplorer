package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pinkyra.kotlinrepoexplorer.feature.explorer.model.ExplorerRemoteRepository

@Suppress("UNCHECKED_CAST")
class ExplorerViewModelFactory(private val remoteRepository: ExplorerRemoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExplorerViewModel(remoteRepository) as T
    }
}