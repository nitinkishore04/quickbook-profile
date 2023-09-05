package com.business.profile.service

import com.business.profile.model.ProductValidation
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductionValidationRepository : MongoRepository<ProductValidation, String> {

    fun findByBusinessProfileId(id: String): ProductValidation?

    fun existsByBusinessProfileId(businessProfileId: String): Boolean

}