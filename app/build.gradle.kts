plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.capstone.mageiras"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.capstone.mageiras"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "BASE_ML_URL",
            "\"http://35.226.149.200/\""
        )

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // circle image
    implementation(libs.circleImageView)
    implementation(libs.animatedBottomBar)

    //camerax
    implementation(libs.androidx.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    //glide
    implementation(libs.glide)
    annotationProcessor(libs.glideCompiler)

    //retrofit + okhttp3
    implementation(libs.retrofit)
    implementation(libs.converterGson)
    implementation(libs.loggingInterceptor)

    //room
    implementation(libs.roomKtx)
    implementation(libs.roomRuntime)
    ksp(libs.roomCompiler)

    //tensorflow
    implementation(libs.tensorflowLiteTaskVision)
    implementation(libs.tensorflowLiteMetadata)
    implementation(libs.tensorflowLiteGpu)
    implementation(libs.tensorflowLite)

    //coroutines
    implementation(libs.lifecycleViewModelKtx)
    implementation(libs.coroutinesCore)
    implementation(libs.lifecycleLivedataKtx)
    implementation(libs.coroutinesAndroid)

    implementation("com.google.android.material:material:1.3.0-alpha03")

    implementation("com.github.bumptech.glide:glide:4.16.0")

}