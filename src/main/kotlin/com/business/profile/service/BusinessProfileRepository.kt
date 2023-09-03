package com.business.profile.service

import com.business.profile.model.BusinessProfile
import org.springframework.data.mongodb.repository.MongoRepository

interface BusinessProfileRepository : MongoRepository<BusinessProfile, String>