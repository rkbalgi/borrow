package com.github.rkbalgi.apps.borrow.views

import com.github.rkbalgi.apps.borrow.security.SecurityService
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.HighlightConditions
import com.vaadin.flow.router.RouterLink
import javax.swing.text.html.ListView


class MainLayout : AppLayout() {

    private val securityService = SecurityService()

    init {
        createHeader()
        createDrawer()
    }


    private fun createHeader() {
        val logo = H1("BorrowApp")
        logo.addClassNames("text-l", "m-m")
        val logout = Button("Log out") { _ -> securityService.logout() }
        val header = HorizontalLayout(DrawerToggle(), logo, logout)
        header.defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        header.expand(logo)
        header.width = "100%"
        header.addClassNames("py-0", "px-m")
        addToNavbar(header)
    }

    private fun createDrawer() {
        addToDrawer(
            VerticalLayout(
                RouterLink("Dashboard", DashboardView::class.java)
            )
        )
    }
}