package com.example.springgatling.model

import jakarta.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    val email: String
) {
    @ManyToMany
    val tasks: MutableList<Task> = mutableListOf()
}

