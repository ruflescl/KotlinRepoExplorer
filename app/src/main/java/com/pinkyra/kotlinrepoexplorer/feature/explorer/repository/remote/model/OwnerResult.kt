package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.model

import com.google.gson.annotations.SerializedName
import com.pinkyra.kotlinrepoexplorer.model.Owner

data class OwnerResult(
    override val login: String,
    @SerializedName("avatar_url")
    override val avatarUrl: String
) : Owner