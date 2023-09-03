package com.business.profile.model

import java.util.UUID

data class BusinessProfile(
    val id: UUID? = UUID.randomUUID(),
    val companyName: String,
    val legalName: String,
    val businessAddress: BusinessAddress,
    val legalAddress: BusinessAddress,
    val taxIdentifiers: TaxIdentifiers,
    val email: String?,
    val website: String?
)
