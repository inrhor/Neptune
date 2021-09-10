package cn.inrhor.neptune.core.manager

import cn.inrhor.neptune.api.EntitySpawner
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit

object HumanManager {

    val humanList = mutableMapOf<Player, EntitySpawner>()

    fun clear() {
        humanList.values.forEach {
            it.remove()
        }
    }

    fun spawn(player: Player) {
        submit(delay = 40, async = true) {
            if (!player.isOnline) return@submit
            val spawner = EntitySpawner(player)
            spawner.spawn()
            humanList[player] = spawner
        }
    }

    fun remove(player: Player) {
        humanList[player]?.remove()
    }

}