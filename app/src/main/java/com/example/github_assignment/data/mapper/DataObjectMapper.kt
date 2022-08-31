package com.example.github_assignment.data.mapper

import com.example.github_assignment.data.ProfileData
import com.example.github_assignment.data.RepoDataItem
import com.example.github_assignment.data.ReposData
import com.example.github_assignment.data.UserProfileData
import retrofit2.Response

class DataObjectMapper() {

    fun mapUserData(mapper: UserProfileData?): ProfileData? {
        if (mapper != null) {
            return ProfileData(
                avatar_image = mapper.avatarUrl,
                bio = mapper.bio,
                followers = mapper.followers,
                following = mapper.following,
                login = mapper.login,
                name = mapper.name,
                public_repos = mapper.publicRepos
            )
        }
        return  null
    }
    fun mapReposData(mapper: RepoDataItem): ReposData {
        return ReposData(
            description = mapper.description,
            forksCount = mapper.forksCount,
            owner = mapper.owner,
            name = mapper.name,
            language = mapper.language,
            stargazersCount = mapper.stargazersCount,
            updatedAt = mapper.updatedAt
        )
    }
    fun toRepoList(initial: List<RepoDataItem>): List<ReposData>{
        return initial.map { mapReposData(it) }
    }


}


