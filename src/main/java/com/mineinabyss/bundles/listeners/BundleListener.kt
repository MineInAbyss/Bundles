package com.mineinabyss.bundles.listeners

import com.mineinabyss.bundles.BundleHolder
import com.mineinabyss.bundles.bundlesPlugin
import com.mineinabyss.idofront.entities.rightClicked
import com.mineinabyss.idofront.messaging.error
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType.SWAP_OFFHAND
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryAction.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.meta.BundleMeta


object BundleListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun PlayerInteractEvent.click() {
        if (hand == EquipmentSlot.OFF_HAND) return  //the event is called twice, on for each hand. We want to ignore the offhand call

        if (!rightClicked) return   //only do stuff when player rightclicks

        player.inventory.itemInMainHand.type == Material.BUNDLE || return
        isCancelled = true // Cancel the regular bundle action

        val bundleMeta = player.inventory.itemInMainHand.itemMeta as BundleMeta

        bundleMeta.items.size > 0 || return

        val bundleSize = if (bundleMeta.items.size > 54) 54 else bundleMeta.items.size
        val slots = round(bundleSize.toLong(), 9).toInt()

        val bundleInventory = Bukkit.createInventory(BundleHolder(), slots, "Bundle")
        bundleInventory.contents = bundleMeta.items.subList(0, bundleSize).toTypedArray()

        player.openInventory(bundleInventory)
    }

    private fun round(n: Long, m: Long): Long {
        return if (n >= 0) (n + m - 1) / m * m else n / m * m
    }


    private fun saveBundle(player: Player, inv: Inventory): Boolean {
        (inv.holder is BundleHolder) || return false
        player.inventory.itemInMainHand.type == Material.BUNDLE || return false

        val bundleMeta = player.inventory.itemInMainHand.itemMeta as BundleMeta
        bundleMeta.setItems(inv.contents.asList().filterNotNull())

        player.inventory.itemInMainHand.itemMeta = bundleMeta

        return true
    }

    @EventHandler
    fun InventoryClickEvent.click() {
        // Prevent interacting with the bundle via vanilla method when you have the bundle inventory open
        if (inventory.holder is BundleHolder && currentItem?.type == Material.BUNDLE) isCancelled = true

        if ((cursor?.type == Material.BUNDLE || currentItem?.type == Material.BUNDLE) && isRightClick) {
            val bundle = if (cursor?.type == Material.BUNDLE) cursor else currentItem
            val item = if (cursor?.type == Material.BUNDLE) currentItem else cursor
            // Check if the bundle has 54 unique items and stop adding new items
            val bundleMeta = bundle?.itemMeta as BundleMeta

            if (cursor?.type != Material.AIR && bundleMeta.items.size >= 54) {
                if (!bundleMeta.items.any { it.isSimilar(item) }) {
                    whoClicked.error("This bundle has too many unique items!")
                    isCancelled = true
                }
            }
        }

        if (action === MOVE_TO_OTHER_INVENTORY) {
            if (inventory.holder is BundleHolder && clickedInventory?.holder !is BundleHolder) isCancelled = true
        }

        if (clickedInventory?.holder is BundleHolder) {
            if (cursor?.type != Material.AIR) isCancelled = true

            when (action) {
                SWAP_WITH_CURSOR,
                HOTBAR_MOVE_AND_READD,
                HOTBAR_SWAP,
                InventoryAction.UNKNOWN -> {
                    isCancelled = true
                }
            }

            when (click) {
                SWAP_OFFHAND -> isCancelled = true
            }

        }

        if (inventory.holder is BundleHolder) {
            Bukkit.getServer().scheduler.scheduleSyncDelayedTask(bundlesPlugin, {
                saveBundle(whoClicked as Player, inventory)
            }, 1)
        }

    }

    @EventHandler
    fun InventoryDragEvent.drag() {
        if (inventory.holder is BundleHolder) isCancelled = true
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.player is Player) {
            val player = event.player as Player
            if (saveBundle(player, player.openInventory.topInventory)) {
                //
            }
        }
    }
}