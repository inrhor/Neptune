package cn.inrhor.neptune.core.manager

import cn.inrhor.neptune.api.EntitySpawner
import cn.inrhor.neptune.server.PluginLoader.settings
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.platform.util.toProxyLocation

/**
 * 人物管理器
 */
object HumanManager {

    val humanList = mutableMapOf<Player, EntitySpawner>()

    fun clear() {
        humanList.values.forEach {
            it.remove()
        }
    }

    fun spawnLogin(player: Player) {
        submit(delay = 12L, async = true) {
            if (!player.isOnline) return@submit
            spawn(player, settings.login.distance, HumanType.LOGIN)
        }
    }

    fun spawn(player: Player, distance: Double = 0.0, humanType: HumanType = HumanType.NONE) {
        val spawner = EntitySpawner(player)
        spawner.spawn(player.location.toProxyLocation(), distance, humanType)
        humanList[player] = spawner
    }

    fun remove(player: Player) {
        humanList[player]?.remove()
    }

    fun exist(player: Player, remove: Boolean = false): Boolean {
        if (humanList.containsKey(player)) {
            if (remove) humanList[player]?.remove()
            return true
        }
        return false
    }

    enum class HumanType {
        NONE, LOGIN
    }

}