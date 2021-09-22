package com.febrian.githubapp.ui.activity.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.febrian.githubapp.api.ApiConfig
import com.febrian.githubapp.data.entity.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private var _users = MutableLiveData<User>()
    private var users: LiveData<User> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUsers(username: String, context: Context): LiveData<User> {
        _isLoading.value = true
        ApiConfig.getService().getDetailUsers(username).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                    .show()
            }
        })

        users = _users
        return users
    }

    companion object {
        const val TAG = "Detail View Model"
    }
}