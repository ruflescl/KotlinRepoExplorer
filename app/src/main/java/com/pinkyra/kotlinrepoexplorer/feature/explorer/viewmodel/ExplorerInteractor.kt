package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

sealed class ExplorerInteractor {
    data class FetchData(val fetch: Boolean) : ExplorerInteractor()
}