package com.example.springgatling.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/")
    fun test(): String {
        Thread.sleep(100)
        return "test"
    }
}