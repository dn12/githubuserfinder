package net.adiwilaga.githubuserfinder.api


import net.adiwilaga.githubuserfinder.data.dataobject.BaseResponse
import net.adiwilaga.githubuserfinder.data.dataobject.GHUser
import retrofit2.http.GET
import retrofit2.http.Query


interface API {

    @GET("/search/users")
    suspend fun getUsers(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): BaseResponse<GHUser>



}