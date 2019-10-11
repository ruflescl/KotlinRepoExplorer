package com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase

import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.ExplorerRemoteRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.model.ExplorerUseCase
import com.pinkyra.kotlinrepoexplorer.model.Repository

class KotlinExplorerUseCase(private val remoteRepository: ExplorerRemoteRepository) :
    ExplorerUseCase(remoteRepository) {

    init {
        query = "language:kotlin"
        sort = "stars"
    }

    override suspend fun fetchFirstPage(): Repository {
        currentPage = 1
        return fetchData(query, sort, currentPage)
    }

    override suspend fun fetchNextPage(): Repository {
        currentPage += 1
        return fetchData(query, sort, currentPage)
    }

    override suspend fun fetchData(query: String?, sort: String?, page: Int): Repository =
        remoteRepository.fetchRepository(query, sort, page)
}