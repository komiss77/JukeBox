package com.statiocraft.jukebox.events;

import com.statiocraft.jukebox.Shuffle;
import com.statiocraft.jukebox.SingleSong;
import com.statiocraft.jukebox.Song;
import com.statiocraft.jukebox.scJukeBox;
import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class evtPlayerJoin {

    public static void runEvent(PlayerJoinEvent evt) {
        int n = scJukeBox.c().i_aoj_;
        Player p = evt.getPlayer();

        if (n == 3) {
            scJukeBox.getRadio().addPlayer(p);
        } else if (n == 2) {
            (new SingleSong((Song) scJukeBox.listSongs().get((new Random()).nextInt(scJukeBox.listSongs().size())))).addPlayer(p);
        } else if (n == 1) {
            (new Shuffle()).addPlayer(p);
        }

    }
}
