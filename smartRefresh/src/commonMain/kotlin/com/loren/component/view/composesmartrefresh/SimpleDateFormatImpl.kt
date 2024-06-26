package com.loren.component.view.composesmartrefresh

import androidx.compose.ui.text.intl.Locale

expect class SimpleDateFormatImpl(format: String, locale: Locale) {
    fun format(timeMs:Long):String
}