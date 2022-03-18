package com.example.sdm_eco_mileage.repository.loginRegisterFindRepository

import android.util.Log
import com.example.sdm_eco_mileage.data.DataOrException
import com.example.sdm_eco_mileage.model.login.request.LoginRequest
import com.example.sdm_eco_mileage.model.login.response.LoginResponse
import com.example.sdm_eco_mileage.network.LoginRegisterFindAPI
import java.util.concurrent.CancellationException
import javax.inject.Inject

class LoginRegisterFindRepository @Inject constructor(private val api: LoginRegisterFindAPI) {
    suspend fun getLogin(token: String, body: LoginRequest) : DataOrException<LoginResponse, Boolean, Exception> {
        val response = try {
            api.getLogin(token = token, body = body)
        } catch (e: Exception){
            if (e is CancellationException)
                throw e
            Log.d("LoginRegisterFindRepository", "getLogin: api call in repository didn't work")
            Log.d("LoginRegisterFindRepository", "getLogin: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }
}