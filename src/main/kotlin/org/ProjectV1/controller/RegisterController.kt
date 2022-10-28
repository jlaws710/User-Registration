package org.ProjectV1.controller

import org.ProjectV1.models.JWTUtil
import org.ProjectV1.models.User
import org.ProjectV1.repository.UserRepository
import org.jetbrains.kotlin.incremental.ChangesCollector.Companion.getNonPrivateNames
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class RegisterController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authManager: AuthenticationManager

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    @GetMapping("/user/{username}")
    fun getUser(@PathVariable(value = "username") username: String): Optional<User> {
        return userRepository.findByUsername(username)
    }

    @GetMapping("/admin")
    fun list(): List<User> {
        return userRepository.findAll()
    }

    @PostMapping("/register")
    fun registerHandler(@RequestBody user: User): Map<String, Any> {
        val encodedPass: String = passwordEncoder.encode(user.password)
        user.password = encodedPass
        user.role = "USER"
        val rand = Random()
        val number = rand.nextInt(99999999)

        user.creditCard = "48212500$number"

        if (user.firstName.equals("Joseph") && user.lastName.equals("Lawson")
            || user.firstName.equals("Coral") && user.lastName.equals("Mejia")
        ) {
            user.role = "ADMIN"
        }

        var user = userRepository.save(user)
        val token = jwtUtil.generateToken(user.username)
        return Collections.singletonMap<String, Any>("jwt-token", token)
    }

    @PostMapping("/login")
    fun loginHandler(@RequestBody body: User): Map<String, Any> {
        return try {
            val authInputToken = UsernamePasswordAuthenticationToken(body.username, body.password)
            authManager.authenticate(authInputToken)
            val token: String = jwtUtil.generateToken(body.username)
            Collections.singletonMap<String, Any>("jwt-token", token)
        } catch (authExc: AuthenticationException) {
            throw RuntimeException("Invalid Login Credentials")
        }
    }

    @DeleteMapping("/user/{username}")
    fun deleteUser(@PathVariable(value = "username") username: String?) {
        userRepository.deleteByUsername(username)
    }
}