package org.ProjectV1.service

import org.ProjectV1.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService : UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        // Fetch User from the DB
        val userRes: Optional<org.ProjectV1.models.User> = userRepository.findByUsername(username)
        // No user found
        if (userRes.isEmpty) throw UsernameNotFoundException("Could not findUser with username = $username")
        // Return a User Details object using the fetched User information
        val user = userRes.get()
        return User(
            username,
            user.password, listOf(SimpleGrantedAuthority(user.role))
        )
    }
}

