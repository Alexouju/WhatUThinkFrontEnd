plugins {
    id("com.android.application")
}

android {
    namespace = "com.pyproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pyproject"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("com.android.volley:volley:1.2.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("org.mockito:mockito-android:4.3.1")
    androidTestImplementation("androidx.test.ext:truth:1.5.0")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test:rules:1.0.2")
    androidTestImplementation("org.hamcrest:hamcrest-library:1.3")

// Intents support for Espresso
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")

// Optional: Add this if you want to use MockWebServer for instrumented tests
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")

// Note: Mockito core dependency if you are also doing unit tests
    testImplementation("org.mockito:mockito-core:4.3.1")

}