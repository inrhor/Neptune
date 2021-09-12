package cn.inrhor.neptune.command

import cn.inrhor.neptune.Neptune
import taboolib.common.platform.*
import taboolib.common.platform.command.*
import taboolib.module.lang.sendLang

object ReloadCommand {

    val reload = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            Neptune.config.reload()
            sender.sendLang("COMMAND-SUCCESSFUL-RELOAD")
        }
    }
}