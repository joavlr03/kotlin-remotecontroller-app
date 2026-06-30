package java.joavlr03.com.github.remotecontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import java.joavlr03.com.github.remotecontroller.model.MainViewModel
import java.joavlr03.com.github.remotecontroller.screens.CommandScreen
import java.joavlr03.com.github.remotecontroller.screens.ComputerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    NavHost(navController, startDestination = "computers") {
        composable("computers") {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = true,
                            onClick = {},
                            label = { Text("Computadores") },
                            icon = {}
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate("commands") },
                            label = { Text("Comandos") },
                            icon = {}
                        )
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    ComputerScreen(viewModel)
                }
            }
        }
        composable("commands") {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate("computers") },
                            label = { Text("Computadores") },
                            icon = {}
                        )
                        NavigationBarItem(
                            selected = true,
                            onClick = {},
                            label = { Text("Comandos") },
                            icon = {}
                        )
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    CommandScreen(viewModel)
                }
            }
        }
    }
}