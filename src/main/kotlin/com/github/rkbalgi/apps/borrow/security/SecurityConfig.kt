package com.github.rkbalgi.apps.borrow.security

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@EnableWebSecurity
@Configuration
class SecurityConfig : VaadinWebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        super.configure(http)
        setLoginView(http, "login")

    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/images/**")
        super.configure(web)
    }

    override fun userDetailsService(): UserDetailsService {
        return InMemoryUserDetailsManager(User.withUsername("user").password("{noop}userpass").roles("USER").build())
    }


}