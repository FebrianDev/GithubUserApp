package com.febrian.githubapp.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var username : String? = null,
    var name : String? = null,
    var avatar : String? = null,
    var company : String? = null,
    var location : String? = null,
    var repository : Int = 0,
    var follower : Int = 0,
    var following : Int = 0,
    ) : Parcelable