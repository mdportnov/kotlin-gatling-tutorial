package com.example.springgatling.controller

import com.example.springgatling.model.ResourceNotFoundException
import com.example.springgatling.model.User
import com.example.springgatling.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @GetMapping
    fun getUsers(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int
    ): Page<User> {
        return userService.getAllUsers(PageRequest.of(page, size))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user.orElseThrow { ResourceNotFoundException("User not found with id: $id") })
    }

    @PostMapping
    fun createUser(@RequestBody user: User): User {
        return userService.createUser(user)
    }
}
