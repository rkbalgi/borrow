package com.github.rkbalgi.apps.borrow.views

import com.github.mvysny.karibudsl.v10.drawerToggle
import com.github.mvysny.karibudsl.v10.navbar
import com.github.rkbalgi.apps.borrow.security.SecurityService
import com.github.rkbalgi.apps.borrow.security.logout
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.HighlightConditions
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.server.VaadinService
import org.springframework.stereotype.Component
import javax.swing.text.html.ListView


class MainLayout() : AppLayout() {

    init {
        isDrawerOpened = false

        createHeader()
        createDrawer()
    }


    private fun createHeader() {
        val logo = H3("BorrowApp1.kt")
        logo.addClassNames("text-l", "m-m")
        val logout = Button("Log out") { _ -> logout() }

        val header1: Text =
            if (VaadinService.getCurrentRequest()?.userPrincipal != null) {
                Text("Logged In:   ${VaadinService.getCurrentRequest()?.userPrincipal?.name}")
            } else {
                Text("")
            }

        val header = HorizontalLayout(DrawerToggle(), logo, header1, logout)
        header.defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        header.expand(logo)
        header.width = "100%"
        header.addClassNames("py-0", "px-m")

        addToNavbar(header)
    }

    private fun createDrawer() {

        val listLink = RouterLink("Dashboard", DashboardView::class.java)
        listLink.highlightCondition = HighlightConditions.sameLocation()

        addToDrawer(
            VerticalLayout(
                listLink,
            )
        )
    }
}