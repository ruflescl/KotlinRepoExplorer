package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail

sealed class ExplorerViewState {
    data class FetchSucessful(val repositoryDetail: List<RepositoryDetail>) : ExplorerViewState()
}