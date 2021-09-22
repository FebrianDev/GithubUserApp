package com.febrian.githubapp.utils

import android.content.Context
import com.febrian.githubapp.data.entity.GithubUsers
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class DataDummy(private var context: Context) {

    fun getUser(): GithubUsers {
        val jsonFileString = getJsonDataFromAsset("githubuser.json")
        val gson = Gson()
        val listUser = object : TypeToken<GithubUsers>() {}.type

        return gson.fromJson(jsonFileString, listUser)
    }

    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}