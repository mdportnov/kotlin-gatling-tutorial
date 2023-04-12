package com.example.springgatling.repository

import com.example.springgatling.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u")
    fun findAllUsers(pageable: Pageable): Page<User>

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    fun findUserById(id: Long): Optional<User>
}