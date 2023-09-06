package com.business.profile.controller

import com.business.profile.dto.ProductSubscriptionList
import com.business.profile.model.ProductValidation
import com.business.profile.service.ProductValidationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/validate")
@CrossOrigin(
    origins = ["http://localhost:3000"],
    methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT]
)
class ProductValidationController(val service: ProductValidationService) {

    @GetMapping("/{profileId}")
    fun getProductValidationDetail(@PathVariable profileId: String): ResponseEntity<ProductValidation> {
        val validationProfile = service.getValidationProfile(profileId)
        return ResponseEntity.status(HttpStatus.OK).body(validationProfile)
    }

    @PostMapping("/create/{profileId}")
    fun createValidationProfile(@PathVariable profileId: String): ResponseEntity<ProductValidation> {
        val validationProfile = service.createValidationProfile(profileId = profileId)
        return ResponseEntity.status(HttpStatus.CREATED).body(validationProfile)

    }

    @PutMapping("/approve/{profileId}")
    fun validateProfileChange( @PathVariable profileId: String, @RequestParam productName: String): ResponseEntity<ProductValidation> {
        val validate = service.updateValidationProfile(profileId, productName)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(validate)
    }

    @PutMapping("/subscribe/{profileId}")
    fun subscribeProduct(@PathVariable profileId: String, @RequestBody productList: ProductSubscriptionList): ResponseEntity<ProductValidation> {
        val subscribe = service.subscribeProduct(profileId, productList)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(subscribe)
    }

    @PutMapping("/verify/{profileId}")
    fun verifyValidation(@PathVariable profileId: String, @RequestParam productName: String): ResponseEntity<Boolean> {
        val validationProfile = service.verifyProductValidation(profileId, productName)
        return ResponseEntity.status(HttpStatus.OK).body(validationProfile)
    }
}