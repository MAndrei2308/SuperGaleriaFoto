plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.supergaleriafoto"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.supergaleriafoto"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Listare imagini în grid
    implementation(libs.recyclerview)

    // Swipe între poze în fullscreen
    implementation(libs.viewpager2)

    // Navigation component (single-activity, multiple fragments)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Glide – încărcare imagini + caching
    implementation(libs.glide)
    implementation(libs.fragment)
    annotationProcessor(libs.glide.compiler)

    // PhotoView – pinch-to-zoom pe imagini
    implementation(libs.photoview)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}