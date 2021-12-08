package com.github.rkbalgi.apps.borrow.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : CrudRepository<User, String> {
    fun findByUserName(id: String): User?
    fun save(user: User)

}