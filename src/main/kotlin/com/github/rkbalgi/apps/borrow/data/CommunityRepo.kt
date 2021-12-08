package com.github.rkbalgi.apps.borrow.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommunityRepo : CrudRepository<Community, UUID> {
    override fun findAll(): MutableIterable<Community>
    override fun findById(id: UUID): Optional<Community>
}