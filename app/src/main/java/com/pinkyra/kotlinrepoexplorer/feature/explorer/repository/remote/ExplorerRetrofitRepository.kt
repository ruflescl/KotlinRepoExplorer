package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote

import com.pinkyra.kotlinrepoexplorer.api.RetrofitServiceProvider
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.ExplorerRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExplorerRetrofitRepository :
    ExplorerRemoteRepository {
    private val service = RetrofitServiceProvider.getGithubService()

    override suspend fun fetchRepository(query: String?, sort: String?, page: Int) =
        withContext(Dispatchers.IO) {
            val data = HashMap<String, String?>()
            data["q"] = query
            data["sort"] = sort
            data["page"] = page.toString()

            service.getRepository(data)
        }
}