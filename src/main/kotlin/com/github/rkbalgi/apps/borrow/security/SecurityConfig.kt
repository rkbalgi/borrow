package com.github.rkbalgi.apps.borrow.security

import com.github.rkbalgi.apps.borrow.LoginView
import com.github.rkbalgi.apps.borrow.data.UserRepo
import com.vaadin.flow.server.VaadinService
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import java.sql.Timestamp

@EnableWebSecurity
@Configuration
class SecurityConfig(@Autowired val userRepo: UserRepo) : VaadinWebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        super.configure(http)
        setLoginView(http, LoginView::class.java)
        //setLoginView(http, "login")

    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/images/**")
        super.configure(web)
    }


    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(CustomAuthProvider(userRepo))
        //auth?.inMemoryAuthentication()?.withUser("user")?.password("{noop}userpass")?.roles("USER");
    }


}

class GrantedAuthorityImpl(private val authority: String) : GrantedAuthority {
    override fun getAuthority(): String {
        return authority
    }

}


//@Component
//public class LoginSuccessListener : ApplicationListener<AuthenticationSuccessEvent> {
//
//
//    override fun onApplicationEvent(evt: AuthenticationSuccessEvent) {
//
//        // if you just need the login
//        String login = evt . getAuthentication ().getName();
//        System.out.println(login + " has just logged in");
//
//        // if you need to access full user (ie only roles are interesting -- the rest is already verified as login is successful)
//        User user =(User) evt . getAuthentication ().getPrincipal();
//        System.out.println(user.getUsername() + " has just logged in");
//
//    }
//}

@Component
class CustomAuthProvider(@Autowired private val userRepo: UserRepo) : AuthenticationProvider {
    override fun authenticate(auth: Authentication?): Authentication? {

        println("${auth?.credentials} --> ${auth?.principal}")
        val passwd = auth?.credentials as String?
        val user = auth?.principal as String

        userRepo.findByUserName(user)
            .run {
                if (this != null) {

                    println("Comparing ${sha256(passwd!!).lowercase()} with ${this.password}")
                    if (sha256(passwd).lowercase() == this.password) {

                        //successful authentication
                        this.lastLoginTS = Timestamp(System.currentTimeMillis())
                        userRepo.save(this)

                        RequestContextHolder.getRequestAttributes()
                            ?.setAttribute("logged_user", this, RequestAttributes.SCOPE_SESSION)

                        return UsernamePasswordAuthenticationToken(
                            User(
                                user, this.password, true, true,
                                true, true,
                                listOf(GrantedAuthorityImpl("ROLE_USER"))
                            ), password, listOf(GrantedAuthorityImpl("ROLE_USER"))
                        )

                    }
                }
                return null
            }


    }

    override fun supports(authentication: Class<*>?): Boolean {
        return true
    }

}