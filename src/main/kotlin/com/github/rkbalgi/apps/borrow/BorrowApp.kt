package com.github.rkbalgi.apps.borrow

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.BodySize
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.material.Material
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
@PWA(name = "Borrow App", shortName = "borrow-app")
@Viewport("width=device-width, initial-scale=1")
@BodySize(height = "100vh", width = "100vw")
@PageTitle("Borrow! - Share books and other things within your community!")
@Theme(themeClass = Material::class, variant = Material.LIGHT)
class BorrowApp : SpringBootServletInitializer(), AppShellConfigurator

fun main(args: Array<String>) {
    runApplication<BorrowApp>(*args)
}