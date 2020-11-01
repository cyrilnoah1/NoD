package com.example.nod

import com.example.nod.common.UI_DATE_FORMAT
import com.example.nod.common.UTC_DATE_FORMAT
import com.example.nod.common.getDesiredDateInDesiredFormat
import com.example.nod.common.isValidUrl
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UtilitiesTest {

    private lateinit var validUrl: String
    private lateinit var invalidUrl: String
    private lateinit var utcDate: String
    private lateinit var validDate: String
    private lateinit var invalidDate: String

    @Before
    fun init() {

        validUrl = "https://www.google.com"
        invalidUrl = "abcdefg"

        utcDate = "2019-03-12T06:35:31Z"
        validDate = "12 Mar 2019 - 12:05 PM"
        invalidDate = "15 Mar 2019 - 11:42 AM"
    }

    /**
     * Function to check whether the [isValidUrl] function asserts true when
     * a [validUrl] is provided.
     */
    @Test
    fun urlValidity_isValid() {
        assertTrue(validUrl.isValidUrl())
    }

    /**
     * Function to check whether the [isValidUrl] function asserts false when
     * an [invalidUrl] is provided.
     */
    @Test
    fun urlValidity_isInvalid() {
        assertTrue(!invalidUrl.isValidUrl())
    }

    /**
     * Function to check whether the [getDesiredDateInDesiredFormat] function converts the
     * provided [utcDate] to [validUrl] when converting from [UTC_DATE_FORMAT] to [UI_DATE_FORMAT].
     */
    @Test
    fun dateConversionValidity_isValid() {
        assertTrue(
            getDesiredDateInDesiredFormat(
                utcDate,
                UTC_DATE_FORMAT,
                UI_DATE_FORMAT
            ) == validDate
        )
    }

    /**
     * Function to check whether the [getDesiredDateInDesiredFormat] function does not convert the
     * provided [utcDate] to [invalidUrl] when converting from [UTC_DATE_FORMAT] to [UI_DATE_FORMAT].
     */
    @Test
    fun dateConversionValidity_isInvalid() {
        assertTrue(
            getDesiredDateInDesiredFormat(
                utcDate,
                UTC_DATE_FORMAT,
                UI_DATE_FORMAT
            ) != invalidDate
        )
    }
}