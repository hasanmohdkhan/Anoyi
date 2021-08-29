package com.hasanzian.anoyi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.work.*
import com.hasanzian.anoyi.databinding.ActivityMainBinding
import com.hasanzian.anoyi.worker.AnoyiWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val work by lazy {
        WorkManager.getInstance(applicationContext)
    }
    private val constraints = Constraints.NONE
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.oneTime.setOnClickListener {
            oneTimeRequest()
        }

        binding.periodic.setOnClickListener {
           periodicWork()
        }


    }

    private fun periodicWork() {
        val periodicWork =
            PeriodicWorkRequestBuilder<AnoyiWorker>(15, TimeUnit.MINUTES).addTag("AnyoiPeriodic").build()

        work.enqueueUniquePeriodicWork(
            "periodicWork",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )
    }

    private fun oneTimeRequest() {
        val oneTimeWorkRequest =
            OneTimeWorkRequestBuilder<AnoyiWorker>().addTag("oneTimeAnyoi").build()

        work.enqueueUniqueWork(
            "periodicWork",
            ExistingWorkPolicy.KEEP,
            oneTimeWorkRequest
        )
    }
}