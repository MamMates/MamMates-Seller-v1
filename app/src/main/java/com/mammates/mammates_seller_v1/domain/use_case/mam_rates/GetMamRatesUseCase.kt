package com.mammates.mammates_seller_v1.domain.use_case.mam_rates

import com.mammates.mammates_seller_v1.common.Resource
import com.mammates.mammates_seller_v1.domain.model.MamRatesItem
import com.mammates.mammates_seller_v1.domain.repository.MamRatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMamRatesUseCase @Inject constructor(
    private val mamRatesRepository: MamRatesRepository
) {
    operator fun invoke(
        token: String,
        image: MultipartBody.Part
    ): Flow<Resource<MamRatesItem>> = flow {
        try {
            emit(Resource.Loading())
            val mamRates = mamRatesRepository.getMamRates(token, image).data?.mamMates
            mamRates?.let {
                emit(Resource.Success(it))
            }
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()
            errorMessage?.let {
                val jsonObject = JSONObject(it.charStream().readText())
                emit(
                    Resource.Error(
                        jsonObject.getString("message") ?: "An unexpected error occured"
                    )
                )
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}