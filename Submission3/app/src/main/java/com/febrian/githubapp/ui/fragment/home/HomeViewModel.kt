package com.febrian.githubapp.ui.fragment.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.febrian.githubapp.api.ApiConfig
import com.febrian.githubapp.data.entity.GithubUsers
import com.febrian.githubapp.data.entity.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(context: Context): LiveData<ArrayList<User>> {
        val users = MutableLiveData<ArrayList<User>>()
        val finalUsers: LiveData<ArrayList<User>>
        _isLoading.value = true
        ApiConfig.getService().getUsers().enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    users.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context, "onFailure: ${response.message()}", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                    .show()
            }
        })
        finalUsers = users
        return finalUsers
    }

    fun getSearchUser(username: String, context: Context): LiveData<GithubUsers> {
        val users = MutableLiveData<GithubUsers>()
        val finalUsers: LiveData<GithubUsers>
        _isLoading.value = true
        ApiConfig.getService().getSearchUsers(username)
            .enqueue(object : Callback<GithubUsers> {
                override fun onResponse(
                    call: Call<GithubUsers>,
                    response: Response<GithubUsers>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        users.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<GithubUsers>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                    Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                        .show()
                }
            })

        finalUsers = users
        return finalUsers
    }

    companion object {
        const val TAG = "TAG"
    }
}