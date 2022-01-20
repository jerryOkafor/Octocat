plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.jerryhanks.compose"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        useLibrary("org.apache.http.legacy")

        javaCompileOptions {
//            annotationProcessorOptions {
//                arguments += mapOf(
//                    "room.schemaLocation" to "$projectDir/schemas",
//                    "room.incremental" to "true",
//                    "room.expandProjection" to "true"
//                )
//            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".dev"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
//        useIR = true
        freeCompilerArgs = listOf("-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
        freeCompilerArgs = freeCompilerArgs + "-Xallow-unstable-dependencies"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=coil.annotation.ExperimentalCoilApi"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.Experimental"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-rc02"
    }

    sourceSets {
        all {
            java.srcDir("src/main/proto")
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")

    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    //Firebase
//    implementation(platform("com.google.firebase:firebase-bom:28.4.0"))
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-messaging-ktx")
//    implementation("com.google.firebase:firebase-messaging-directboot")

    //Compose
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.compose.material:material:1.2.0-alpha01")
    implementation("androidx.compose.animation:animation:1.2.0-alpha01")
    implementation("androidx.compose.ui:ui-tooling:1.2.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")

    //Datastore
//    implementation("androidx.datastore:datastore:1.0.0")
//    implementation("com.google.protobuf:protobuf-javalite:4.0.0-rc-2")
//    implementation("androidx.datastore:datastore-preferences:1.0.0-rc01")

    //WorkManager
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    androidTestImplementation("androidx.work:work-testing:2.7.1")

    //Accompanists
    //https://search.maven.org/search?q=g:com.google.accompanist
    implementation("com.google.accompanist:accompanist-navigation-animation:0.22.0-rc")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.22.0-rc")
    implementation("com.google.accompanist:accompanist-insets:0.22.0-rc")
//    implementation("com.google.accompanist:accompanist-placeholder:0.22.0-rc")
//    implementation("com.google.accompanist:accompanist-flowlayout:0.22.0-rc")
//    implementation("com.google.accompanist:accompanist-insets:0.22.0-rc")
//    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")
//    implementation("com.google.accompanist:accompanist-swiperefresh:0.22.0-rc")

    //Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation("com.google.android.gms:play-services-location:19.0.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-service:2.4.0")

    // Coil
    implementation("io.coil-kt:coil:1.4.0")
    implementation("io.coil-kt:coil-compose:1.4.0")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-rc01")

    // For instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.38.1")

    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    kaptTest("com.google.dagger:hilt-android-compiler:2.38.1")

    //Hilt WorkerManager
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //Room
//    implementation("androidx.room:room-runtime:2.3.0")
//    kapt("androidx.room:room-compiler:2.3.0")
//    implementation("androidx.room:room-ktx:2.3.0")
//    testImplementation("androidx.room:room-testing:2.3.0")
//    debugImplementation("com.amitshekhar.android:debug-db:1.0.6")

    //Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // testing
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")

    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.4-alpha03")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.5")
}