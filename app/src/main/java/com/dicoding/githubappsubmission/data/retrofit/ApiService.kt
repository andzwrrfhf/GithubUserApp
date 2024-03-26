package com.dicoding.githubappsubmission.data.retrofit

import com.dicoding.githubappsubmission.data.response.DetailUserResponse
import com.dicoding.githubappsubmission.data.response.ItemsItem
import com.dicoding.githubappsubmission.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token <Personal Token>")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token <Personal Token>")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token <Personal Token>")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token <Personal Token>")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}
