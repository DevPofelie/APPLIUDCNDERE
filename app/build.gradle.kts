plugins {
    id("com.android.application")
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") // pour Room (génération de code)
}

android {
    namespace = "com.example.appliudcndere"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.appliudcndere"
        minSdk = 24        // Android 7.0 — large compatibilité
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-beta"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material.icons.extended)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    //androidTestImplementation(libs.androidx.compose.ui.test.junit4)
// Ajoutez cette dépendance pour activer le WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

// Implémentation pour IntentionsScreen
    implementation("androidx.browser:browser:1.8.0")

// Dépendance pour les requêtes réseau (OkHttp est idéale avec WorkManager)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

// Optionnel mais recommandé : Pour analyser le JSON proprement si vous n'utilisez pas JSONObject
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

        // ACTIVITY / NAVIGATION
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // VIEWMODEL / LIFECYCLE
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Room (SQLite local)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    }
