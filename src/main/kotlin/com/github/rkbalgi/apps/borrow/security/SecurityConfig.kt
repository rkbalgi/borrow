package com.github.rkbalgi.apps.borrow.security

import com.github.rkbalgi.apps.borrow.LoginView
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
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
        setLoginView(http,LoginView::class.java)
        //setLoginView(http, "login")

    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/images/**")
        super.configure(web)
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        //super.configure(auth)
        auth?.inMemoryAuthentication()?.withUser("user")?.password("{noop}userpass")?.roles("USER");
    }


}