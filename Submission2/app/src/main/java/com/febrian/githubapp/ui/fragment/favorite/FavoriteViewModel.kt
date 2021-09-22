package com.febrian.githubapp.ui.fragment.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.febrian.githubapp.data.database.DataRoom
import com.febrian.githubapp.data.database.DatabaseRoom

class FavoriteViewModel : ViewModel() {
    private var _users = MutableLiveData<ArrayList<DataRoom>>()
    private var users: LiveData<ArrayList<DataRoom>> = _users

    fun getData(context: Context): LiveData<ArrayList<DataRoom>> {
        val database = DatabaseRoom.getDatabase(context.applicationContext).dataDao()
        _users.value = database.getAllData() as ArrayList<DataRoom>
        users = _users

        return users
    }
}