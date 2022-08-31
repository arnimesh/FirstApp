package com.example.github_assignment.retrofit

import com.example.github_assignment.data.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiInterface{

    @GET("users/{user}")
    suspend fun getProfileData(@Path("user") userID:String): Response<UserProfileData?>

    @GET("users/{user}/repos")
    suspend fun getReposData(@Path("user") userID: String): Response<List<ReposData>>

    @GET("repos/{user}/{repo}/contributors")
    suspend fun getContributorData(@Path("user") userID: String, @Path("repo") repoName: String):
            Response<List<ContributorData>>
}