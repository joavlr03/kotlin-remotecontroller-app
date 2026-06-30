package java.joavlr03.com.github.remotecontroller.network

import retrofit2.http.Body
import retrofit2.http.POST

data class ComputerRequest(
    val name: String,
    val ipAddress: String,
    val macAddress: String,
    val operatingSystem: String
)

data class ComputerResponse(
    val id: Long,
    val name: String,
    val ipAddress: String
)

data class CommandRequest(
    val type: String,
    val computerId: Long
)

data class CommandResponse(
    val id: Long,
    val type: String,
    val status: String
)

interface ApiService {

    @POST("api/v1/computers")
    suspend fun createComputer(@Body request: ComputerRequest): ComputerResponse

    @POST("api/v1/commands")
    suspend fun createCommand(@Body request: CommandRequest): CommandResponse
}