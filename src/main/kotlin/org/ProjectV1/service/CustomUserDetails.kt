package org.ProjectV1.service

import org.ProjectV1.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails: UserDetails {

    private lateinit var user: User

    /*fun CustomUserDetails(user: User) {
        super()
        this.user = user
    } */

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return setOf(SimpleGrantedAuthority(user.role))
    }

    override fun getPassword(): String? {
        return user.password
    }

    override fun getUsername(): String? {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}