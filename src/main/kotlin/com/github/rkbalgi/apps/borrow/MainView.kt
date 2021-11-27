package com.github.rkbalgi.apps.borrow

import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.PWA

/**
 * The main view contains a button and a click listener.
 */
@Route("")
class MainView : KComposite() {
    private lateinit var nameField: TextField
    private lateinit var addAssetButton: Button
    private lateinit var addAssetDialog: Dialog

    // The main view UI definition
    private val root = ui {


        //appLayout {

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        verticalLayout() {

            // Use TextField for standard text input
            nameField = textField("Your name")

            // Use Button for a clickable button
            addAssetButton = button("Add Asset") {

                addClickShortcut(Key.ENTER)
                addThemeVariants(ButtonVariant.MATERIAL_OUTLINED)


            }

            addAssetDialog = dialog() {
                val d = this
                width = "40%"




                formLayout {
                    val layout = this

                    var authorItem: FormLayout.FormItem? = null

                    formItem("Type") {
                        comboBox<String> {
                            setItems("Book", "Gadget")
                            addValueChangeListener {
                                if (it?.value === "Book") {
                                    authorItem?.setVisible(true)
                                } else {
                                    authorItem?.setVisible(false)
                                }
                            }


                        }
                    }
                    formItem("Title") {
                        textField() { tooltip = "title" }

                    }
                    authorItem = layout.formItem("Author(s)") { textField() }
                    authorItem.setVisible(false)

                    formItem("Tags") { textField() { tooltip = "space separated tags related to the asset" } }

                    formItem {
                        horizontalLayout {
                            button("OK").onLeftClick {
                                // update in repo
                                d.close()
                            }
                            button("Cancel").onLeftClick {
                                d.close()
                            }
                            this.alignItems = FlexComponent.Alignment.CENTER
                        }

                    }


                }


            }
        }

        //}


    }

    init {
        // attach functionality to the UI components.
        // It's a good practice to keep UI functionality separated from UI definition.

        // Button click listeners can be defined as lambda expressions
        addAssetButton.addClickListener {
            addAssetDialog.open()
        }
    }
}

@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
class AppShell : AppShellConfigurator
