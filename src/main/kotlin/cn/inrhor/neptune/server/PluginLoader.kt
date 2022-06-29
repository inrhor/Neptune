package cn.inrhor.neptune.server

import cn.inrhor.neptune.Neptune
import cn.inrhor.neptune.api.setting.Settings
import cn.inrhor.neptune.core.manager.HumanManager
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.configuration.Configuration.Companion.getObject

/**
 * 运行处理
 */
object PluginLoader {

    private var reloading = false

    fun isReloading(): Boolean {
        return reloading
    }

    var settings = Settings()

    fun doReload() {
        if (reloading) return
        reloading = true
        Logger.loadInfo()
        settings = Neptune.config.getObject("")
        reloading = false
    }

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        doReload()
    }

    @Awake(LifeCycle.DISABLE)
    fun onDisable() {
        HumanManager.clear()
    }

}