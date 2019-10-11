package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.model

import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dto.RepositoryDetailDto
import com.pinkyra.kotlinrepoexplorer.room.AppDatabase

abstract class ExplorerLocalRepository(val database: AppDatabase) {
    abstract fun shouldFetch(repositoryDetails: List<RepositoryDetailDto>): Boolean
    abstract fun fetchRepositoryDetails(page: Int): List<RepositoryDetailDto>
    abstract fun deleteOldRepositoryDetails(repositoryDetails: List<RepositoryDetailDto>)
    abstract fun saveRepositoryDetails(repositoryDetails: List<RepositoryDetailDto>)
}