package java.joavlr03.com.github.remotecontroller.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.joavlr03.com.github.remotecontroller.model.MainViewModel

@Composable
fun CommandScreen(viewModel: MainViewModel) {
    var computerId by remember { mutableStateOf("") }
    val message by viewModel.message.collectAsState()

    val commandTypes = listOf("SHUTDOWN", "RESTART", "SLEEP")
    var selected by remember { mutableStateOf(commandTypes[0]) }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Enviar Comando", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = computerId,
            onValueChange = { computerId = it },
            label = { Text("ID do Computador") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Tipo de Comando:")
        commandTypes.forEach { type ->
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                RadioButton(
                    selected = selected == type,
                    onClick = { selected = type }
                )
                Text(type)
            }
        }

        Button(
            onClick = {
                android.util.Log.d("BUTTON", "Botão enviar clicado! computerId=$computerId selected=$selected")
                val id = computerId.toLongOrNull()
                if (id != null) {
                    viewModel.sendCommand(selected, id)
                } else {
                    viewModel._message.value = "ID inválido. Digite um número."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        message?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
            LaunchedEffect(it) { viewModel.clearMessage() }
        }
    }
}