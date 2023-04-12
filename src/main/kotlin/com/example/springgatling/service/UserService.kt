package com.example.springgatling.service

import com.example.springgatling.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.*

interface UserService {
    fun getUserById(id: Long): Optional<User>
    fun getAllUsers(pageRequest: PageRequest): Page<User>
    fun createUser(user: User): User
}