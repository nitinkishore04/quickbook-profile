package com.business.profile.service

import com.business.profile.exception.ProfileNotFoundException
import com.business.profile.model.BusinessAddress
import com.business.profile.model.BusinessProfile
import com.business.profile.model.TaxIdentifiers
import com.mongodb.assertions.Assertions.assertNotNull
import com.mongodb.assertions.Assertions.assertNull
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProfileServiceTest {

    @InjectMocks
    private lateinit var service: ProfileService

    @Mock
    private lateinit var repository: BusinessProfileRepository


    @Test
    fun whenSuccessfulRequestIsSent_thenNewProfileShouldBeCreated() {
        val profile = giveDummyProfile(null)
        Mockito.`when`(repository.save(Mockito.any(BusinessProfile::class.java))).thenReturn(profile)
        val result = service.addBusinessProfile(profile)

        assertNotNull(result)
        Assertions.assertEquals("Test Name", result.companyName)
    }

    @Test
    fun whenUserRequestProfileById_thenItShouldProfile() {
        Mockito.`when`(repository.findById(Mockito.any(String::class.java))).thenReturn(Optional.of(giveDummyProfile(null)))
        val result = service.fetchProfileById("1234")

        assertNotNull(result)
    }

    @Test
    fun whenUserRequestForAllProfile_thenAllProfileShouldBeReturned() {
        Mockito.`when`(repository.findAll()).thenReturn(mutableListOf(giveDummyProfile(null)))
        val result = service.fetchAllProfiles()
        Assertions.assertEquals(result.size, 1)

    }
    @Test
    fun whenUnknownIdIsSent_thenItShouldThrowException() {
        Mockito.`when`(repository.existsById(Mockito.any(String::class.java))).thenReturn(false)

        Assertions.assertThrows(ProfileNotFoundException::class.java) {
            service.deleteProfileById("123")
        }
    }

    @Test
    fun whenUserTriesToUpdateValidProfile_thenProfileShouldBeUpdated() {
        Mockito.`when`(repository.existsById(Mockito.any(String::class.java))).thenReturn(true)
        Mockito.`when`(repository.save(Mockito.any(BusinessProfile::class.java))).thenReturn(giveDummyProfile("Updated Name"))

        val result = service.updateProfile("123", giveDummyProfile("Updated Name"))

        assertNotNull(result)
        Assertions.assertEquals(result?.companyName,"Updated Name")

    }

    @Test
    fun whenUserTriesToUpdateInvalidProfile_thenProfileShouldNotBeUpdated() {
        Mockito.`when`(repository.existsById(Mockito.any(String::class.java))).thenReturn(false)
        val result = service.updateProfile("123", giveDummyProfile("Updated Name"))
        assertNull(result)
    }

    @Test
    fun whenKnownIdIsSent_thenItShouldBeDeleted() {
        Mockito.`when`(repository.existsById(Mockito.any(String::class.java))).thenReturn(true)
        service.deleteProfileById("123")
        Mockito.verify(repository).deleteById(Mockito.any(String::class.java))
    }

    private fun giveDummyProfile(name: String?): BusinessProfile {
        return BusinessProfile(
            companyName = name?: "Test Name",
            legalName =  "Test name",
            businessAddress = BusinessAddress(line1 = "Test", line2 = "Test 2", city = "Test City", state = "State", zip = "test-zip", country = "India"),
            legalAddress = BusinessAddress(line1 = "Test", line2 = "Test 2", city = "Test City", state = "State", zip = "test-zip", country = "India"),
            taxIdentifiers = TaxIdentifiers(pan = "test1234", ein = "test5678"),
            email = "test@test.com",
            website = "www.google.com"
        )
    }
}