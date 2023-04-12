package com.example.springgatling.service

import com.example.springgatling.model.User
import com.example.springgatling.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(val userRepository: UserRepository) : UserService {
    override fun getUserById(id: Long): Optional<User> {
        return userRepository.findUserById(id)
    }

    override fun getAllUsers(pageRequest: PageRequest): Page<User> {
        return userRepository.findAllUsers(pageRequest)
    }

    override fun createUser(user: User): User {
        return userRepository.save(user)
    }
}
