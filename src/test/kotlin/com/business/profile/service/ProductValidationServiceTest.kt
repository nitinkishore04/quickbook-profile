package com.business.profile.service

import com.business.profile.dto.ProductSubscriptionList
import com.business.profile.exception.ProductValidationException
import com.business.profile.model.ProductValidation
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ProductValidationServiceTest{

    @InjectMocks
    private lateinit var service: ProductValidationService

    @Mock
    private lateinit var repository: ProductionValidationRepository

    @Test
    fun whenUserRequestForValidationProfile_andIfIdIsCorrect_thenValidationProfileShouldBeReturned() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(true)
        Mockito.`when`(repository.findByBusinessProfileId("test")).thenReturn(getDummyValidationData())
        service.getValidationProfile("test")
        Mockito.verify(repository).findByBusinessProfileId("test")
    }

    @Test
    fun whenUserRequestForValidationProfile_andIfIdIsInvalid_thenItShouldThrowException() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(false)
        Assertions.assertThrows(ProductValidationException::class.java) {
            service.getValidationProfile("test")
        }
    }

    @Test
    fun whenUserSendsValidData_thenValidationProfileShouldBeCreated(){
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(false)
        Mockito.`when`(repository.save(Mockito.any(ProductValidation::class.java))).thenReturn(getDummyValidationData())
        val result = service.createValidationProfile("test")

        Assertions.assertEquals(result.businessProfileId, "test")
    }

    @Test
    fun whenUserSendsInvalidData_thenValidationProfileShouldThrowException(){
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(true)

        Assertions.assertThrows(ProductValidationException::class.java) {
            service.createValidationProfile("test")
        }
    }

    @Test
    fun whenUserSendsValidDataToSubscribeProduct_thenItShouldGetsSubscribed() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(true)
        Mockito.`when`(repository.findByBusinessProfileId("test")).thenReturn(getDummyValidationData())
        Mockito.`when`(repository.save(Mockito.any(ProductValidation::class.java))).thenReturn(getDummyValidationData())
        service.subscribeProduct("test", ProductSubscriptionList(listOf("hello")))
        Mockito.verify(repository).save(Mockito.any(ProductValidation::class.java))
    }

    @Test
    fun whenUserSendsInvalidDataToSubscribeProduct_thenItShouldThrowException() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(false)

        Assertions.assertThrows(ProductValidationException::class.java) {
            service.subscribeProduct("test", ProductSubscriptionList(listOf("hello")))
        }
    }

    @Test
    fun whenUserSendsValidDataForValidation_thenItShouldGetUpdated() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(true)
        Mockito.`when`(repository.findByBusinessProfileId("test")).thenReturn(getDummyValidationData())
        Mockito.`when`(repository.save(Mockito.any(ProductValidation::class.java))).thenReturn(getDummyValidationData())

        service.updateValidationProfile("test", "QBPayroll")

        Mockito.verify(repository).save(Mockito.any(ProductValidation::class.java))
    }

    @Test
    fun whenUserRequestValidationFromUnsubscribedProduct_thenItShouldThrowException() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(true)
        Mockito.`when`(repository.findByBusinessProfileId("test")).thenReturn(getDummyValidationData())

        Assertions.assertThrows(ProductValidationException::class.java) {
            service.updateValidationProfile("test", "SomeProduct")
        }
    }

    @Test
    fun whenUserSendsValidDataForValidation_butProductHasAlreadyValidated_thenItShouldThrowException() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(true)
        Mockito.`when`(repository.findByBusinessProfileId("test")).thenReturn(getDummyValidationData())

        Assertions.assertThrows(ProductValidationException::class.java) {
            service.updateValidationProfile("test", "TSheet")
        }
    }

    @Test
    fun whenUserSendsInvalidDataForValidation_thenItShouldThrowException() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(false)

        Assertions.assertThrows(ProductValidationException::class.java) {
            service.updateValidationProfile("test", "TSheet")
        }
    }

    @Test
    fun whenUserSendsValid_thenItShouldCheckProductValidation() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(true)
        Mockito.`when`(repository.findByBusinessProfileId("test")).thenReturn(getDummyValidationData())
        val result = service.verifyProductValidation("test", "TestName")
        Assertions.assertEquals(result, false)
    }

    @Test
    fun whenUserSendsInvalid_thenItShouldThrowException() {
        Mockito.`when`(repository.existsByBusinessProfileId("test")).thenReturn(false)


        Assertions.assertThrows(ProductValidationException::class.java) {
            service.verifyProductValidation("test", "TestName")
        }
    }



    private fun getDummyValidationData(): ProductValidation {
        return ProductValidation(
            businessProfileId = "test",
            validatedProduct = listOf("TSheet"),
            subscribedProduct = listOf("QBPayroll", "TSheet")
        )
    }

}