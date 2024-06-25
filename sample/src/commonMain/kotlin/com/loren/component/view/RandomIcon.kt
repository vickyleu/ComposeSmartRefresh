package com.loren.component.view

import composesmartrefresh.sample.generated.resources.*
import kotlin.random.Random

/**
 * Created by Loren on 2022/4/6
 * Description -> 随机icon工具类
 */
object RandomIcon {
    private val icons = arrayOf(
        Res.drawable.ic_baitu,
        Res.drawable.ic_bianfu,
        Res.drawable.ic_bianselong,
        Res.drawable.ic_daishu,
        Res.drawable.ic_daxiang,
        Res.drawable.ic_eyu,
        Res.drawable.ic_gongji,
        Res.drawable.ic_gou,
        Res.drawable.ic_hainiu,
        Res.drawable.ic_haiyaoyu,
        Res.drawable.ic_hashiqi,
        Res.drawable.ic_hema,
        Res.drawable.ic_houzi,
        Res.drawable.ic_huanxiong,
        Res.drawable.ic_hudie,
        Res.drawable.ic_jiakechong,
        Res.drawable.ic_jingangyingwu,
        Res.drawable.ic_jingyu
    )

    fun icon() = icons[Random.nextInt(0, icons.size - 1)]
}