package com.example.github_assignment.retrofit

import com.example.github_assignment.data.ContributorData
import com.example.github_assignment.data.ProfileData
import com.example.github_assignment.data.ReposData
import com.example.github_assignment.data.UserProfileData
import retrofit2.Response

interface Repository {
    suspend fun getProfileData(user: String): ProfileData?
    suspend fun getReposData(user: String):  Response<List<ReposData>>
    suspend fun getContributorData(user: String, repo: String): Response<List<ContributorData>>
}