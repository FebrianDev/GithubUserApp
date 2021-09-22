package com.febrian.githubapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Insert
    fun insert(user: DataRoom)

    @Query("DELETE from data where username = :username")
    fun delete(username: String)

    @Query("SELECT * from data order by id asc")
    fun getAllData(): List<DataRoom>

    @Query("SELECT * from data where id = :myId ")
    fun getDataById(myId: Int): DataRoom

    @Query("SELECT * from data where username = :username")
    fun getDataByTitle(username: String): DataRoom

    @Query("SELECT EXISTS(SELECT * from data where username = :username)")
    fun dataExist(username: String): Boolean

    @Query("SELECT EXISTS(SELECT * from data where id = :id)")
    fun dataExist(id: Int): Boolean
}