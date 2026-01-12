plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm {
        mainRun {
            mainClass.set("org.example.MainKt")
        }
    }
    linuxX64 {
        binaries {
            executable {
                entryPoint = "org.example.main"
            }
        }
    }

}
dependencies {
}
