package com.github.rkbalgi.apps.borrow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
class BorrowApp:SpringBootServletInitializer()

fun main(args: Array<String>) {
    runApplication<BorrowApp>(*args)
}