package com.github.rkbalgi.apps.borrow.data

import java.sql.Timestamp
import java.util.*
import javax.persistence.*


@Entity
class User(
    @Id @Column(name = "user_name") var userName: String,
    var password: String,
    @Column(name = "created_ts") var createdTS: Timestamp,
    @Column(name = "last_updated_ts") var lastUpdatedTS: Timestamp,
    @Column(name = "last_login_ts") var lastLoginTS: Timestamp,
    @Column(name = "addr") var address: String,
    @Column(name = "email") var email: String,
    @Column(name = "phone") var phone: String,
    @OneToOne @JoinColumn(name = "community_id") var community: Community
)

enum class AssetType {
    Book, Gadget
}

@Entity
class Asset(
    @Id var id: UUID,
    var name: String,
    var tags: String,
    var authors: String,
    var comments: String,
    @Enumerated(EnumType.STRING) var type: AssetType,
    @OneToOne @JoinColumn(name = "owner") var owner: User
)


@Entity
class Community(@Id var id: UUID, var name: String, var location: String)


