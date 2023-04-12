package com.example.springgatling.model

import jakarta.persistence.*

@Entity
class Task(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    val description: String,
) {
    @ManyToMany(mappedBy = "tasks")
    val users: MutableList<User> = mutableListOf()
}