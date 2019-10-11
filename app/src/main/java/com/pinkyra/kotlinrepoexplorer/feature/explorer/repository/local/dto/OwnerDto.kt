package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dto

import androidx.room.PrimaryKey
import com.pinkyra.kotlinrepoexplorer.model.Owner

data class OwnerDto(@PrimaryKey override val login: String, override val avatarUrl: String) : Owner