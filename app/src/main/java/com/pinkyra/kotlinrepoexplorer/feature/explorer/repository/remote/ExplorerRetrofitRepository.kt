package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote

import com.pinkyra.kotlinrepoexplorer.api.RetrofitServiceProvider
import com.pinkyra.kotlinrepoexplorer.feature.explorer.model.ExplorerRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExplorerRetrofitRepository : ExplorerRemoteRepository {
    private val service = RetrofitServiceProvider.getGithubService()

    override suspend fun fetchRepository() = withContext(Dispatchers.IO) {
        val data = HashMap<String, String>()
        data["q"] = "language:kotlin"
        data["sort"] = "stars"
        data["page"] = "1"

        service.getRepository(data)
    }
}