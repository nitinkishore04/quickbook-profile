package com.business.profile.service

import com.business.profile.exception.ProfileNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ProfileServiceTest {

    @InjectMocks
    private lateinit var service: ProfileService

    @Mock
    private lateinit var repository: BusinessProfileRepository

    @Test
    fun testDelete() {
        Mockito.`when`(repository.existsById(Mockito.any(String::class.java))).thenReturn(false)

        Assertions.assertThrows(ProfileNotFoundException::class.java) {
            service.deleteProfileById("123")
        }
    }
}