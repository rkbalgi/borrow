package com.github.rkbalgi.apps.borrow.security

import com.github.rkbalgi.apps.borrow.data.UserRepo
import com.vaadin.flow.component.UI
import com.vaadin.flow.server.VaadinServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Component


@Component
class SecurityService(@Autowired private val userRepo: UserRepo) {


    fun getAuthenticatedUser(): UserDetails? {


        val context = SecurityContextHolder.getContext()
        val principal = context.authentication?.principal
        return if (principal is UserDetails) {
            principal
        } else null

    }


}

fun logout() {
    UI.getCurrent().page.setLocation("/")
    val logoutHandler = SecurityContextLogoutHandler()
    logoutHandler.logout(
        VaadinServletRequest.getCurrent().httpServletRequest, null,
        null
    )
}