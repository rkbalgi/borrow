package com.github.rkbalgi.apps.borrow.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
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
    @OneToOne @JoinColumn(name="community_id") var community: Community
)

@Entity
class Community(@Id var id: UUID, var name: String, var location: String)

@Repository
interface UserRepo : CrudRepository<User, String>{
     fun findByUserName(id: String): User?
}

@Repository
interface CommunityRepo : CrudRepository<Community, String>
