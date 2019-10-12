package com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel

sealed class ExplorerInteractor {
    object FetchNextPage : ExplorerInteractor()
    data class ReloadData(val forceDataRenewal: Boolean) : ExplorerInteractor()
}