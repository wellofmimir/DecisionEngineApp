plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.molokosoft.decisionengine"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.molokosoft.decisionengine"
        minSdk = 26
        targetSdk = 36
        versionCode = 26
        versionName = "1.26"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://api.meinedomain.de\""
            )
        }

        debug {
            isDebuggable = true

            buildConfigField(
                "String",
                "BASE_URL",
                "\"http://192.168.178.100:8080\""
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
        buildConfig = true
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Lifecycle / ViewModel
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Ktor
    implementation("io.ktor:ktor-client-core:3.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Google Play
    implementation("com.google.android.gms:play-services-appset:16.1.0")

    // Billing
    implementation("com.android.billingclient:billing:9.1.0")

    // Room
    implementation("androidx.room:room-runtime:2.8.0")
    implementation("androidx.room:room-ktx:2.8.0")
    ksp("androidx.room:room-compiler:2.8.0")

    // EncryptedSharedPreferences / MasterKey
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    //Notifications & Work
    implementation("androidx.work:work-runtime-ktx:2.11.0")

    // Tests
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}