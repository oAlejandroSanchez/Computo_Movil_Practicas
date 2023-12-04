plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "edu.unam.facultad_de_ingenieria.got_api"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.unam.facultad_de_ingenieria.got_api"
        minSdk = 23
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    val retrofitVersion = "2.9.0"
    val glideVersion = "4.15.1"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Retrofit y Gson
    implementation ("com.squareup.retrofit2:retrofit:retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //Glide (para imágenes)
    implementation ("com.github.bumptech.glide:glide:$glideVersion")

    //Imágenes con bordes redondeados
    implementation ("com.makeramen:roundedimageview:2.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}