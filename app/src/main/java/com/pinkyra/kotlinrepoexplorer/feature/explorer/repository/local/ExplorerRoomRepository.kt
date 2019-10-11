package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local

import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dto.RepositoryDetailDto
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.model.ExplorerLocalRepository
import com.pinkyra.kotlinrepoexplorer.room.AppDatabase

class ExplorerRoomRepository(database: AppDatabase) : ExplorerLocalRepository(database) {
    private val repositoryDetailDao = database.repositoryDetailDao()

    override fun shouldFetch(repositoryDetails: List<RepositoryDetailDto>): Boolean =
        repositoryDetails.isEmpty()

    override fun fetchRepositoryDetails(page: Int): List<RepositoryDetailDto> =
        repositoryDetailDao.getByPage(page)

    override fun deleteOldRepositoryDetails(repositoryDetails: List<RepositoryDetailDto>) =
        repositoryDetailDao.deleteAll(repositoryDetails)

    override fun saveRepositoryDetails(repositoryDetails: List<RepositoryDetailDto>) =
        repositoryDetailDao.insertAll(repositoryDetails)
}