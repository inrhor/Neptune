package cn.inrhor.neptune.core.inventory

import cn.inrhor.neptune.core.manager.HumanManager
import org.bukkit.entity.Player

/**
 * 人物库存模块
 */
object InvHandle {

    fun open(player: Player) {
        HumanManager.spawn(player)
    }

    fun displayItem(player: Player) {

    }

}