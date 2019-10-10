package com.pinkyra.kotlinrepoexplorer.feature.explorer.model

import com.pinkyra.kotlinrepoexplorer.model.Repository

interface ExplorerRemoteRepository {
    suspend fun fetchRepository(): Repository
}