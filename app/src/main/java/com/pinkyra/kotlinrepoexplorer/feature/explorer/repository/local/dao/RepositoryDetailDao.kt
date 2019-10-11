package com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dao

import androidx.room.*
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dto.RepositoryDetailDto

@Dao
interface RepositoryDetailDao {
    @Query("SELECT * FROM repository_detail")
    fun getAll(): List<RepositoryDetailDto>

    @Query("SELECT * FROM repository_detail WHERE page = :page")
    fun getByPage(page: Int): List<RepositoryDetailDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repositoryDetail: List<RepositoryDetailDto>)

    @Delete
    fun deleteAll(repositoryDetail: List<RepositoryDetailDto>)
}