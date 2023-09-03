package com.business.profile.service

import com.business.profile.model.BusinessProfile
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProfileService(val repository: BusinessProfileRepository) {

    fun addBusinessProfile(profile: BusinessProfile): BusinessProfile {
        return repository.save(profile);
    }

    fun fetchProfileById(id: String): BusinessProfile? {
        return repository.findByIdOrNull(id)
    }

    fun fetchAllProfiles(): MutableList<BusinessProfile> {
        return repository.findAll()
    }

    fun updateProfile(id: String, profile: BusinessProfile): BusinessProfile? {
        if (repository.existsById(id)) {
            profile.id = id
            return repository.save(profile)
        }
        return null
    }
}