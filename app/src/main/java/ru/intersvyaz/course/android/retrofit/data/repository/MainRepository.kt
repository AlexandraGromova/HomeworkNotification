package ru.intersvyaz.course.android.retrofit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.intersvyaz.course.android.retrofit.data.api.ApiHelper
import ru.intersvyaz.course.android.retrofit.data.api.ApiService
import ru.intersvyaz.course.android.retrofit.data.api.RetrofitBuilder
import ru.intersvyaz.course.android.retrofit.data.database.AppDatabase
import ru.intersvyaz.course.android.retrofit.data.model.User

class MainRepository(
    private val apiHelper: ApiHelper,
    appDatabase: AppDatabase?
) {
    private val userDao = appDatabase?.userDao()

    suspend fun getUsers(): List<User>? = withContext(Dispatchers.IO) {
        val apiResult = apiHelper.getUsers()
        userDao?.insertAllUsers(apiResult)

        userDao?.getAllUsers()
    }


    //    suspend fun getUser(id: String): User? = withContext(Dispatchers.IO) { userDao?.getUser(id) }
    suspend fun getUser(id: String): User? = withContext(Dispatchers.IO) {
        apiHelper.getUser(id)
    }
}