package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote

import android.util.Log
import com.pinkyra.kotlinrepoexplorer.api.RetrofitServiceProvider
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.ExplorerRemoteRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.RepositoryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExplorerRetrofitRepository : ExplorerRemoteRepository {

    private companion object {
        private const val TAG: String = "ExplorerRetrofitRepo"
    }

    private val service = RetrofitServiceProvider.getGithubService()

    override suspend fun fetchRepository(query: String?, sort: String?, page: Int) =
        withContext(Dispatchers.IO) {
            val data = HashMap<String, String?>()
            data["q"] = query
            data["sort"] = sort
            data["page"] = page.toString()

            try {
                service.getRepository(data)
            } catch (ex: Exception) {
                Log.e(TAG, "Error while fetching new data", ex)
                RepositoryResult(arrayListOf())
            }
        }
}