package com.statiocraft.jukebox;

import com.statiocraft.jukebox.fromapi.util.StringUtil;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.IllegalPluginAccessException;

public class Shuffle extends JukeBox {

    public Shuffle() {
        super(scJukeBox.listSongs());
    }

    @Override
    public void addPlayers(List players) {
        super.addPlayers(players);
        String s = StringUtil.convertCodes(scJukeBox.c().s_npt_.replaceAll("#TRACK", super.s[super.c].getName()));
        Iterator iterator = players.iterator();

        while (iterator.hasNext()) {
            Player p = (Player) iterator.next();
JukeBox.sendActionBar(p, s);  
           // p.sendMessage(s);
        }

    }

    @Override
    public boolean next() {
        if (!super.next()) {
            return false;
        } else {
            String s = StringUtil.convertCodes(scJukeBox.c().s_npt_.replaceAll("#TRACK", super.s[super.c].getName()));
            Iterator iterator = super.p.iterator();

            while (iterator.hasNext()) {
                Player p = (Player) iterator.next();
JukeBox.sendActionBar(p, s);  
              //  p.sendMessage(s);
            }

            return true;
        }
    }

    @Override
    public void onDestroy() {
        final List l = super.listPlayers();

        try {
            Bukkit.getScheduler().scheduleSyncDelayedTask(scJukeBox.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Shuffle s = new Shuffle();

                    s.addPlayers(l);

                    try {
                        Field e = scJukeBox.class.getDeclaredField("r");

                        e.setAccessible(true);
                        e.set(scJukeBox.getInstance(), s);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            }, 20L);
        } catch (IllegalPluginAccessException illegalpluginaccessexception) {
            ;
        }

    }
}
