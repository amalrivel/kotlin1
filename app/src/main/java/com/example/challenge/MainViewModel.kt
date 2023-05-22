package com.example.challenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge.api.ApiConfig
import com.example.challenge.model.DetailUserResponse
import com.example.challenge.model.FollowResponseItem
import com.example.challenge.model.ItemsItem
import com.example.challenge.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _searchList = MutableLiveData<ArrayList<ItemsItem>>()
    val searchList: LiveData<ArrayList<ItemsItem>> = _searchList

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _follower = MutableLiveData<ArrayList<FollowResponseItem>>()
    val follower: LiveData<ArrayList<FollowResponseItem>> = _follower

    private val _following = MutableLiveData<ArrayList<FollowResponseItem>>()
    val following: LiveData<ArrayList<FollowResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun searchUsers(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getSearch(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()?.items
                    if (responseBody != null) {
                        _searchList.value = responseBody as ArrayList<ItemsItem>?
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun detailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().detailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun follower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().follower(username)
        client.enqueue(object : Callback<ArrayList<FollowResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<FollowResponseItem>>,
                response: Response<ArrayList<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _follower.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun following(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().following(username)
        client.enqueue(object : Callback<ArrayList<FollowResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<FollowResponseItem>>,
                response: Response<ArrayList<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _following.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })

    }
}
