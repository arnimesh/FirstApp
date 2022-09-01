package com.example.github_assignment.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.github_assignment.MainCoroutineRule
import com.example.github_assignment.data.Owner
import com.example.github_assignment.data.ProfileData
import com.example.github_assignment.data.ReposData
import com.example.github_assignment.retrofit.Repository
import io.mockk.MockKStubScope

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response


@RunWith(JUnit4::class)
class ProfileViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()
    private lateinit var validUserName: String
    private lateinit var invalidUserName: String
    private lateinit var viewModel: ProfileViewModel
    private var mockRepository = mockk<Repository>()


    @Before
    fun setup() {
        validUserName = "vipinhelloindia"
        invalidUserName="invalidusername21323"

        val profileResp: ProfileData?= ProfileData(
            login = validUserName,
            avatar_image = "/",
            followers = 100,
            following = 100,
            bio = "Fake bio",
            name = "Fake name",
            public_repos = 100
        )



        val repoResponse: Response<List<ReposData>> =
            Response.success(
                listOf(
                    ReposData(
                        name = "Repo_1",
                        description = "Fake repo",
                        language = "Kotlin",
                        forksCount = 0,
                        stargazersCount = 0,
                        updatedAt = "today",
                        owner = Owner(validUserName)
                    )
                    ,ReposData(
                        name = "Repo_2",
                        description = "Fake repo 2",
                        language = "java",
                        forksCount = 0,
                        stargazersCount = 0,
                        updatedAt = "",
                        owner = Owner(validUserName)
                    ))
            )

        coEvery {
            mockRepository.getProfileData(validUserName)

        }  returns profileResp

        coEvery {
            mockRepository.getReposData(validUserName)
        } returns repoResponse

        coEvery {
            mockRepository.getProfileData(invalidUserName)
        } returns null

        coEvery {
            mockRepository.getReposData(invalidUserName)
        } returns repoResponse

        viewModel = ProfileViewModel(mockRepository)

    }


    @Test
    fun `check if validUserName received is same as entered ` (){
        runBlocking {
            viewModel.getDataFromApi(validUserName)
        }
        val userReceived = viewModel.userData.value?.login
        assertEquals(validUserName, userReceived)
    }

    @Test
    fun `get repo list`() {
        runBlocking {
            viewModel.getDataFromApi(validUserName)
        }
        val repoList = viewModel.repoList.value

        if (repoList != null) {
            assertTrue(repoList.size == 2)
        }
        }
    @Test
    fun `check invalid user `() {
        runBlocking {
            viewModel.getDataFromApi(invalidUserName)
        }
        val userNotFound=viewModel.userNotFound.value
        assertEquals(true,userNotFound)

    }


}

