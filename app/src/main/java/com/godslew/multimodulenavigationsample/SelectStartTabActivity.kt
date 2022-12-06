package com.godslew.multimodulenavigationsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.godslew.multimodulenavigationsample.databinding.ActivitySelectTabBinding

class SelectStartTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonHome.setOnClickListener {
                openMain(StartTab.Home)
            }
            buttonHomeWithWeb.setOnClickListener {
                openMain(StartTab.HomeWithWeb)
            }
            buttonBoard.setOnClickListener {
                openMain(StartTab.Dashboard)
            }
            buttonNotifications.setOnClickListener {
                openMain(StartTab.Notifications)
            }
        }
    }

    private fun openMain(tab: StartTab) {
        startActivity(MainActivity.createIntent(this, tab))
    }
}