package com.business.profile.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class ProfileController {

    @GetMapping("/all")
    fun getAllBusinessProfile(): String {
        return "Hello World"
    }

}