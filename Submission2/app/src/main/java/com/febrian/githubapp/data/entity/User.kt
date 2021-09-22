package com.febrian.githubapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("login")
    var username: String? = null,
    var name: String? = null,
    @SerializedName("avatar_url")
    var avatar: String? = null,
    var company: String? = null,
    var location: String? = null,
    @SerializedName("public_repos")
    var repository: Int = 0,
    @SerializedName("followers")
    var follower: Int = 0,
    var following: Int = 0,
    @SerializedName("html_url")
    var url: String? = null,
    @SerializedName("followers_url")
    var followerUrl: String? = null,
    @SerializedName("following_url")
    var followingUrl: String? = null
) : Parcelable