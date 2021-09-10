package cn.inrhor.neptune.server

import cn.inrhor.neptune.core.manager.HumanManager
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

/**
 * 运行操作
 */
object PluginLoader {

    /*private var reloading = false

    fun isReloading(): Boolean {
        return reloading
    }*/

    fun doReload() {
//        reloading = true
        Logger.loadInfo()
//        reloading = false
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