package com.mineinabyss.bundles

import com.mineinabyss.bundles.config.BundlesConfig
import com.mineinabyss.bundles.listeners.BundleListener
import com.mineinabyss.bundles.listeners.PlayerListener
import com.mineinabyss.idofront.plugin.registerEvents
import kotlinx.serialization.InternalSerializationApi
import org.bukkit.plugin.java.JavaPlugin

val bundlesPlugin: BundlesPlugin by lazy { JavaPlugin.getPlugin(BundlesPlugin::class.java) }

class BundlesPlugin : JavaPlugin() {
    override fun onLoad() {
    }

    @InternalSerializationApi
    override fun onEnable() {
//        LibraryLoaderInjector.inject(this)
        saveDefaultConfig()
        BundlesConfig.load()

        registerEvents(
            BundleListener,
            PlayerListener
        )
    }
}