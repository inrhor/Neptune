package cn.inrhor.neptune.server

import taboolib.common.platform.Platform
import taboolib.common.platform.function.pluginVersion
import taboolib.module.metrics.Metrics

object Metrics {

    private val bStats by lazy {
        Metrics(12738, pluginVersion, Platform.BUKKIT)
    }

}