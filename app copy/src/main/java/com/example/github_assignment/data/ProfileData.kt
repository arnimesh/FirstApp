package com.example.github_assignment.data

data class ProfileData(
    val avatar_image: String,
    val bio: Any?,
    val followers: Int,
    val following: Int,
    val login: String,
    val name: String,
    val public_repos: Int
)