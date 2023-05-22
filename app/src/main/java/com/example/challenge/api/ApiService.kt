package com.example.challenge.api

import com.example.challenge.model.DetailUserResponse
import com.example.challenge.model.FollowResponseItem
import com.example.challenge.model.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearch(
        @Query("q") username: String,
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun follower(
        @Path("username") username: String
    ): Call<ArrayList<FollowResponseItem>>

    @GET("users/{username}/following")
    fun following(
        @Path("username") username: String
    ): Call<ArrayList<FollowResponseItem>>
}
