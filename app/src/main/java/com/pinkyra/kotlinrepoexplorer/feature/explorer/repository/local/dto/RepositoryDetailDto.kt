package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dto

import androidx.room.*
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail

@Entity(tableName = "repository_detail")
data class RepositoryDetailDto(
    @PrimaryKey
    override val id: Long,
    override val name: String,
    @ColumnInfo(name = "forks_count")
    override val forksCount: Int,
    @ColumnInfo(name = "stargazers_count")
    override val stargazersCount: Int,
    @Embedded
    override val owner: OwnerDto,
    val page: Int,
    val createdTime: Long = System.currentTimeMillis()
) : RepositoryDetail {
    @Ignore
    constructor(repositoryDetail: RepositoryDetail, page: Int) : this(
        0,
        repositoryDetail.name,
        repositoryDetail.forksCount,
        repositoryDetail.stargazersCount,
        OwnerDto(repositoryDetail.owner.login, repositoryDetail.owner.avatarUrl),
        page
    )
}