package com.statiocraft.jukebox;


import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.entity.Player;
import ru.komiss77.ApiOstrov;

public abstract class JukeBox {

    protected final List p;
    protected final Song[] s;
    protected final SongPlayer[] l;
    protected int c;

    public JukeBox(Song... songs) {
        this((List) (new ArrayList(Arrays.asList(songs))));
    }

    public JukeBox(List songs) {
        this.p = new ArrayList();
        this.c = -1;
        this.s = new Song[songs.size()];

        for (int n = 0; n < this.s.length; ++n) {
            int i = (new Random()).nextInt(songs.size());

            this.s[n] = (Song) songs.get(i);
            songs.remove(i);
        }

        this.l = new SongPlayer[this.s.length];
        scJukeBox.registerNewJukebox(this);
        this.next();
    }

    public boolean next() {
        try {
            this.l[this.c].destroy(false);
        } catch (Exception exception) {
            ;
        }

        if (scJukeBox.d()) {
            return false;
        } else {
            ++this.c;
            if (this.c >= this.s.length) {
                this.destroy();
                return false;
            } else {
                this.l[this.c] = this.s[this.c].play(this.p, RadioSongPlayer.class);
                this.l[this.c].setAutoDestroy(false);
                this.l[this.c].callOnDestroy(new Runnable() {
                    @Override
                    public void run() {
                        Iterator iterator = JukeBox.this.p.iterator();

                        while (iterator.hasNext()) {
                            Player p = (Player) iterator.next();

                            JukeBox.this.l[JukeBox.this.c].removePlayer(p);
                        }

                        JukeBox.this.next();
                    }
                });
                return true;
            }
        }
    }

    public void addPlayers(List players) {
        Iterator iterator = players.iterator();

        while (iterator.hasNext()) {
            Player p = (Player) iterator.next();
            JukeBox j;

            if ((j = scJukeBox.getCurrentJukebox(p)) != null) {
                j.removePlayer(p);
            }

            this.p.add(p);
            this.l[this.c].addPlayer(p);
        }

    }

    public void addPlayer(Player player) {
        this.addPlayers(Arrays.asList(new Player[] { player}));
    }

    public void removePlayer(Player player) {
        try {
            this.l[this.c].removePlayer(player);
        } catch (Exception exception) {
            ;
        }

        this.p.remove(player);
    }

    public void removePlayers(List players) {
        Iterator iterator = players.iterator();

        while (iterator.hasNext()) {
            Player p = (Player) iterator.next();

            this.removePlayer(p);
        }

    }

    public List listPlayers() {
        return new ArrayList(this.p);
    }

    protected abstract void onDestroy();

    public void destroy() {
        try {
            this.l[this.c].destroy();
        } catch (Exception exception) {
            ;
        }

        this.onDestroy();
        this.removePlayers(this.listPlayers());
        scJukeBox.unregisterJukebox(this);
    }
    
     public static void sendActionBar(Player p, String msg) {
         ApiOstrov.sendActionBar(p, msg);
    }
    
   
    
    
    public static void sendTitle(Player p, String title, String subtitle) {
        ApiOstrov.sendTabList(p, title, subtitle);
    }
    
   
    
    
    
}
