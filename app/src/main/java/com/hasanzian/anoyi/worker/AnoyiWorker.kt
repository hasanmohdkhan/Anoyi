package com.hasanzian.anoyi.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hasanzian.anoyi.R

class AnoyiWorker(
    private val context: Context,
    private val parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        return try {
            notification("Water Time","It's Water time")
            Result.success();
        }catch (error: Throwable){
            Result.failure()
        }

    }

    private fun notification(title: String, description: String) {
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Anoyi",
                "Anoyi Remainder",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)

            val builder =
                NotificationCompat.Builder(applicationContext, "Anoyi").setContentText(title)
                    .setContentText(description).setSmallIcon(
                    R.drawable.ic_launcher_foreground
                )
            notificationManager.notify(1, builder.build())


        }


    }


}