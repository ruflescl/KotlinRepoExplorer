package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model

import com.pinkyra.kotlinrepoexplorer.model.Repository

interface ExplorerRemoteRepository {
    suspend fun fetchRepository(query: String?, sort: String?, page: Int): Repository
}