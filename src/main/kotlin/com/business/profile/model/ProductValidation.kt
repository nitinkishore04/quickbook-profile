package com.business.profile.model

import java.util.UUID

data class ProductValidation(
    val id: String? = UUID.randomUUID().toString(),
    val businessProfileId: String,
    var subscribedProduct: List<String>?,
    var validatedProduct: List<String>?
) {
    constructor(businessProfileId: String) : this(businessProfileId = businessProfileId, validatedProduct = listOf(), subscribedProduct = listOf())
}