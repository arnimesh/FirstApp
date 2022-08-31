package com.example.github_assignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_assignment.data.ContributorData
import com.example.github_assignment.retrofit.Repository
import com.example.github_assignment.utils.EspressoIdlingResource
import kotlinx.coroutines.*


class ContributorViewModel(val repository: Repository): ViewModel() {
    private val _contributorList = MutableLiveData<List<ContributorData>>(emptyList())
    val contributorList: LiveData<List<ContributorData>> get() = _contributorList


    suspend fun getContributorFromApi(userID: String, repoName: String) {
        viewModelScope.launch {


            EspressoIdlingResource.increment()
            val contributorResponse =   repository.getContributorData(userID, repoName)
            EspressoIdlingResource.decrement()
            if (contributorResponse.isSuccessful) {
                val contributorData = contributorResponse.body()!!
                _contributorList.postValue(contributorData)
            }


        }
        }

}