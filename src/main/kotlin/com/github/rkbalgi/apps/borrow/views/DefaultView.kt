package com.github.rkbalgi.apps.borrow.views

import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import javax.annotation.security.PermitAll

@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout::class)
@PageTitle("My Assets | BorrowApp")
@PermitAll
class DefaultView : VerticalLayout() {
    //TODO::
}
