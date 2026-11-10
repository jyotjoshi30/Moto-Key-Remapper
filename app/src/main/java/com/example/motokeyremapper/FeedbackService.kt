package com.example.motokeyremapper

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator

class FeedbackService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        var delayBeforeStop: Long = 0

        when (intent?.action) {
            "com.example.motokeyremapper.ACTION_VIBRATE" -> {
                // 1. Trigger vibration FIRST using backwards-compatible methods
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                if (vibrator.hasVibrator()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val effect = VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE)

                        // Tag it as a Notification so the OS doesn't block it in the background
                        val attributes = AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                            .build()

                        vibrator.vibrate(effect, attributes)
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(150)
                    }
                }

                // 2. Switch ringer mode SECOND
                audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE

                delayBeforeStop = 250 // Give the motor time to spin
            }
            "com.example.motokeyremapper.ACTION_RING" -> {
                audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL

                val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val ringtone = RingtoneManager.getRingtone(applicationContext, notificationUri)
                ringtone?.play()

                delayBeforeStop = 2000 // Give the audio time to play
            }
        }

        // Stop the background service after the action finishes
        if (delayBeforeStop > 0L) {
            Handler(Looper.getMainLooper()).postDelayed({
                stopSelf()
            }, delayBeforeStop)
        } else {
            stopSelf()
        }

        return START_NOT_STICKY
    }
}