package com.example.github_assignment.retrofit

import com.example.github_assignment.data.ContributorData
import com.example.github_assignment.data.ProfileData
import com.example.github_assignment.data.ReposData
import com.example.github_assignment.data.mapper.DataObjectMapper
import retrofit2.Response

class Repo_Impl(private val apiInterface: ProfileApiInterface): Repository {

     val dataObjectMapper=DataObjectMapper()

    override suspend fun getProfileData(user: String): ProfileData? {
        val response=apiInterface.getProfileData(user)

        if(response.code()==404 || response.code()==403|| !response.isSuccessful )
        return null

        return dataObjectMapper.mapUserData( response.body())
    }

    override suspend fun getReposData(user: String): Response<List<ReposData>> {
       return apiInterface.getReposData(user)

    }

    override suspend fun getContributorData(user: String, repo: String): Response<List<ContributorData>>
    = apiInterface.getContributorData(user,repo)
}