package com.business.profile.service

import com.business.profile.dto.ProductSubscriptionList
import com.business.profile.exception.ProductValidationException
import com.business.profile.model.ProductValidation
import com.business.profile.util.Util
import org.springframework.stereotype.Service

@Service
class ProductValidationService(val repository: ProductionValidationRepository) {

    fun getValidationProfile(businessProfileId: String): ProductValidation? {
        if(!repository.existsByBusinessProfileId(businessProfileId)) {
            throw ProductValidationException("No Business Profile Found for id = $businessProfileId")
        }
        return repository.findByBusinessProfileId(businessProfileId)
    }

    fun createValidationProfile(profileId: String): ProductValidation {
        if(repository.existsByBusinessProfileId(profileId)) {
            throw ProductValidationException("Subscription Profile Already Exists")
        }
        val validationProfile = ProductValidation(businessProfileId = profileId)
        return repository.save(validationProfile)
    }

    fun subscribeProduct(profileId: String, productList: ProductSubscriptionList): ProductValidation {
        if(!repository.existsByBusinessProfileId(profileId)) {
            throw ProductValidationException("No Business Profile Found for id = $profileId")
        }
        val subs = repository.findByBusinessProfileId(profileId)
        subs?.subscribedProduct = productList.subscribedProduct
        subs?.profileChangeValidated = Util.compareStringLists(subs?.validatedProduct!!, subs.subscribedProduct!!)
        return repository.save(subs)
    }

    fun updateValidationProfile(profileId: String, productName: String): ProductValidation {
        if(!repository.existsByBusinessProfileId(profileId)) {
            throw ProductValidationException("No Business Profile Found for id = $profileId")
        }
        val subs = repository.findByBusinessProfileId(profileId)
        if (subs?.validatedProduct != null) {
            if (productName in subs.validatedProduct!!) {
                throw ProductValidationException("Product = $productName has already been validated ")
            }
            val newValidatedList = subs.validatedProduct!! + productName
            subs.validatedProduct = newValidatedList
        } else {
            val validatedProduct = listOf(productName)
            subs?.validatedProduct = validatedProduct
        }
        subs?.profileChangeValidated = Util.compareStringLists(subs?.validatedProduct!!, subs?.subscribedProduct!!)
        return repository.save(subs)
    }

    fun verifyProductValidation(profileId: String, productName: String): Boolean {
        if(!repository.existsByBusinessProfileId(profileId)) {
            throw ProductValidationException("No Business Profile Found for id = $profileId")
        }
        val validationData = repository.findByBusinessProfileId(profileId)
        return validationData?.validatedProduct?.contains(productName) == true
    }
}