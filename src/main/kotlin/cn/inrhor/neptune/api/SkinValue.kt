package cn.inrhor.neptune.api

import com.google.gson.JsonParser
import java.io.InputStreamReader
import java.net.URL

class SkinValue(val name: String) {

    var texture: String? = null
    var signature: String? = null

    init {
        val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
        val reader = InputStreamReader(url.openStream())
        val uuid = JsonParser().parse(reader).asJsonObject.get("id").asString
        val uo = URL("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false")
        val ro = InputStreamReader(uo.openStream())
        val textureProperty = JsonParser().parse(ro).asJsonObject.get("properties").asJsonArray.get(0).asJsonObject
        texture = textureProperty["value"].asString
        signature = textureProperty["signature"].asString
    }

}