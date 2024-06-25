package com.loren.component.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.loren.component.view.composesmartrefresh.MyRefreshFooter
import com.loren.component.view.composesmartrefresh.MyRefreshHeader
import com.loren.component.view.composesmartrefresh.SmartSwipeRefresh
import com.loren.component.view.composesmartrefresh.SmartSwipeStateFlag
import com.loren.component.view.composesmartrefresh.ThresholdScrollStrategy
import com.loren.component.view.composesmartrefresh.rememberSmartSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


class MainViewModel {

    private val viewModelScope = CoroutineScope(Job())

    private val _mainUiState: MutableSharedFlow<MainUiState?> = MutableSharedFlow()
    val mainUiState: SharedFlow<MainUiState?>
        get() = _mainUiState

    private val topics = listOf(
        TopicModel("Arts & Crafts", RandomIcon.icon()),
        TopicModel("Beauty", RandomIcon.icon()),
        TopicModel("Books", RandomIcon.icon()),
        TopicModel("Business", RandomIcon.icon()),
        TopicModel("Comics", RandomIcon.icon()),
        TopicModel("Culinary", RandomIcon.icon()),
        TopicModel("Design", RandomIcon.icon()),
        TopicModel("Writing", RandomIcon.icon()),
        TopicModel("Religion", RandomIcon.icon()),
        TopicModel("Technology", RandomIcon.icon()),
        TopicModel("Social sciences", RandomIcon.icon()),
        TopicModel("Arts & Crafts", RandomIcon.icon()),
        TopicModel("Beauty", RandomIcon.icon()),
        TopicModel("Books", RandomIcon.icon()),
        TopicModel("Business", RandomIcon.icon()),
        TopicModel("Comics", RandomIcon.icon()),
        TopicModel("Culinary", RandomIcon.icon()),
        TopicModel("Design", RandomIcon.icon()),
        TopicModel("Writing", RandomIcon.icon()),
        TopicModel("Religion", RandomIcon.icon()),
        TopicModel("Technology", RandomIcon.icon()),
        TopicModel("Social sciences", RandomIcon.icon())
    )

    private var flag = true // 模拟成功失败
    fun fillData(isRefresh: Boolean) {
        viewModelScope.launch {
            runCatching {
                delay(2000)
                if (!flag) {
                    throw Exception("error")
                }
                if (isRefresh) {
                    MainUiState(isLoadMore = false, data = topics.toMutableList().apply {
                        this[0] = this[0].copy(
                            title = Clock.System.now().toEpochMilliseconds().toString()
                        )
                    }, flag = true)
                } else {
                    MainUiState(
                        isLoadMore = true,
                        data = (_mainUiState.lastOrNull()?.data ?: mutableListOf()).apply {
                            addAll(topics)
                        }, flag = true
                    )
                }
            }.onSuccess {
                println("Loren fillData success")
                _mainUiState.emit(it)
            }.onFailure {
                _mainUiState.emit(
                    _mainUiState.lastOrNull()?.copy(isLoadMore = !isRefresh, flag = false)
                )
            }
            flag = !flag
        }
    }
}

data class TopicModel(
    val title: String,
    val icon: DrawableResource
)

data class MainUiState(
    val data: MutableList<TopicModel>? = null,
    val isLoadMore: Boolean = false,
    val flag: Boolean = true
)

@Composable
fun App() {
    val scrollState = rememberLazyListState()
    val viewModel by remember {
        mutableStateOf(MainViewModel())
    }
    val mainUiState = viewModel.mainUiState.collectAsState(null)
    val refreshState = rememberSmartSwipeRefreshState()
    // 快速滚动头尾允许的阈值
    with(LocalDensity.current) {
        refreshState.dragHeaderIndicatorStrategy = ThresholdScrollStrategy.UnLimited
        refreshState.dragFooterIndicatorStrategy =
            ThresholdScrollStrategy.Fixed(160.dp.toPx())
        refreshState.flingHeaderIndicatorStrategy = ThresholdScrollStrategy.None
        refreshState.flingFooterIndicatorStrategy =
            ThresholdScrollStrategy.Fixed(80.dp.toPx())
    }
    refreshState.needFirstRefresh = true
    Column {
        SmartSwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            onRefresh = {
                viewModel.fillData(true)
            },
            onLoadMore = {
                viewModel.fillData(false)
            },
            state = refreshState,
            headerIndicator = {
                MyRefreshHeader(refreshState.refreshFlag, true)
            },
            footerIndicator = {
                MyRefreshFooter(refreshState.loadMoreFlag, true)
            },
            contentScrollState = scrollState
        ) {

            LaunchedEffect(mainUiState.value) {
                mainUiState.value?.let {
                    if (it.isLoadMore) {
                        refreshState.loadMoreFlag = when (it.flag) {
                            true -> SmartSwipeStateFlag.SUCCESS
                            false -> SmartSwipeStateFlag.ERROR
                        }
                    } else {
                        refreshState.refreshFlag = when (it.flag) {
                            true -> SmartSwipeStateFlag.SUCCESS
                            false -> SmartSwipeStateFlag.ERROR
                        }
                    }
                }
            }

           /* CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {

            }*/
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState
            ) {
                mainUiState.value?.data?.let {
                    items(it.size) { index ->
                        val item = it[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(Color.LightGray)
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp),
                                painter = painterResource(item.icon),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = item.title)
                        }
                    }
                }
            }
        }
    }
}