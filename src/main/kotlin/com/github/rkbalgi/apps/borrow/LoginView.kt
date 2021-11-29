package com.github.rkbalgi.apps.borrow

import com.github.mvysny.karibudsl.v10.h1
import com.vaadin.flow.component.login.LoginForm
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route("login")
@PageTitle("Login | BorrowApp")
class LoginView : VerticalLayout(), BeforeEnterObserver {


    private val login = LoginForm()

    init {
        addClassName("login-view")
        setSizeFull()
        alignItems = (FlexComponent.Alignment.CENTER)
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        login.action = "login"


        add(h1("BorrowApp - "+System.getenv().getOrDefault("borrow-app.install-name","Unspecified")), login)
    }


    override fun beforeEnter(p0: BeforeEnterEvent?) {

        if (p0?.location?.queryParameters?.parameters?.containsKey("error")!!) {
            login.setError(true)
        }

    }
}