package cn.inrhor.neptune.command

import cn.inrhor.neptune.server.PluginLoader
import taboolib.common.platform.*
import taboolib.common.platform.command.*
import taboolib.module.lang.sendLang

object ReloadCommand {

    val reload = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            PluginLoader.doReload()
            sender.sendLang("COMMAND-SUCCESSFUL-RELOAD")
        }
    }
}