package com.statiocraft.jukebox.events;

import com.statiocraft.jukebox.SingleSong;
import com.statiocraft.jukebox.Song;
import com.statiocraft.jukebox.scJukeBox;
import com.statiocraft.jukebox.fromapi.menu.GUI;
import com.statiocraft.jukebox.fromapi.util.ItemUtil;
import java.util.Iterator;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class evtInventoryClick {

    public static void runEvent(InventoryClickEvent evt) {
        GUI g;

        if ((g = GUI.getByInventory(evt.getInventory())) != null) {
            evt.setCancelled(true);
            if (evt.getRawSlot() < g.getSize() + 9) {
                Player p = (Player) evt.getWhoClicked();
                ItemStack i = evt.getCurrentItem();
                boolean b = false;

                if (ItemUtil.compare(i, g.getClose())) {
                    p.closeInventory();
                    b = true;
                } else if (ItemUtil.compare(i, g.getPageBackward())) {
                    g.openPrevious(p);
                    b = true;
                } else if (ItemUtil.compare(i, g.getPageForward())) {
                    g.openNext(p);
                    b = true;
                } else if (ItemUtil.compare(i, scJukeBox.c().is_mmd_)) {
                    p.performCommand("music off");
                    b = true;
                } else if (ItemUtil.compare(i, scJukeBox.c().is_mmr_)) {
                    p.performCommand("music random");
                    b = true;
                } else if (ItemUtil.compare(i, scJukeBox.c().is_mmR_)) {
                    p.performCommand("music radio");
                    b = true;
                } else if (ItemUtil.compare(i, scJukeBox.c().is_mms_)) {
                    p.performCommand("music shuffle");
                    b = true;
                } else {
                    Iterator iterator = scJukeBox.listSongs().iterator();

                    while (iterator.hasNext()) {
                        Song s = (Song) iterator.next();

                        if (ItemUtil.compare(i, s.getMenuItem())) {
                            (new SingleSong(s)).addPlayer(p);
                            b = true;
                            break;
                        }
                    }
                }

                if (b) {
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                }

            }
        }
    }
}
