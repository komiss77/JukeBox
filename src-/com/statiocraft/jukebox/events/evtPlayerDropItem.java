package com.statiocraft.jukebox.events;

import com.statiocraft.jukebox.scJukeBox;
import com.statiocraft.jukebox.fromapi.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class evtPlayerDropItem {

    public static void runEvent(final PlayerDropItemEvent evt) {
        ItemStack i = evt.getItemDrop().getItemStack();

        if (i != null) {
            if (ItemUtil.compare(i, scJukeBox.c().is_pml_) && scJukeBox.c().b_pmle_ || ItemUtil.compare(i, scJukeBox.c().is_pmr_) && scJukeBox.c().b_pmre_ || ItemUtil.compare(i, scJukeBox.c().is_pmR_) && scJukeBox.c().b_pmRe_ || ItemUtil.compare(i, scJukeBox.c().is_pms_) && scJukeBox.c().b_pmse_) {
                evt.setCancelled(true);
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(scJukeBox.getInstance(), new Runnable() {
                @Override
                public void run() {
                    evt.getPlayer().updateInventory();
                }
            }, 5L);
        }
    }
}
