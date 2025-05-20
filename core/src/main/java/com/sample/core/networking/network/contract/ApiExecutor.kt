package com.sample.core.networking.network.contract

import com.sample.core.networking.utility.Result
import retrofit2.Response

interface ApiExecutor {
    suspend operator fun <T> invoke(executionBlock: suspend ()->Response<T>) : Result<T>
}