buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}