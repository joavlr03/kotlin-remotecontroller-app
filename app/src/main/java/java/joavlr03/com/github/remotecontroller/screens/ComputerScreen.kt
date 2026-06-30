package java.joavlr03.com.github.remotecontroller.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.joavlr03.com.github.remotecontroller.model.MainViewModel


@Composable
fun ComputerScreen(viewModel: MainViewModel) {
    var name by remember { mutableStateOf("") }
    var ipAddress by remember { mutableStateOf("") }
    var macAddress by remember { mutableStateOf("") }
    var operatingSystem by remember { mutableStateOf("") }
    val message by viewModel.message.collectAsState()

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Cadastrar Computador", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("IP (ex: 192.168.1.10)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = macAddress,
            onValueChange = { macAddress = it },
            label = { Text("MAC (ex: 00:1A:2B:3C:4D:5E)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = operatingSystem,
            onValueChange = { operatingSystem = it },
            label = { Text("Sistema Operacional (ex: Windows 11)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                android.util.Log.d("BUTTON", "Botão clicado! name=$name ip=$ipAddress")
                viewModel.createComputer(name, ipAddress, macAddress, operatingSystem)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar")
        }

        message?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
            LaunchedEffect(it) { viewModel.clearMessage() }
        }
    }
}