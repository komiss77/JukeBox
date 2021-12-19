package com.statiocraft.jukebox.events;

import com.statiocraft.jukebox.JukeBox;
import com.statiocraft.jukebox.scJukeBox;
import org.bukkit.event.player.PlayerQuitEvent;

public class evtPlayerQuit {

    public static void runEvent(PlayerQuitEvent evt) {
        JukeBox j;

        if ((j = scJukeBox.getCurrentJukebox(evt.getPlayer())) != null) {
            j.removePlayer(evt.getPlayer());
        }

    }
}
