package com.business.profile.service

import com.business.profile.exception.ProfileNotFoundException
import com.business.profile.model.BusinessProfile
import com.business.profile.model.ProductValidation
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProfileService(val repository: BusinessProfileRepository, val validationRepository: ProductionValidationRepository) {

    fun addBusinessProfile(profile: BusinessProfile): BusinessProfile {
        val businessProfile =  repository.save(profile)
        val productValidation = businessProfile.id?.let { ProductValidation(businessProfileId = it) }
        validationRepository.save(productValidation!!)
        return businessProfile
    }

    fun fetchProfileById(id: String): BusinessProfile? {
        return repository.findById(id).orElse(null)
    }

    fun fetchAllProfiles(): MutableList<BusinessProfile> {
        return repository.findAll()
    }

    fun updateProfile(id: String, profile: BusinessProfile, productName: String): BusinessProfile? {
        if (repository.existsById(id)) {
            profile.id = id
            val updatedProfile =  repository.save(profile)
            val validationProfile = validationRepository.findByBusinessProfileId(updatedProfile.id!!)
            validationProfile?.validatedProduct = listOf(productName)
            validationProfile?.profileChangeValidated = false
            validationRepository.save(validationProfile!!)
            return updatedProfile
        }
        return null
    }

    fun deleteProfileById(id: String) {
        if (!repository.existsById(id)) {
            throw ProfileNotFoundException("Business Profile with id = $id not found")
        }
        repository.deleteById(id)
    }
}