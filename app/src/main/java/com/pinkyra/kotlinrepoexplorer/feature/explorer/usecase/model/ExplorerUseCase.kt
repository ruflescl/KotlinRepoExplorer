package com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.model

import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dto.RepositoryDetailDto
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.model.ExplorerLocalRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.ExplorerRemoteRepository
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail
import java.util.concurrent.TimeUnit

abstract class ExplorerUseCase(
    protected val remoteRepository: ExplorerRemoteRepository,
    protected val localRepository: ExplorerLocalRepository
) {
    protected var currentPage: Int = 0
    protected var query: String? = null
    protected var sort: String? = null
    protected val cacheTimeUnit = TimeUnit.MINUTES
    protected val cacheTimeValue = 2L

    abstract suspend fun clearAllData()
    abstract suspend fun fetchFirstPage(): List<RepositoryDetail>
    abstract suspend fun fetchNextPage(): List<RepositoryDetail>
    protected abstract suspend fun fetchData(
        query: String?,
        sort: String?,
        page: Int
    ): List<RepositoryDetail>

    fun filterOldRepositoryDetails(
        repositoryDetailDto: List<RepositoryDetailDto>,
        timeUnit: TimeUnit,
        timeValue: Long
    ): List<RepositoryDetailDto> {
        val currentTime: Long = System.currentTimeMillis()
        val desiredCacheTime: Long = currentTime - timeUnit.toMillis(timeValue)

        return repositoryDetailDto.filter { data -> data.createdTime >= desiredCacheTime }
    }
}