package com.sdm.ecomileage.repository.loginRegisterFindRepository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.sdm.ecomileage.SdmEcoMileageApplication
import com.sdm.ecomileage.UserInformation
import com.sdm.ecomileage.data.DataOrException
import com.sdm.ecomileage.data.UserInformationSerializer
import com.sdm.ecomileage.model.login.request.LoginRequest
import com.sdm.ecomileage.model.login.response.LoginResponse
import com.sdm.ecomileage.model.memberUpdate.request.MemberUpdateRequest
import com.sdm.ecomileage.model.memberUpdate.response.MemberUpdateResponse
import com.sdm.ecomileage.model.register.request.RegisterRequest
import com.sdm.ecomileage.model.register.response.RegisterResponse
import com.sdm.ecomileage.network.LoginRegisterFindAPI
import com.sdm.ecomileage.utils.Constants.DATA_STORE_FILE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import okio.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class LoginRegisterFindRepository @Inject constructor(private val api: LoginRegisterFindAPI) {

    val context = SdmEcoMileageApplication.ApplicationContext()
    val Context.userStore: DataStore<UserInformation> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = UserInformationSerializer
    )

    val readProto: Flow<UserInformation> = context.userStore.data.catch { exception ->
        if (exception is IOException) {
            Log.d("LoginRepository", "protoDataStore: ${exception.message.toString()}")
            emit(UserInformation.getDefaultInstance())
        } else {
            throw exception
        }
    }

    suspend fun updateValue(uuid: String?, id: String?, password: String?) {
        if (uuid != null) {
            context.userStore.updateData { preference ->
                preference.toBuilder().setUuid(uuid).build()
            }
            Log.d("Login", "updateValue: UUID come?")
        }
        if (id != null) {
            context.userStore.updateData { preference ->
                preference.toBuilder().setLastId(id).build()
            }
            Log.d("Login", "updateValue: Id come?")

        }
        if (password != null) {
            context.userStore.updateData { preference ->
                preference.toBuilder().setLastPassword(password).build()
            }
            Log.d("Login", "updateValue: Password come?")
        }
    }


    suspend fun getLogin(
        body: LoginRequest
    ): DataOrException<LoginResponse, Boolean, Exception> {
        val response = try {
            api.getLogin(body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.d("LoginRegisterFindRepository", "getLogin: api call in repository didn't work")
            Log.d("LoginRegisterFindRepository", "getLogin: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }

    suspend fun postRegister(
        body: RegisterRequest
    ): DataOrException<RegisterResponse, Boolean, Exception> {
        val response = try {
            api.postRegister(body = body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d("LoginRegisterFindRepository", "postRegister: api call in repository didn't work")
            Log.d("LoginRegisterFindRepository", "postRegister: exception is $e")

            return DataOrException(e = e)
        }

        return DataOrException(response)
    }

    suspend fun putMemberUpdate(
        token: String,
        body: MemberUpdateRequest
    ): DataOrException<MemberUpdateResponse, Boolean, Exception> {
        val response = try {
            api.putMemberUpdate(token, body)
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e

            Log.d(
                "LoginRegisterFindRepository",
                "putMemberUpdate: api call in repository didn't work"
            )
            Log.d("LoginRegisterFindRepository", "putMemberUpdate: exception is $e")

            return DataOrException(e = e)
        }
        return DataOrException(response)
    }



}