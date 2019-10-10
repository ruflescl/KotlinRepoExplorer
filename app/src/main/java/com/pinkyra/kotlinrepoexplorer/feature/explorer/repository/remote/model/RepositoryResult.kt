package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model

import com.pinkyra.kotlinrepoexplorer.model.Repository

data class RepositoryResult(
    override val items: List<RepositoryDetailResult>
) : Repository