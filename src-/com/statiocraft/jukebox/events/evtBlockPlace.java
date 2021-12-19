package com.statiocraft.jukebox.events;

import com.statiocraft.jukebox.scJukeBox;
import com.statiocraft.jukebox.fromapi.util.ItemUtil;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class evtBlockPlace {

    public static void runEvent(BlockPlaceEvent evt) {
        ItemStack i = evt.getItemInHand();

        if (i != null) {
            if (ItemUtil.compare(i, scJukeBox.c().is_pml_) && scJukeBox.c().b_pmle_ || ItemUtil.compare(i, scJukeBox.c().is_pmr_) && scJukeBox.c().b_pmre_ || ItemUtil.compare(i, scJukeBox.c().is_pmR_) && scJukeBox.c().b_pmRe_ || ItemUtil.compare(i, scJukeBox.c().is_pms_) && scJukeBox.c().b_pmse_) {
                evt.setCancelled(true);
            }

        }
    }
}
