package com.business.profile.controller

import com.business.profile.model.BusinessProfile
import com.business.profile.service.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class ProfileController(private val service: ProfileService) {

    @GetMapping("/all")
    fun getAllBusinessProfile(): String {
        return "Hello World"
    }

    @PostMapping("/add")
    fun createBusinessProfile(@RequestBody profile: BusinessProfile): ResponseEntity<BusinessProfile> {
        val createdProfile = service.addBusinessProfile(profile)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile)
    }

}