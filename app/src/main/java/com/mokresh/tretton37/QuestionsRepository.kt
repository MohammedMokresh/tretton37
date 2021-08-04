package com.mokresh.tretton37

import com.mokresh.tretton37.api.ApiServices
import com.mokresh.tretton37.model.QuestionsResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class QuestionsRepository(
    private val service: ApiServices,
) {
    suspend fun getQuestions(): Flow<Response<QuestionsResponseBody>> {
        return flow {
            emit(service.getQuestions(amount = 11, "multiple"))
        }.flowOn(Dispatchers.IO)
    }

}