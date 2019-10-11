package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model

import com.google.gson.annotations.SerializedName
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail

data class RepositoryDetailResult(
    override val name: String,
    override val owner: OwnerResult,
    @SerializedName("forks_count")
    override val forksCount: Int,
    @SerializedName("stargazers_count")
    override val stargazersCount: Int
) : RepositoryDetail