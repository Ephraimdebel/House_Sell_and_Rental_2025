data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val msg: String,
    val token: String,
    val username: String,
    val userid: Int,
    val role: String
)
