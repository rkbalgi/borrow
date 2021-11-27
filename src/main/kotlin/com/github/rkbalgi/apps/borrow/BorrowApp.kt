package com.github.rkbalgi.apps.borrow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BorrowApp

fun main(args: Array<String>) {
    runApplication<BorrowApp>(*args)
}