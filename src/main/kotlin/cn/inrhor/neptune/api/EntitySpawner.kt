package cn.inrhor.neptune.api

import cn.inrhor.neptune.core.manager.HumanManager
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

class EntitySpawner(val player: Player) {

    val manager = AdyeshachAPI.getEntityManagerPrivate(player)

    var adyHuman: AdyHuman? = null

    var hologram: Hologram<*>? = null

    var password = ""

    var content = console().asLangText("ENTER-PASSWORD")

    fun spawn() {
        val l = player.location
        val loc = Location(l.world?.name, l.x, l.y, l.z)
        val refLoc = loc
            .referTo(loc.yaw, 0F, 2.0, 0.0)
            .toBukkitLocation()
        if (adyHuman != null) {
            adyHuman?.teleport(refLoc)
        }else {
            adyHuman = manager.create(EntityTypes.PLAYER, refLoc) as AdyHuman
        }
        val name = player.name
        adyHuman?.setName(name)
        submit(async = true) {
            val skin = SkinValue(name)
            val texture = skin.texture
            val signature = skin.signature
            if (texture != null && signature != null) {
                adyHuman?.setTexture(texture, signature)
            }
        }

        var m = "_"
        createHologram(refLoc.clone().add(0.0, 2.0, 0.0), mutableListOf("${content}$m".colored()))

        var float = 0F
        submit(async = true, period = 1) {
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
        submit(async = true, period = 10) {
            if (!player.isOnline) {
                cancel(); return@submit
            }
            m = if (m == "_")  " " else "_"
            updateHologram(mutableListOf("${content}$m".colored()))
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

    fun remove() {
        password = ""
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