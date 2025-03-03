package com.lchristmann.demoworkneverhavinginternetinbg

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lchristmann.demoworkneverhavinginternetinbg.ui.theme.DemoWorkNeverHavingInternetInBgTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoWorkNeverHavingInternetInBgTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Button(onClick = {
            Log.d("MainActivity.kt", "Work scheduled with 15s delay")
                val oneTimeRequest = OneTimeWorkRequestBuilder<DemoWorker>()
                    .setInitialDelay(15, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(context).enqueue(oneTimeRequest)
        }) { Text(text = "Schedule work with 15s delay") }

    }
}