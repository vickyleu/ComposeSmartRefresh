package com.loren.component.view.composesmartrefresh

import androidx.compose.ui.text.intl.Locale
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.dateWithTimeIntervalSince1970

/**
 * Created by Loren on 2022/6/13
 * Description -> 支持下拉刷新&加载更多的通用组件
 * [state] 刷新以及加载的状态
 * [onRefresh] 刷新的回调
 * [onLoadMore] 加载更多的回调
 * [headerIndicator] 头布局
 * [footerIndicator] 尾布局
 * [contentScrollState] 当内容布局可滚动时，传入该布局的滚动状态，可以控制滚动，加载更多成功时仅隐藏尾布局，新内容直接显示
 * [content] 内容布局
 */

actual class SimpleDateFormatImpl actual constructor(
    format: String,
    locale: Locale
) {
    private val dateFormatter: NSDateFormatter = NSDateFormatter().apply {
        dateFormat = format
        this.locale = NSLocale(locale.toString())
    }

    actual fun format(timeMs: Long): String {
        val date = NSDate.dateWithTimeIntervalSince1970(timeMs / 1000.0)
        return dateFormatter.stringFromDate(date)
    }
}