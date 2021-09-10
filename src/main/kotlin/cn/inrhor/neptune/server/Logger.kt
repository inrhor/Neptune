package cn.inrhor.neptune.server

import cn.inrhor.neptune.Neptune
import taboolib.common.platform.function.console

/**
 * 信息输出
 */
object Logger {

    private fun logoSend() {
        listOf("\n" +
                " _   _            _                    \n" +
                "| \\ | |          | |                   \n" +
                "|  \\| | ___ _ __ | |_ _   _ _ __   ___ \n" +
                "| . ` |/ _ \\ '_ \\| __| | | | '_ \\ / _ \\\n" +
                "| |\\  |  __/ |_) | |_| |_| | | | |  __/\n" +
                "\\_| \\_/\\___| .__/ \\__|\\__,_|_| |_|\\___|\n" +
                "           | |                         \n" +
                "           |_|                         \n").forEach {
            console().sendMessage(it)
        }
    }

    fun loadInfo() {
        val pluginCon = Neptune.plugin.description
        logoSend()
    }

}