package com.business.profile.service

import com.business.profile.model.BusinessProfile
import org.springframework.stereotype.Service

@Service
class ProfileService(val businessProfileRepository: BusinessProfileRepository) {

    fun addBusinessProfile(profile: BusinessProfile): BusinessProfile {
        return businessProfileRepository.save(profile);
    }
}