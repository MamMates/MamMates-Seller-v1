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
import okhttp3.RequestBody
import retrofit2.Response
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

    // Auth
    @POST("auth/login")
    suspend fun authLogin(
        @Body reqLogin: ReqLogin
    ): Response<ResMamMates<String>>

    @POST("auth/register/seller")
    suspend fun authRegister(
        @Body reqRegister: ReqRegister
    ): ResMamMates<String>

    // Order
    @GET("orders/recent")
    suspend fun getOrderRecent(
        @Header("Authorization") token: String
    ): ResMamMates<OrdersRecent>

    @GET("orders/seller")
    suspend fun getOrders(
        @Header("Authorization") token: String,
        @Query("s") status: Int
    ): ResMamMates<Orders>

    @GET("orders/seller")
    suspend fun getOrderDetail(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): ResMamMates<OrderDetail>

    @PUT("orders/seller/{id}")
    suspend fun changeOrderStatus(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body reqStatusChange: ReqStatusChange
    ): ResMamMates<String>

    // Foods
    @GET("foods")
    suspend fun getAllFoods(
        @Header("Authorization") token: String,
    ): ResMamMates<Foods>

    @Multipart
    @POST("foods")
    suspend fun addFood(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("price") price: Int,
        @Part("category") category: Int,
        @Part mamImage: MultipartBody.Part,
        @Part("mam_rates") mamRates: Int,
    ): ResMamMates<String>

    @GET("foods/{id}")
    suspend fun getFoodDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ResMamMates<FoodDetail>

    @Multipart
    @PUT("foods/{id}")
    suspend fun updateFood(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("price") price: Int,
        @Part("category") category: Int,
        @Part mamImage: MultipartBody.Part,
        @Part("mam_rates") mamRates: Int,
    ): ResMamMates<String>

    @DELETE("foods/{id}")
    suspend fun deleteFood(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): ResMamMates<String>

    // Account
    @GET("accounts/store")
    suspend fun getStore(
        @Header("Authorization") token: String,
    ): ResMamMates<Store>

    @GET("accounts/seller")
    suspend fun getAccount(
        @Header("Authorization") token: String,
    ): ResMamMates<AccountSeller>

    @PUT("accounts/seller")
    suspend fun updateAccount(
        @Header("Authorization") token: String,
        @Body reqAccountSeller: ReqAccountSeller
    ): ResMamMates<String>


    @Multipart
    @PATCH("accounts/seller")
    suspend fun updateProfilePicture(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ): ResMamMates<String>

    @PUT("password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body reqPasswordChange: ReqPasswordChange
    ): ResMamMates<String>

    // MamRates
    @Multipart
    @POST("mam_mates")
    suspend fun getMamRates(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ): ResMamMates<MamRates>

    @Multipart
    @POST("reports")
    suspend fun reportMamRates(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("price") price: Int,
        @Part("rating") rating: Int,
        @Part image: MultipartBody.Part,
    ): ResMamMates<String>
}