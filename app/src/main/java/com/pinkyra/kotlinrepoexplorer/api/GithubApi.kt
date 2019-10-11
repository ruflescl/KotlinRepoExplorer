package com.pinkyra.kotlinrepoexplorer.api

import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model.RepositoryResult
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GithubApi {
    @GET("/search/repositories")
    suspend fun getRepository(@QueryMap(encoded = false) filter: Map<String, String?>): RepositoryResult
}