package com.sdm.ecomileage.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.sdm.ecomileage.UserInformation
import java.io.InputStream
import java.io.OutputStream

object UserInformationSerializer : Serializer<UserInformation> {
    override val defaultValue: UserInformation
        get() = UserInformation.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserInformation {
        try {
            return UserInformation.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserInformation, output: OutputStream) = t.writeTo(output)
}