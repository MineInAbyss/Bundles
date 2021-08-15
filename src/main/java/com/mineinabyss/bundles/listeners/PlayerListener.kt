package com.mineinabyss.bundles.listeners

import com.mineinabyss.bundles.config.BundlesConfig
import com.mineinabyss.idofront.util.toMCKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerListener: Listener {
    @EventHandler
    fun PlayerJoinEvent.join() {
        if(BundlesConfig.data.discoverOnJoin) player.discoverRecipe(BundlesConfig.data.bundleRecipe.key.toMCKey())
    }
}