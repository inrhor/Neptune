package cn.inrhor.neptune.command

import taboolib.common.platform.command.*


@CommandHeader("neptune")
internal object Command {

    @CommandBody(permission = "neptune.player.use")
    val open = OpenInvCommand.open

    @CommandBody(permission = "neptune.admin.reload")
    val reload = ReloadCommand.reload

}