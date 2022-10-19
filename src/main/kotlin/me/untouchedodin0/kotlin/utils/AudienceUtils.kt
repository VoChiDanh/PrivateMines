package me.untouchedodin0.kotlin.utils

import me.clip.placeholderapi.PlaceholderAPI
import me.untouchedodin0.privatemines.PrivateMines
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class AudienceUtils {

    var privateMines: PrivateMines = PrivateMines.getPrivateMines()

    fun sendMessage(player: Player, message: String) {
        val miniMessage = MiniMessage.miniMessage()
        val audiences = privateMines.adventure
        val placeholderAPI = PlaceholderAPI.setPlaceholders(player, message)
        val parsed = miniMessage.deserialize(placeholderAPI)
        val audience = audiences.player(player)

        audience.sendMessage(parsed)
    }

    fun sendMessage(player: Player, target: Player, message: String) {
        val miniMessage = MiniMessage.miniMessage()
        val audiences = privateMines.adventure
        val placeholderAPI = PlaceholderAPI.setPlaceholders(target, message)
        val parsed = miniMessage.deserialize(placeholderAPI)
        val audience = audiences.player(player)
        audience.sendMessage(parsed)
    }

    fun sendMessage(player: Player, target: OfflinePlayer, message: String) {
        val miniMessage = MiniMessage.miniMessage()
        val audiences = privateMines.adventure
        val placeholderAPI = PlaceholderAPI.setPlaceholders(target, message)
        val parsed = miniMessage.deserialize(placeholderAPI)
        val audience = audiences.player(player)
        audience.sendMessage(parsed)
    }

    fun sendMessage(player: Player, message: String, int: Int) {
        val miniMessage = MiniMessage.miniMessage()
        val audiences = privateMines.adventure
        val parsed = miniMessage.deserialize(message.replace("{amount}", int.toString()))
        val audience = audiences.player(player)
        audience.sendMessage(parsed)
    }
}