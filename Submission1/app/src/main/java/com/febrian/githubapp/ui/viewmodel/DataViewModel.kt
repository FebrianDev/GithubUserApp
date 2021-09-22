package com.febrian.githubapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.febrian.githubapp.data.entity.GithubUsers
import com.febrian.githubapp.utils.DataDummy

class DataViewModel(private var context : Context) : ViewModel() {

    fun getData(): LiveData<GithubUsers> {
        val data: MutableLiveData<GithubUsers> = MutableLiveData<GithubUsers>()
        data.postValue(DataDummy(context).getUser())
        return data
    }
}
