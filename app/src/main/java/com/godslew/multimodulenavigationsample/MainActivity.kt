package com.godslew.multimodulenavigationsample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.godslew.home.HomeFragmentArgs
import com.godslew.multimodulenavigationsample.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val args: MainActivityArgs by navArgs()

    companion object {
        fun createIntent(context: Context, tab: StartTab): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtras(MainActivityArgs(tab).toBundle())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.mobile_navigation)
        when (args.tab) {
            StartTab.Home -> {
                graph.setStartDestination(R.id.navigation_home)
                navController.setGraph(
                    graph = graph,
                    HomeFragmentArgs(
                        text = System.currentTimeMillis().toString(),
                        isOpen = false,
                    ).toBundle()
                )
            }
            StartTab.Dashboard -> {
                graph.setStartDestination(R.id.navigation_dashboard)
                navController.setGraph(graph = graph, null)
            }
            StartTab.Notifications -> {
                graph.setStartDestination(R.id.navigation_notifications)
                navController.setGraph(graph = graph, null)
            }
            StartTab.HomeWithWeb -> {
                graph.setStartDestination(R.id.navigation_home)
                navController.setGraph(
                    graph = graph,
                    HomeFragmentArgs(
                        text = System.currentTimeMillis().toString(),
                        isOpen = true,
                    ).toBundle()
                )
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}