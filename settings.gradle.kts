pluginManagement {
    repositories {


        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven { setUrl("https://maven.vaadin.com/vaadin-prereleases") }
        gradlePluginPortal()
    }
}
