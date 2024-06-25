plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()
    jvm("desktop")

    sourceSets {
        commonMain.get().dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)
            implementation(projects.smartRefresh)
            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(libs.kotlinx.datetime)
        }

        val desktopMain by getting{
            dependencies{
                implementation(compose.desktop.currentOs)
            }
        }

    }
}

android {
    namespace = "com.loren.component.view.sample"

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].apply {
        manifest {
            srcFile("src/androidMain/AndroidManifest.xml")
        }
        res {
            srcDirs("src/androidMain/res")
        }
    }
    defaultConfig {
        applicationId = "com.loren.component.view.sample"
        minSdk = libs.versions.android.minSdk.get().toInt()
        version = "1.0"
        versionCode = 1
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunne"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
    lint {
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    configurations.all {
        resolutionStrategy {
            force("org.slf4j:slf4j-api:1.7.36")
        }
    }
    dependencies {
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        implementation(project.dependencies.platform(libs.compose.bom))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.material3:material3")


        implementation("androidx.activity:activity-compose:1.8.0")
        implementation("androidx.compose.runtime:runtime-livedata:1.1.1")



    }
}