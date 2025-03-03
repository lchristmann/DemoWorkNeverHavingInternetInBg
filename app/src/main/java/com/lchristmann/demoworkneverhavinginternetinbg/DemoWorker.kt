package com.lchristmann.demoworkneverhavinginternetinbg

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DemoWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val networkAvailable = isNetworkAvailable(applicationContext)
        Log.d("DemoWorker.kt", "Network available: $networkAvailable")

        // Build Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.restful-api.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(DemoApiService::class.java)

        // Execute GET request
        return try {
            val response: Response<JsonObject> = apiService.getObject()
            if (response.isSuccessful && response.body() != null) {
                Log.d("DemoWorker.kt", "GET Response: ${response.body()}")
                Result.success()
            } else {
                Log.e("DemoWorker.kt", "GET request failed with code: ${response.code()}")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e("DemoWorker.kt", "GET request exception: ${e.message}")
            Result.retry()
        }

    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}