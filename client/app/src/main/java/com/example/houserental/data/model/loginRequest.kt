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
data class RegisterRequest(
    val full_name: String,
    val email: String,
    val password: String,
    val phone_number: String
)

data class RegisterResponse(val message: String)
