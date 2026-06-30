package java.joavlr03.com.github.remotecontroller.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.joavlr03.com.github.remotecontroller.network.CommandRequest
import java.joavlr03.com.github.remotecontroller.network.ComputerRequest
import java.joavlr03.com.github.remotecontroller.network.RetrofitInstance

class MainViewModel : ViewModel() {

    public val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun createComputer(name: String, ipAddress: String, macAddress: String, operatingSystem: String) {
        viewModelScope.launch {
            try {
                android.util.Log.d("RETROFIT", "Enviando requisição para createComputer...")
                val response = RetrofitInstance.api.createComputer(
                    ComputerRequest(name, ipAddress, macAddress, operatingSystem)
                )
                android.util.Log.d("RETROFIT", "Resposta: ${response.id}")
                _message.value = "Computador criado! ID: ${response.id}"
            } catch (e: Exception) {
                android.util.Log.e("RETROFIT", "Erro: ${e.message}", e)
                _message.value = "Erro: ${e.message}"
            }
        }
    }

    fun sendCommand(type: String, computerId: Long) {
        viewModelScope.launch {
            try {
                android.util.Log.d("RETROFIT", "Enviando comando: type=$type computerId=$computerId")
                val response = RetrofitInstance.api.createCommand(CommandRequest(type, computerId))
                android.util.Log.d("RETROFIT", "Comando enviado! Status: ${response.status}")
                _message.value = "Comando enviado! Status: ${response.status}"
            } catch (e: Exception) {
                android.util.Log.e("RETROFIT", "Erro comando: ${e.message}", e)
                _message.value = "Erro: ${e.message}"
            }
        }
    }

    fun clearMessage() { _message.value = null }
}