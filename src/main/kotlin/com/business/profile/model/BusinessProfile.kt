package com.business.profile.model

import java.util.UUID

data class BusinessProfile(
    var id: String? = UUID.randomUUID().toString(),
    val companyName: String,
    val legalName: String,
    val businessAddress: BusinessAddress,
    val legalAddress: BusinessAddress,
    val taxIdentifiers: TaxIdentifiers,
    val email: String?,
    val website: String?
)
