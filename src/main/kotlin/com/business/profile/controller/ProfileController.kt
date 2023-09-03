package com.business.profile.controller

import com.business.profile.model.BusinessProfile
import com.business.profile.service.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class ProfileController(private val service: ProfileService) {

    @GetMapping("/all")
    fun getAllBusinessProfile(): ResponseEntity<MutableList<BusinessProfile>> {
        val profiles = service.fetchAllProfiles()
        return ResponseEntity.status(HttpStatus.OK).body(profiles)
    }

    @PostMapping("/add")
    fun createBusinessProfile(@RequestBody profile: BusinessProfile): ResponseEntity<BusinessProfile> {
        val createdProfile = service.addBusinessProfile(profile)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile)
    }

    @GetMapping("/{id}")
    fun getBusinessProfileById(@PathVariable id: String): ResponseEntity<Any> {
        val profile = service.fetchProfileById(id)
        return if (profile != null) {
            ResponseEntity.ok(profile)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No profile found for id = $id")
        }
    }


    @PutMapping("/update/{id}")
    fun updateBusinessProfile(
        @PathVariable id: String,
        @RequestBody profile: BusinessProfile
    ):  ResponseEntity<Any> {
        val updatedProfile = service.updateProfile(id, profile)
        return if (updatedProfile != null) {
            ResponseEntity.ok(profile)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No profile found for id = $id")
        }
    }

}