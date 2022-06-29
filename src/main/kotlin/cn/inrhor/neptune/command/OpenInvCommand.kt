package cn.inrhor.neptune.command

import cn.inrhor.neptune.core.inventory.InvHandle
import taboolib.common.platform.*
import taboolib.common.platform.command.*

object OpenInvCommand {

    val open = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            InvHandle.open(sender.cast())
        }
    }
}