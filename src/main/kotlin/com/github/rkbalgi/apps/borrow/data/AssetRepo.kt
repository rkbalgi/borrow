package com.github.rkbalgi.apps.borrow.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AssetRepo : CrudRepository<Asset, UUID> {
    override fun findAll(): MutableIterable<Asset>
    override fun findById(id: UUID): Optional<Asset>
    override fun <S: Asset?> save(asset: S):S



}