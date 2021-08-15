package com.mineinabyss.bundles.config

import com.mineinabyss.bundles.bundlesPlugin
import com.mineinabyss.idofront.config.IdofrontConfig
import com.mineinabyss.idofront.config.ReloadScope
import com.mineinabyss.idofront.recpies.register
import com.mineinabyss.idofront.serialization.SerializableRecipe
import kotlinx.serialization.Serializable

object BundlesConfig : IdofrontConfig<BundlesConfig.Data>(bundlesPlugin, Data.serializer()) {
    @Serializable
    data class Data(
        var bundleRecipe: SerializableRecipe,
        var discoverOnJoin: Boolean = true
    )

    override fun ReloadScope.load() {
        "Registering bundle recipe" {
            data.bundleRecipe.toCraftingRecipe().register()
        }
    }
}