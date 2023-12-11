package com.mammates.mammates_seller_v1.data.source.remote

import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqAccountSeller
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqLogin
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqPasswordChange
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqRegister
import com.mammates.mammates_seller_v1.data.source.remote.dto.ReqStatusChange
import com.mammates.mammates_seller_v1.data.source.remote.dto.ResMamMates
import com.mammates.mammates_seller_v1.domain.model.AccountSeller
import com.mammates.mammates_seller_v1.domain.model.FoodDetail
import com.mammates.mammates_seller_v1.domain.model.Foods
import com.mammates.mammates_seller_v1.domain.model.MamRates
import com.mammates.mammates_seller_v1.domain.model.OrderDetail
import com.mammates.mammates_seller_v1.domain.model.Orders
import com.mammates.mammates_seller_v1.domain.model.OrdersRecent
import com.mammates.mammates_seller_v1.domain.model.Store
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MamMatesApi {

    @POST("auth/login")
    fun authLogin(
        @Body reqLogin: ReqLogin
    ): Call<ResMamMates<String>>

    @POST("auth/register/seller")
    suspend fun authRegister(
        @Body reqRegister: ReqRegister
    ): ResMamMates<String>

    @GET("orders/recent")
    suspend fun getOrderRecent(
        @Header("Authentication") token: String
    ): ResMamMates<OrdersRecent>

    @GET("orders")
    suspend fun getOrders(
        @Header("Authentication") token: String,
        @Query("status") status: Int
    ): ResMamMates<Orders>

    @GET("orders/{id}")
    suspend fun getOrderDetail(
        @Header("Authentication") token: String,
        @Path("id") id: Int
    ): ResMamMates<OrderDetail>

    @PUT("orders/{id}")
    suspend fun changeOrderStatus(
        @Header("Authentication") token: String,
        @Path("id") id: Int,
        @Body reqStatusChange: ReqStatusChange
    ): ResMamMates<String>

    @GET("foods")
    suspend fun getAllFoods(
        @Header("Authentication") token: String,
    ): ResMamMates<Foods>

    @Multipart
    @POST("foods")
    suspend fun addFood(
        @Header("Authentication") token: String,
        @Part("image") image: MultipartBody.Part,
        @Part("name") name: String,
        @Part("price") price: Int,
        @Part("category") category: String,
        @Part("mam_image") mamImage: MultipartBody.Part,
        @Part("mam_rates") mamRates: Int,
    ): ResMamMates<String>

    @GET("foods/{id}")
    suspend fun getFoodDetails(
        @Header("Authentication") token: String,
        @Path("id") id: Int
    ): ResMamMates<FoodDetail>

    @Multipart
    @PUT("foods/{id}")
    suspend fun updateFood(
        @Header("Authentication") token: String,
        @Path("id") id: Int,
        @Part("image") image: MultipartBody.Part,
        @Part("name") name: String,
        @Part("price") price: Int,
        @Part("category") category: String,
        @Part("mam_image") mamImage: MultipartBody.Part,
        @Part("mam_rates") mamRates: Int,
    ): ResMamMates<String>

    @DELETE("foods/{id}")
    suspend fun deleteFood(
        @Header("Authentication") token: String,
        @Path("id") id: Int,
    ): ResMamMates<String>

    @GET("accounts/store")
    suspend fun getStore(
        @Header("Authentication") token: String,
    ): ResMamMates<Store>

    @GET("accounts/seller")
    suspend fun getAccount(
        @Header("Authentication") token: String,
    ): ResMamMates<AccountSeller>

    @PUT("accounts/seller")
    suspend fun updateAccount(
        @Header("Authentication") token: String,
        @Body reqAccountSeller: ReqAccountSeller
    ): ResMamMates<String>


    @Multipart
    @PATCH("accounts/seller")
    suspend fun updateProfilePicture(
        @Header("Authentication") token: String,
        @Part("image") image: MultipartBody.Part,
    ): ResMamMates<String>

    @PUT("password")
    suspend fun changePassword(
        @Header("Authentication") token: String,
        @Body reqPasswordChange: ReqPasswordChange
    ): ResMamMates<String>

    @POST("mam_mates")
    suspend fun getMamRates(
        @Header("Authentication") token: String,
        @Part("image") image: MultipartBody.Part
    ): ResMamMates<MamRates>

    @Multipart
    @POST("reports")
    suspend fun reportMamRates(
        @Header("Authentication") token: String,
        @Part("name") name: String,
        @Part("price") price: Int,
        @Part("rating") rating: Int,
        @Part("image") image: MultipartBody.Part,
    ): ResMamMates<String>
}