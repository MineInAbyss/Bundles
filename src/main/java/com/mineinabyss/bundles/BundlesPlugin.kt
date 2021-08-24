package com.mineinabyss.bundles

import com.mineinabyss.bundles.listeners.BundleListener
import com.mineinabyss.idofront.plugin.registerEvents
import org.bukkit.plugin.java.JavaPlugin

val bundlesPlugin: BundlesPlugin by lazy { JavaPlugin.getPlugin(BundlesPlugin::class.java) }

class BundlesPlugin : JavaPlugin() {
    override fun onEnable() {
        registerEvents(
            BundleListener
        )
    }
}