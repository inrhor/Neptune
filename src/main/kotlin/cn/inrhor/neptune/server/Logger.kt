package cn.inrhor.neptune.server

import taboolib.common.platform.function.info

/**
 * 信息输出
 */
object Logger {

    private fun logoSend() {
        info("\n" +
                " _   _            _                    \n" +
                "| \\ | |          | |                   \n" +
                "|  \\| | ___ _ __ | |_ _   _ _ __   ___ \n" +
                "| . ` |/ _ \\ '_ \\| __| | | | '_ \\ / _ \\\n" +
                "| |\\  |  __/ |_) | |_| |_| | | | |  __/\n" +
                "\\_| \\_/\\___| .__/ \\__|\\__,_|_| |_|\\___|\n" +
                "           | |                         \n" +
                "           |_|                         \n")
    }

    fun loadInfo() {
        logoSend()
    }

}