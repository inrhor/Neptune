package cn.inrhor.neptune.core.login

import cn.inrhor.neptune.core.manager.HumanManager
import cn.inrhor.neptune.server.PluginLoader.settings
import fr.xephi.authme.api.v3.AuthMeApi
import fr.xephi.authme.events.LoginEvent
import org.bukkit.event.player.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.OptionalEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.asLangText

/**
 * 登录模块
 */
object LoginHandle {

    private val passMap = mutableMapOf<String, String>()

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun join(ev: PlayerJoinEvent) {
        if (settings.login.enable) {
            val p = ev.player
            p.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, Int.MAX_VALUE, 1))
            p.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, Int.MAX_VALUE, 1))
            HumanManager.spawnLogin(p)
        }
    }

    @SubscribeEvent
    fun chat(ev: AsyncPlayerChatEvent) {
        if (settings.login.enable) {
            val p = ev.player
            if (AuthMeApi.getInstance().isAuthenticated(p)) return
            val password = ev.message
            val spawner = HumanManager.humanList[p] ?: return
            passMap[p.name] = password
            spawner.contentLogin = p.asLangText("CONFIRM-PASSWORD", password)
        }
    }

    @SubscribeEvent(bind = "fr.xephi.authme.events.LoginEvent")
    fun login(op: OptionalEvent) {
        if (settings.login.enable) {
            val ev = op.get<LoginEvent>()
            val p = ev.player
            HumanManager.remove(p)
            p.removePotionEffect(PotionEffectType.BLINDNESS)
            p.removePotionEffect(PotionEffectType.INVISIBILITY)
        }
    }

    @SubscribeEvent
    fun quit(ev: PlayerQuitEvent) {
        passMap.remove(ev.player.name)
        HumanManager.remove(ev.player)
    }

    @SubscribeEvent
    fun click(ev: PlayerInteractEvent) {
        val p = ev.player
        val am = AuthMeApi.getInstance()
        if (am.isAuthenticated(p)) return
        val spawner = HumanManager.humanList[p]?: return
        val name = p.name
        val password = passMap[name]?: return
        if (am.isRegistered(name)) {
            if (am.checkPassword(name, password)) {
                am.forceLogin(p)
                return
            }
            spawner.contentLogin = p.asLangText("WRONG-PASSWORD")
            return
        }
        am.forceRegister(p, password)
        passMap.remove(name)
    }

}