package com.github.rkbalgi.apps.borrow.views

import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.refresh
import com.github.mvysny.kaributools.tooltip
import com.github.rkbalgi.apps.borrow.data.*
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.BodySize
import com.vaadin.flow.component.page.Meta
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.provider.*

import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.PreserveOnRefresh
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.PWA
import com.vaadin.flow.server.VaadinService
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.material.Material
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import java.util.*
import java.util.stream.Stream

import javax.annotation.security.PermitAll


class AssetBackendDataProvider(private val assetRepo: AssetRepo) : AbstractDataProvider<Asset, Any>() {

    private var allAssets: MutableList<Asset> = mutableListOf<Asset>()

    init {
        assetRepo.findAll().toCollection(allAssets);
        println("Fetching from db .. $allAssets")
        println("----- $allAssets")
    }


    override fun isInMemory(): Boolean {
        return false
    }

    override fun size(query: Query<Asset, Any>?): Int {
        return allAssets.size
    }

    override fun fetch(query: Query<Asset, Any>?): Stream<Asset> {
        //this.refreshAll()
        query?.limit
        query?.pageSize

        assetRepo.findAll().toCollection(allAssets);
        return allAssets.stream();
    }

}

@Component
@PermitAll
@Route(value = "dashboard", layout = MainLayout::class)
@PageTitle("Dashboard | BorrowApp")
@PreserveOnRefresh
class DashboardView(@Autowired val assetRepo: AssetRepo, @Autowired val userRepo: UserRepo) : VerticalLayout() {

    private lateinit var dataProvider: ListDataProvider<Asset>

    private val allAssets = mutableListOf<Asset>()
    private lateinit var assetGrid: Grid<Asset>
    private lateinit var refreshDataButton: Button
    private lateinit var addAssetButton: Button
    private lateinit var addAssetDialog: Dialog

    val title = TextField()
    val authors = TextField()
    val comments = TextField()
    val tags = TextField()

    // The main view UI definition
    init {


        verticalLayout() {

            horizontalLayout {
                addAssetButton = button("", VaadinIcon.PLUS_SQUARE_O.create()) {
                    addClickShortcut(Key.ENTER)
                }

                refreshDataButton = button("", VaadinIcon.REFRESH.create()) {}

            }
            // Use Button for a clickable button
            assetRepo.findAll().toCollection(allAssets);
            dataProvider = DataProvider.ofCollection(allAssets)

            assetGrid = grid(Asset::class, dataProvider) {
                width = "80%"
                columnFor<Asset, String>(Asset::name) {
                    setHeader("Name")
                }
                columnFor<Asset, AssetType>(Asset::type) {
                    setHeader("Type")
                }
                columnFor<Asset, String>(Asset::authors) {
                    setHeader("Author(s)")
                }
                columnFor<Asset, String>(Asset::comments) {
                    setHeader("Comments")
                }
                columnFor<Asset, User>("owner", true, { it?.userName }) {
                    setHeader("Owner")

                }
            }

            addAssetDialog = dialog() {
                val d = this
                width = "80%"



                formLayout {
                    val layout = this

                    var authorItem: FormLayout.FormItem? = null
                    var assetType: ComboBox<String>? = null

                    formItem("Type") {
                        assetType = comboBox<String> {
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



                    formItem("Title") { add(title) }
                    authorItem = layout.formItem("Author(s)") { add(authors) }
                    authorItem.isVisible = false



                    formItem("Tags") { add(tags) }
                    formItem("Comments") { add(comments) }

                    formItem {
                        horizontalLayout {
                            button("OK").onLeftClick {


                                var userDetailsObj =
                                    SecurityContextHolder.getContext()?.authentication?.principal as org.springframework.security.core.userdetails.User
                                var userObj = RequestContextHolder.getRequestAttributes()
                                    ?.getAttribute("logged_user", RequestAttributes.SCOPE_SESSION)

                                println("user is $userDetailsObj, obj= $userObj")

                                userObj = userObj ?: userRepo.findById(userDetailsObj.username)


                                assetRepo.save(
                                    Asset(
                                        UUID.randomUUID(),
                                        title.value,
                                        tags.value,
                                        authors.value,
                                        comments.value,
                                        if (assetType?.value == "Book") {
                                            AssetType.Book
                                        } else AssetType.Gadget,
                                        userObj as User
                                    )
                                )
                                Notification.show("Asset added OK.")
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


        // attach functionality to the UI components.
        // It's a good practice to keep UI functionality separated from UI definition.

        // Button click listeners can be defined as lambda expressions
        addAssetButton.addClickListener {
            addAssetDialog.open()
        }

        refreshDataButton.addClickListener {
            allAssets.clear()
            assetRepo.findAll().toCollection(allAssets)
            assetGrid.refresh()
        }

    }
}


