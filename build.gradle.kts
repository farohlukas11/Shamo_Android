// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
    }
}
allprojects {
    extra.apply {
        set("kotlin_version", "1.8.21")
        set("appcompat_version", "1.1.0")
        set("core_ktx_version", "1.3.2")
        set("constraint_version", "2.0.4")
        set("legacy_support_version", "1.0.0")
        set("junit_version", "4.13.1")
        set("androidx_junit_version", "1.1.2")
        set("espresso_version", "3.3.0")
        set("multidex_version", "2.0.1")
//        set("activity_ktx_version", "1.1.0")
//        set("fragment_ktx_version", "1.2.5")

        set("cardview_version", "1.0.0")
        set("recyclerview_version", "1.3.1")
        set("material_version", "1.2.1")
        set("glide_version", "4.11.0")

        set("room_version", "2.5.2")

        set("retrofit_version", "2.9.0")
        set("logging_interceptor_version", "4.8.0")

        set("lifecycle_ktx_version", "2.6.1")

        set("rxjava_version", "2.2.19")
        set("rxandroid_version", "2.1.1")
        set("lifecycle_version", "2.2.0")

        set("data_store_version", "1.0.0")

//    ext.dagger_version = "2.29.1"
        set("activity_ktx_version", "1.7.2")
        set("fragment_ktx_version", "1.6.1")

        set("hilt_version", "2.47")
    }
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
}