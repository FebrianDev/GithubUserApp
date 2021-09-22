package com.febrian.githubapp.ui.fragment.follower

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.febrian.githubapp.api.ApiConfig
import com.febrian.githubapp.data.entity.User
import com.febrian.githubapp.ui.fragment.home.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private var _users = MutableLiveData<ArrayList<User>>()
    private var users: LiveData<ArrayList<User>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String, context: Context): LiveData<ArrayList<User>> {
        _isLoading.value = true
        ApiConfig.getService().getFollowers(username).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    Log.e(HomeViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(HomeViewModel.TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                    .show()
            }

        })

        users = _users
        return users
    }
}