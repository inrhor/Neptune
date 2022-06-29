package cn.inrhor.neptune.api

import cn.inrhor.neptune.Neptune
import cn.inrhor.neptune.core.manager.HumanManager
import cn.inrhor.neptune.server.PluginLoader.settings
import fr.xephi.authme.api.v3.AuthMeApi
import ink.ptms.adyeshach.api.AdyeshachAPI
import ink.ptms.adyeshach.api.Hologram
import ink.ptms.adyeshach.common.entity.EntityTypes
import ink.ptms.adyeshach.common.entity.type.AdyHuman
import org.bukkit.entity.Player
import taboolib.common.platform.function.console
import taboolib.common.platform.function.submit
import taboolib.common.util.Location
import taboolib.module.chat.colored
import taboolib.module.lang.asLangText
import taboolib.platform.util.toBukkitLocation

/**
 * 人物生成器
 */
class EntitySpawner(val player: Player) {

    var adyHuman: AdyHuman? = null

    var hologram: Hologram<*>? = null

    var contentLogin = console().asLangText("ENTER-PASSWORD")

    fun spawn(playerLocation: Location, distance: Double = 0.0, humanType: HumanManager.HumanType = HumanManager.HumanType.NONE) {
        val loc = Location(playerLocation.world, playerLocation.x, playerLocation.y, playerLocation.z)
        val refLoc = loc
            .referTo(loc.yaw, 0F,
                distance,
                0.0)
            .toBukkitLocation()
        if (adyHuman != null) {
            adyHuman?.teleport(refLoc)
        }else {
            adyHuman = AdyeshachAPI.getEntityManagerPrivate(player).create(EntityTypes.PLAYER, refLoc) as AdyHuman
        }
        val name = player.name
        adyHuman?.setName(name)
        adyHuman?.setTexture(name)

        if (humanType == HumanManager.HumanType.LOGIN) {

            var m = "_"
            createHologram(refLoc.clone().add(0.0, 2.0, 0.0), mutableListOf("${contentLogin}$m".colored()))

            var float = 0F
            submit(async = true, period = settings.login.speed) {
                if (!player.isOnline) {
                    remove()
                    cancel(); return@submit
                }
                if (adyHuman == null) {
                    cancel(); return@submit
                }
                float += 10F
                adyHuman?.controllerLook(float, 0F)
            }
            submit(async = true, period = 5) {
                if (!player.isOnline) {
                    cancel(); return@submit
                }
                m = if (m == "_") " " else "_"
                updateHologram(mutableListOf("${contentLogin}$m".colored()))
            }
            submit(period = 5) {
                if (AuthMeApi.getInstance().isAuthenticated(player)) {
                    cancel(); return@submit
                }
                if (player.location != loc) {
                    player.teleport(loc.toBukkitLocation())
                }
            }
        }
    }

    fun remove() {
        adyHuman?.delete()
        hologram?.delete()
        HumanManager.humanList.remove(player)
    }

    fun createHologram(location: org.bukkit.Location, list: MutableList<String>) {
        hologram = AdyeshachAPI.createHologram(player, location, list)
    }

    fun updateHologram(list: MutableList<String>) {
        hologram?.update(list)
    }

}