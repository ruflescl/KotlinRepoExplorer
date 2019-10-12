package com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase

import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dto.RepositoryDetailDto
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.model.ExplorerLocalRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.ExplorerRemoteRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.model.ExplorerUseCase
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KotlinExplorerUseCase(
    remoteRepository: ExplorerRemoteRepository,
    localRepository: ExplorerLocalRepository
) : ExplorerUseCase(remoteRepository, localRepository) {

    init {
        query = "language:kotlin"
        sort = "stars"
    }

    override suspend fun fetchFirstPage(): List<RepositoryDetail> = withContext(Dispatchers.IO) {
        currentPage = 1
        fetchData(query, sort, currentPage)
    }

    override suspend fun fetchNextPage(): List<RepositoryDetail> = withContext(Dispatchers.IO) {
        currentPage += 1
        fetchData(query, sort, currentPage)
    }

    override suspend fun clearAllData() = withContext(Dispatchers.IO) {
        localRepository.deleteAllData()
    }

    override suspend fun fetchData(
        query: String?,
        sort: String?,
        page: Int
    ): List<RepositoryDetail> {
        val localData = localRepository.fetchRepositoryDetails(page)
        val filteredData = filterOldRepositoryDetails(localData, cacheTimeUnit, cacheTimeValue)
        return if (localRepository.shouldFetch(filteredData)) {
            if (filteredData.isNotEmpty()) {
                localRepository.deleteOldRepositoryDetails(filteredData)
            }
            val remoteData = remoteRepository.fetchRepository(query, sort, page)
            localRepository.saveRepositoryDetails(remoteData.items.map { item ->
                RepositoryDetailDto(item, page)
            })
            localRepository.fetchRepositoryDetails(page)
        } else {
            filteredData
        }
    }
}