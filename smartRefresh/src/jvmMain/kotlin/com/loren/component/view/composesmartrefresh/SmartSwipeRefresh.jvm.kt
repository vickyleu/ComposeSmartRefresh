package com.loren.component.view.composesmartrefresh

import androidx.compose.ui.text.intl.Locale
import java.util.Locale as JvmLocale

private fun androidxLocaleToJvmLocale(androidxLocale: Locale): JvmLocale {
    return JvmLocale(androidxLocale.language, androidxLocale.region)
}

actual class SimpleDateFormatImpl actual constructor(
    format: String,
    locale: Locale
) : java.text.SimpleDateFormat(format, androidxLocaleToJvmLocale(locale)) {
    actual fun format(timeMs: Long): String {
        return super.format(timeMs)
    }
}