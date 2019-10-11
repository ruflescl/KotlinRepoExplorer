package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

sealed class ExplorerInteractor {
    data class FetchNextPage(val fetch: Boolean) : ExplorerInteractor()
    data class ReloadData(val reload: Boolean) : ExplorerInteractor()
}