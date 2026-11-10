package com.example.motokeyremapper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Forward the incoming hardware button command to the background service
        val serviceIntent = Intent(this, FeedbackService::class.java).apply {
            action = intent?.action
        }
        startService(serviceIntent)

        // Terminate instantly. No display, no delay, no crashes.
        finish()
    }
}