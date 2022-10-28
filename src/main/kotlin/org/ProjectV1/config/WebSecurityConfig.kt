package org.ProjectV1.config

import org.ProjectV1.models.JWTFilter
import org.ProjectV1.repository.UserRepository
import org.ProjectV1.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * properties for WebSecurity Config
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig: WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }

    @Autowired
    private lateinit var filter: JWTFilter

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var uds: CustomUserDetailsService

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService())
        provider.setPasswordEncoder(BCryptPasswordEncoder())
        return provider
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(HttpMethod.POST, "/register").antMatchers("/login")
    }

    @Throws(java.lang.Exception::class)
    override fun configure(http: HttpSecurity) { // Method to configure your app security
        http.csrf().disable() // Disabling csrf
            .httpBasic().disable() // Disabling http basic
            .cors() // Enabling cors
            .and()
            .authorizeHttpRequests() // Authorizing incoming requests
            .antMatchers(HttpMethod.POST, "/register").permitAll()
            .antMatchers("/login").permitAll() // Allows auth requests to be made without authentication of any sort
            .antMatchers("/user/{username}")
            .hasAuthority("USER") // Allows only users with the "USER" role to make requests to the user routes
            .antMatchers("/admin")
            .hasAuthority("ADMIN")
            .and()
            .userDetailsService(uds) // Setting the user details service to the custom implementation
            .exceptionHandling()
            .authenticationEntryPoint() // Rejecting request as unauthorized when entry point is reached
        // If this point is reached it means that the current request requires authentication
        // and no JWT token was found attached to the Authorization header of the current request.
        { request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException ->
            response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized"
            )
        }
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Setting Session to be stateless */

        // Adding the JWT filter
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }
}