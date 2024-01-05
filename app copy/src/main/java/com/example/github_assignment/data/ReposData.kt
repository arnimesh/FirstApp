package com.example.github_assignment.data

import com.google.gson.annotations.SerializedName

data class ReposData(
    @SerializedName("description")
    val description: String,
    @SerializedName("forks_count")
    val forksCount: Int?,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String,


)