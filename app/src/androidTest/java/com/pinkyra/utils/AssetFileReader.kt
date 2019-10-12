package com.pinkyra.utils

import androidx.test.platform.app.InstrumentationRegistry

class AssetFileReader {
    fun readFile(path: String): String =
        InstrumentationRegistry.getInstrumentation().context.assets.open(path).bufferedReader().use { it.readText() }
}