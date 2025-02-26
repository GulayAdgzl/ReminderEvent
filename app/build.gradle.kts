plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.glyadgzl.reminder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.glyadgzl.reminder"
        minSdk = 24
        targetSdk = 34
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
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // work manager
    implementation("androidx.work:work-runtime-ktx:2.10.0")

    // coroutines
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$rootProject.lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$rootProject.lifecycleVersion")
    // Lifecycle utilities for Compose
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:$rootProject.lifecycleVersion")

    implementation ("androidx.fragment:fragment-ktx:1.8.6")

    implementation ("androidx.compose.material:material:1.7.8")
}