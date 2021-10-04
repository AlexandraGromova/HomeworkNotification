package ru.intersvyaz.course.android.retrofit.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getUsers()
    suspend fun getUser(userid : String) = apiService.getUser(userid)

}