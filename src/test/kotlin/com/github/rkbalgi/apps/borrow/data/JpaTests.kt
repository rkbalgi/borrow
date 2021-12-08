package com.github.rkbalgi.apps.borrow.data

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource


//@SpringBootTest
//@TestPropertySource(locations = ["classpath:application-test.properties"])
class JpaTests(@Autowired val assetRepo: AssetRepo) {


    @Test
    @Disabled
    fun test_assets() {
        val assets = mutableListOf<Asset>()
        assetRepo.findAll().toCollection(assets)
        println(assets.size)
        assertTrue(assets.size > 0)

    }
}