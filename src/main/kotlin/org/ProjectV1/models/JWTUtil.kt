package org.ProjectV1.models

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.stereotype.Component
import java.util.*

@Component // Marks this as a component. Now, Spring Boot will handle the creation and management of the JWTUtil Bean
// and you will be able to inject it in other places of your code
class JWTUtil {
    private val secret = UUID.randomUUID().toString()

    // Method to sign and create a JWT using the injected secret
    @Throws(IllegalArgumentException::class, JWTCreationException::class)
    fun generateToken(username: String?): String {
        return JWT.create()
            .withSubject("User Details")
            .withClaim("username", username)
            .withIssuedAt(Date())
            .withIssuer("Multiverse Project-1")
            .sign(Algorithm.HMAC256(secret))
    }

    // Method to verify the JWT and then decode and extract the user email stored in the payload of the token
    @Throws(JWTVerificationException::class)
    fun validateTokenAndRetrieveSubject(token: String?): String {
        val verifier = JWT.require(Algorithm.HMAC256(secret))
            .withSubject("User Details")
            .withIssuer("Multiverse Project-1")
            .build()
        val jwt = verifier.verify(token)
        return jwt.getClaim("username").asString()
    }
}