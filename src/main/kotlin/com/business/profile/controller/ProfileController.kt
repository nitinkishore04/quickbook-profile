package com.business.profile.controller

import com.business.profile.model.BusinessProfile
import com.business.profile.service.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profile")
@CrossOrigin(
    origins = ["http://localhost:3000"],
    methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE]
)
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
        @RequestParam productName: String,
        @RequestBody profile: BusinessProfile,
    ):  ResponseEntity<Any> {
        val updatedProfile = service.updateProfile(id, profile, productName)
        return if (updatedProfile != null) {
            ResponseEntity.ok(profile)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No profile found for id = $id")
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteBusinessProfile(@PathVariable id: String): ResponseEntity<Any> {
        service.deleteProfileById(id)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Profile deleted for id = $id")
    }

}