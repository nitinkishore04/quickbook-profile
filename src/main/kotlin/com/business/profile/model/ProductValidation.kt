package com.business.profile.model

import com.business.profile.util.Util
import java.util.UUID

data class ProductValidation(
    val id: String? = UUID.randomUUID().toString(),
    val businessProfileId: String,
    var subscribedProduct: List<String>? = listOf(),
    var validatedProduct: List<String>? = listOf(),
    var profileChangeValidated: Boolean = Util.compareStringLists(validatedProduct!!, subscribedProduct!!)
) {
    constructor(businessProfileId: String) : this(businessProfileId = businessProfileId, validatedProduct = listOf(), subscribedProduct = listOf())
}