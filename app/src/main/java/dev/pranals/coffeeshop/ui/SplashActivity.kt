package dev.pranals.coffeeshop.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dev.pranals.coffeeshop.databinding.ActivitySplashBinding
import dev.pranals.coffeeshop.ui.dashboard.DashboardActivity
import dev.pranals.coffeeshop.ui.itemdetails.DetailsActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, DetailsActivity::class.java))
        }

    }
}