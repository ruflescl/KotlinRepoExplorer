package com.pinkyra.kotlinrepoexplorer.model

interface RepositoryDetail {
    val name: String
    val owner: Owner
    val stargazersCount: Int
    val forksCount: Int
}