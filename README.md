[![Powered By Vaadin on Kotlin](http://vaadinonkotlin.eu/iconography/vok_badge.svg)](http://vaadinonkotlin.eu)

# Borrow 
## An app to share your books and other things with your friends in your community

This app is built using [vaadin](https://vaadin.com/blog/meet-vaadin-21) and [kotlin](https://kotlinlang.org/) using the [karibu](https://github.com/mvysny/karibu10-helloworld-application) DSL


# Preparing Environment

Java 8 (or higher) JDK is required.

Vaadin apps also require nodejs and pnpm, but these tools are installed automatically for you
when you build the app for the first time via Gradle.
Alternatively, you can install nodejs and npm to your OS:

* Windows: [node.js Download site](https://nodejs.org/en/download/) - use the .msi 64-bit installer
* Linux: `sudo apt install npm`

## Getting Started

To quickly start the app, just type this into your terminal:

```bash
git clone https://github.com/rkbalgi/borrow
cd borrow
./gradlew appRun
```

Gradle will automatically download an embedded servlet container (Jetty) and will run your app in it. Your app will be running on
[http://localhost:8080](http://localhost:8080).

> This is based of a karibu-hello-world sample app [here](https://github.com/mvysny/karibu10-helloworld-application)

## Supported Modes

Runs in Vaadin npm mode, using the [Vaadin Gradle Plugin](https://github.com/vaadin/vaadin-gradle-plugin).

Both the [development and production modes](https://vaadin.com/docs/v14/guide/production) are supported.
To prepare for development mode, just run:

```bash
./gradlew clean vaadinPrepareFrontend
```

To build in production mode, just run:

```bash
./gradlew clean build -Pvaadin.productionMode
```

Vaadin will download nodejs and npm/pnpm automatically for you (handy for CI).

# Workflow

To compile the entire project in production mode, run `./gradlew -Pvaadin.productionMode`.

To run the application in development mode, run `./gradlew appRun` and open [http://localhost:8080/](http://localhost:8080/).

To produce a deployable production-mode WAR:
- run `./gradlew -Pvaadin.productionMode`
- You will find the WAR file in `build/libs/*.war`
- To revert your environment back to development mode, just run `./gradlew` or `./gradlew vaadinPrepareFrontend`
  (omit the `-Pvaadin.productionMode`) switch.

This will allow you to quickly start the example app and allow you to do some basic modifications.


# Docker

Use the following `Dockerfile` to both build the app, and to create a final production container:

```dockerfile
# Build stage
FROM openjdk:11 AS BUILD
RUN git clone https://github.com/mvysny/karibu10-helloworld-application /app
WORKDIR app
RUN ./gradlew -Pvaadin.productionMode

# Run stage
FROM tomcat:9.0.35-jdk11-openjdk
COPY --from=BUILD /app/build/libs/app.war /usr/local/tomcat/webapps/ROOT.war
```

To build the docker image:
```bash
docker build -t mvy/karibu10:latest .
```

To run the app:
```bash
docker run --rm -ti -p 8080:8080 mvy/karibu10
```

Now open [http://localhost:8080/](http://localhost:8080/).
