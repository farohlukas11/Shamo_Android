plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
}

android {
    namespace = "com.faroh.shamoandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.faroh.shamoandroid"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://shamo-backend.buildwithangga.id/api/\"")
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")

    val cardview_version = rootProject.extra["cardview_version"]
    implementation("androidx.cardview:cardview:$cardview_version")
    val recyclerview_version = rootProject.extra["recyclerview_version"]
    implementation("androidx.recyclerview:recyclerview:$recyclerview_version")
    val material_version = rootProject.extra["material_version"]
    implementation("com.google.android.material:material:$material_version")
    val glide_version = rootProject.extra["glide_version"]
    implementation("com.github.bumptech.glide:glide:$glide_version")

    val room_version = rootProject.extra["room_version"]
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")

    val retrofit_version = rootProject.extra["retrofit_version"]
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    val logging_interceptor_version = rootProject.extra["logging_interceptor_version"]
    implementation("com.squareup.okhttp3:logging-interceptor:$logging_interceptor_version")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    //noinspection LifecycleAnnotationProcessorWithJava8
    ksp("androidx.lifecycle:lifecycle-compiler:2.6.1")


    val lifecycle_ktx_version = rootProject.extra["lifecycle_ktx_version"]
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_ktx_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_ktx_version")

    val rxjava_version = rootProject.extra["rxjava_version"]
    implementation("io.reactivex.rxjava2:rxjava:$rxjava_version")
    val rxandroid_version = rootProject.extra["rxandroid_version"]
    implementation("io.reactivex.rxjava2:rxandroid:$rxandroid_version")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofit_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    val lifecycle_version = rootProject.extra["lifecycle_version"]
//    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version")
    implementation("android.arch.lifecycle:reactivestreams:1.1.1")
    implementation("android.arch.lifecycle:extensions:1.1.1")
//    implementation("io.reactivex.rxjava2:rxjava:2.2.2")
//    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")


    val data_store_version = rootProject.extra["data_store_version"]
    implementation("androidx.datastore:datastore-preferences:$data_store_version")
    implementation("androidx.datastore:datastore-preferences-rxjava2:$data_store_version")
    implementation("androidx.datastore:datastore-core:$data_store_version")

    val hilt_version = rootProject.extra["hilt_version"]
    implementation("com.google.dagger:hilt-android:$hilt_version")
    ksp("com.google.dagger:hilt-android-testing:$hilt_version")
    ksp("com.google.dagger:hilt-compiler:$hilt_version")

    //by viewModels di Activity dan Fragment
    val activity_ktx_version = rootProject.extra["activity_ktx_version"]
    implementation("androidx.activity:activity-ktx:$activity_ktx_version")
    val fragment_ktx_version = rootProject.extra["fragment_ktx_version"]
    implementation("androidx.fragment:fragment-ktx:$fragment_ktx_version")

    implementation("me.relex:circleindicator:2.1.6")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}