package org.ProjectV1.models

import com.auth0.jwt.exceptions.JWTVerificationException
import org.ProjectV1.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component // Marks this as a component. Now, Spring Boot will handle the creation and management of the JWTFilter Bean
// and you will be able to inject it in other places of your code
class JWTFilter : OncePerRequestFilter() {
    // Injecting Dependencies
    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Extracting the "Authorization" header
        val authHeader = request.getHeader("Authorization")

        // Checking if the header contains a Bearer token
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            // Extract JWT
            val jwt = authHeader.substring(7)
            if (jwt == null || jwt.isBlank()) {
                // Invalid JWT
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header")
            } else {
                try {
                    // Verify token and extract email
                    val username: String = jwtUtil.validateTokenAndRetrieveSubject(jwt)

                    // Fetch User Details
                    val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(username)

                    // Create Authentication Token
                    val authToken =
                        UsernamePasswordAuthenticationToken(username, userDetails.password, userDetails.authorities)

                    // Setting the authentication on the Security Context using the created token
                    if (SecurityContextHolder.getContext().authentication == null) {
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                } catch (exc: JWTVerificationException) {
                    // Failed to verify JWT
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token")
                }
            }
        }

        // Continuing the execution of the filter chain
        filterChain.doFilter(request, response)
    }
}