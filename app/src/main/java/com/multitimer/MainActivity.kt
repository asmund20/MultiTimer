package com.multitimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.multitimer.ui.main.MainScreen
import com.multitimer.ui.main.MainScreenViewModel
import com.multitimer.ui.theme.MultiTimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiTimerTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val mainScreenViewModel = MainScreenViewModel()
    MainScreen(mainScreenViewModel)
}