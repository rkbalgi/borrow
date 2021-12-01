package com.github.rkbalgi.apps.borrow.security

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CryptoKtTest {


    @Test
    fun test_sha256() {
        assertEquals("ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae", sha256("test123"))
    }
}