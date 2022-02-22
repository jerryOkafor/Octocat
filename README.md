# OctoCat

Looking to start using Jetpack Compose, we have a simple template here to get you started.
Links:
https://github.com/google/secrets-gradle-plugin

## Features
* Single Activity
* Networking - Retrofit
* Dependency Injection - Hilt
* Navigation - Navigation Arch. Component + Jetpack Compose Navigation
* Accompanist - AnimatedNavHost, WindowInsets
* Kotlin Coroutine
* Splash Screen
* Unidirectional Data Flow (UDF)
* Functional Programming (Immutable UI State)


Todo:
Add 12 + Splashscreen
https://developer.android.com/guide/topics/ui/splash-screen
https://developer.android.com/reference/androidx/core/splashscreen/SplashScreen
https://developer.android.com/jetpack/androidx/releases/core
https://medium.com/bugless/undo-redo-graphics-editor-with-command-pattern-in-kotlin-5354676c2166

//Download public schema
./gradlew :app:downloadApolloSchema --endpoint='https://docs.github.com/public/schema.docs.graphql' --schema=app/src/main/graphql/com/octocat/api/schema.graphqls


Testing
https://www.apollographql.com/docs/kotlin/testing/mocking-graphql-responses/

// Forming GraphQL
https://stepzen.com/docs/using-graphql/graphql-query-basics