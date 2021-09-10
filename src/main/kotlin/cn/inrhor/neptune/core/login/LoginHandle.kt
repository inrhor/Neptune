package cn.inrhor.neptune.core.login

import cn.inrhor.neptune.core.manager.HumanManager
import fr.xephi.authme.api.v3.AuthMeApi
import fr.xephi.authme.events.LoginEvent
import org.bukkit.event.player.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.asLangText

object LoginHandle {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun join(ev: PlayerJoinEvent) {
        val p = ev.player
        p.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, Int.MAX_VALUE, 1))
        p.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, Int.MAX_VALUE, 1))
        HumanManager.spawn(p)
    }

    @SubscribeEvent
    fun chat(ev: AsyncPlayerChatEvent) {
        val p = ev.player
        if (AuthMeApi.getInstance().isAuthenticated(p)) return
        val password = ev.message
        val spawner = HumanManager.humanList[p]?: return
        spawner.password = password
        spawner.content = "&6&l点击确认密码： &f$password&l"
    }

    @SubscribeEvent
    fun login(ev: LoginEvent) {
        val p = ev.player
        HumanManager.remove(p)
        p.removePotionEffect(PotionEffectType.BLINDNESS)
        p.removePotionEffect(PotionEffectType.INVISIBILITY)
    }

    @SubscribeEvent
    fun quit(ev: PlayerQuitEvent) {
        HumanManager.remove(ev.player)
    }

    @SubscribeEvent
    fun click(ev: PlayerInteractEvent) {
        val p = ev.player
        val am = AuthMeApi.getInstance()
        if (am.isAuthenticated(p)) return
        val spawner = HumanManager.humanList[p]?: return
        val password = spawner.password
        val name = p.name
        if (am.isRegistered(name)) {
            if (am.checkPassword(name, password)) {
                am.forceLogin(p)
                return
            }
            spawner.content = p.asLangText("WRONG-PASSWORD")
            return
        }
        am.forceRegister(p, password)
    }

}