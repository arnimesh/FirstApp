package com.example.github_assignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.github_assignment.data.ContributorData
import com.example.github_assignment.data.ProfileData
import com.example.github_assignment.data.ReposData
import com.example.github_assignment.retrofit.Repository
import com.example.github_assignment.utils.EspressoIdlingResource
import kotlinx.coroutines.*
import javax.xml.xpath.XPathExpression


class ProfileViewModel(private val repository: Repository): ViewModel() {


    private val _userData = MutableLiveData<ProfileData?>()
    val userData: LiveData<ProfileData?> get() = _userData

    private val _repoList = MutableLiveData<List<ReposData>>()
    val repoList: LiveData<List<ReposData>> get() = _repoList
    private val _userNotFound = MutableLiveData<Boolean?>()
    val userNotFound: LiveData<Boolean?> get() = _userNotFound

//    private val _contributorList = MutableLiveData<List<ContributorData>>(emptyList())
//    val contributorList: LiveData<List<ContributorData>> get() = _contributorList




    fun getDataFromApi(user: String) {


        viewModelScope.launch {
            EspressoIdlingResource.increment()
            val profileResponse= repository.getProfileData(user)

            EspressoIdlingResource.decrement()

            EspressoIdlingResource.increment()

            val repoResponse= repository.getReposData(user)

            EspressoIdlingResource.decrement()

            if (profileResponse== null ) {
                _userNotFound.postValue(true)
            } else {
                _userData.postValue(profileResponse)
                _repoList.postValue(repoResponse.body())
                _userNotFound.postValue(false)
            }


        }

    }
//    fun getContributorFromApi(userID: String, repoName: String) {
//        viewModelScope.launch {
//            EspressoIdlingResource.increment()
//            val contributorResponse =   repository.getContributorData(userID, repoName)
//            EspressoIdlingResource.decrement()
//            if (contributorResponse.isSuccessful) {
//                val contributorData = contributorResponse.body()!!
//                _contributorList.postValue(contributorData)
//            }
//
//
//        }
//    }


}