package com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.model

import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.ExplorerRemoteRepository
import com.pinkyra.kotlinrepoexplorer.model.Repository

abstract class ExplorerUseCase(private val remoteRepository: ExplorerRemoteRepository) {
    protected var currentPage: Int = 0
    protected var query: String? = null
    protected var sort: String? = null

    abstract suspend fun fetchFirstPage(): Repository
    abstract suspend fun fetchNextPage(): Repository
    protected abstract suspend fun fetchData(query: String?, sort: String?, page: Int): Repository
}