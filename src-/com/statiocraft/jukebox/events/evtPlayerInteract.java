package com.statiocraft.jukebox.events;

import com.statiocraft.jukebox.JukeBox;
import com.statiocraft.jukebox.Shuffle;
import com.statiocraft.jukebox.SingleSong;
import com.statiocraft.jukebox.Song;
import com.statiocraft.jukebox.scJukeBox;
import com.statiocraft.jukebox.fromapi.util.ItemUtil;
import com.statiocraft.jukebox.fromapi.util.StringUtil;
import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class evtPlayerInteract {

    private static final String a = "§4Error§c: You are not permitted to use that item!";

    public static void runEvent(PlayerInteractEvent evt) {
        Player p = evt.getPlayer();
        ItemStack i = evt.getItem();

        if (i != null) {
            JukeBox j = scJukeBox.getCurrentJukebox(p);

            if (ItemUtil.compare(i, scJukeBox.c().is_pml_) && scJukeBox.c().b_pmle_) {
              //  if (!p.hasPermission("music.item.use.list")) {
             //       p.sendMessage("§4Error§c: You are not permitted to use that item!");
             //   } else {
                    scJukeBox.getGui().open(p);
             //   }
            } else if (ItemUtil.compare(i, scJukeBox.c().is_pmr_) && scJukeBox.c().b_pmre_) {
               // if (!p.hasPermission("music.item.use.random")) {
              //      p.sendMessage("§4Error§c: You are not permitted to use that item!");
             //   } else
                    if (j != null && j instanceof SingleSong) {
                    j.destroy();
                  //  p.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_muD_));
JukeBox.sendActionBar(p, StringUtil.convertCodes(scJukeBox.c().s_muD_)); 
                } else {
                    (new SingleSong((Song) scJukeBox.listSongs().get((new Random()).nextInt(scJukeBox.listSongs().size())))).addPlayer(p);
                }
            } else if (ItemUtil.compare(i, scJukeBox.c().is_pmR_) && scJukeBox.c().b_pmRe_) {
             //   if (!p.hasPermission("music.item.use.radio")) {
             //       p.sendMessage("§4Error§c: You are not permitted to use that item!");
             //   } else 
                if (j != null && j.equals(scJukeBox.getRadio())) {
                    j.removePlayer(p);
                  //  p.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_muD_));
JukeBox.sendActionBar(p, StringUtil.convertCodes(scJukeBox.c().s_muD_));  
                } else {
                    scJukeBox.getRadio().addPlayer(p);
                }
            } else {
                if (!ItemUtil.compare(i, scJukeBox.c().is_pms_) || !scJukeBox.c().b_pmse_) {
                    return;
                }

             //   if (!p.hasPermission("music.item.use.shuffle")) {
             //       p.sendMessage("§4Error§c: You are not permitted to use that item!");
             //   } else 
                    if (j != null && j instanceof Shuffle && !j.equals(scJukeBox.getRadio())) {
                    j.removePlayer(p);
                  //  p.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_muD_));
JukeBox.sendActionBar(p, StringUtil.convertCodes(scJukeBox.c().s_muD_));  
                } else {
                    (new Shuffle()).addPlayer(p);
                }
            }

            evt.setCancelled(true);
        }
    }
}
