package com.febrian.githubapp.api

import com.febrian.githubapp.data.entity.GithubUsers
import com.febrian.githubapp.data.entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    @Headers("Authorization: token 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")
    fun getUsers(): Call<ArrayList<User>>

    @GET("/search/users")
    fun getSearchUsers(
        @Query("q") q: String
    ): Call<GithubUsers>

    @GET("/users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<User>

    @GET("/users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("/users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}