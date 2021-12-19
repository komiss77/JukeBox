package com.statiocraft.jukebox;

import com.statiocraft.jukebox.fromapi.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.Player;

public class SingleSong extends JukeBox {

    public SingleSong(Song song) {
        super(new Song[] { song});
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

              //  p.sendMessage(s);
             JukeBox.sendActionBar(p, s);    
                
            }

            return true;
        }
    }

    @Override
    public void addPlayers(List players) {
        super.addPlayers(players);
        String s = StringUtil.convertCodes(scJukeBox.c().s_npt_.replaceAll("#TRACK", super.s[super.c].getName()));
        Iterator iterator = players.iterator();

        while (iterator.hasNext()) {
            Player p = (Player) iterator.next();
JukeBox.sendActionBar(p, s);  
         //   p.sendMessage(s);
        }

    }

    @Override
    public void onDestroy() {}
}
