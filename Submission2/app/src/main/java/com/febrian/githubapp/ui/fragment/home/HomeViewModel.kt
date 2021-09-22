package com.febrian.githubapp.ui.fragment.home

import android.util.Log
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

    companion object {
        const val TAG = "TAG"
    }

    fun getUser(): LiveData<ArrayList<User>> {
        val _users = MutableLiveData<ArrayList<User>>()
        val users: LiveData<ArrayList<User>>
        _isLoading.value = true
        Log.d("Kosong", "Kosong")
        ApiConfig.getService().getUsers().enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        users = _users
        return users
    }

    fun getSearchUser(username: String): LiveData<GithubUsers> {
        val _users = MutableLiveData<GithubUsers>()
        val users: LiveData<GithubUsers>
        _isLoading.value = true
        ApiConfig.getService().getSearchUsers(username)
            .enqueue(object : Callback<GithubUsers> {
                override fun onResponse(
                    call: Call<GithubUsers>,
                    response: Response<GithubUsers>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _users.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<GithubUsers>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })

        users = _users
        return users
    }
}