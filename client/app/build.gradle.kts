plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt") // Ensure KAPT is applied
    id("dagger.hilt.android.plugin") // Hilt Plugin applied
}

android {
    namespace = "com.example.houserental"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.houserental"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)  // Make sure only this is included
    implementation(libs.androidx.navigation.runtime.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material-icons-extended")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // ✅ Only include one version of navigation-compose
    // You had both 2.7.7 and 2.6.0 — kept 2.7.7 (latest)
    // implementation("androidx.navigation:navigation-compose:2.6.0") — Removed

    // Retrofit & networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")


    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Activity result APIs
    implementation("androidx.activity:activity-ktx:1.7.2")



    implementation("com.google.accompanist:accompanist-permissions:0.31.3-beta")

    // Compose Foundation & Material
    implementation("androidx.compose.foundation:foundation:1.5.0")
<<<<<<< HEAD
    implementation("androidx.compose.material3:material3:1.2.0") // Keep this only
=======

    implementation("androidx.compose.material3:material3:1.2.0")
>>>>>>> f06f22104fc1ddf338cb88285e2f36e665e28718

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.0")
    implementation("com.google.accompanist:accompanist-flowlayout:0.32.0")
}
