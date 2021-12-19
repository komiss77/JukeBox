package com.statiocraft.jukebox.events;

import com.statiocraft.jukebox.scJukeBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventManager implements Listener {

    public EventManager(scJukeBox plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent evt) {
        evtBlockPlace.runEvent(evt);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        evtInventoryClick.runEvent(evt);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent evt) {
        evtPlayerDropItem.runEvent(evt);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        evtPlayerInteract.runEvent(evt);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        evtPlayerJoin.runEvent(evt);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        evtPlayerQuit.runEvent(evt);
    }
}
