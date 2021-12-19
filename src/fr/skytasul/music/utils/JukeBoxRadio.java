// 
// Decompiled by Procyon v0.5.36
// 

package fr.skytasul.music.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import java.util.Iterator;
import java.util.UUID;
import com.xxmicloxx.NoteBlockAPI.event.SongNextEvent;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import org.bukkit.plugin.Plugin;
import fr.skytasul.music.JukeBox;
import org.bukkit.Bukkit;
import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import org.bukkit.event.Listener;

public class JukeBoxRadio implements Listener
{
    public final CustomSongPlayer songPlayer;
    
    public JukeBoxRadio(final Playlist songs) {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)JukeBox.getInstance());
        (this.songPlayer = new CustomSongPlayer(songs)).setRandom(true);
        this.songPlayer.setAutoDestroy(false);
        this.songPlayer.setRepeatMode(RepeatMode.ALL);
        this.songPlayer.setPlaying(true);
    }
    
    @EventHandler
    public void onSongNext(final SongNextEvent e) {
        if (e.getSongPlayer() == this.songPlayer) {
            for (final UUID id : this.songPlayer.getPlayerUUIDs()) {
                final Player p = Bukkit.getPlayer(id);
                if (p != null) {
                    JukeBox.sendMessage(p, String.valueOf(Lang.MUSIC_PLAYING) + " " + JukeBox.getSongName(e.getSongPlayer().getSong()));
                }
            }
        }
    }
    
    public void join(final Player p) {
        this.songPlayer.addPlayer(p);
        JukeBox.sendMessage(p, String.valueOf(Lang.MUSIC_PLAYING) + " " + JukeBox.getSongName(this.songPlayer.getSong()));
    }
    
    public void leave(final Player p) {
        this.songPlayer.removePlayer(p);
    }
    
    public void stop() {
        for (final UUID id : this.songPlayer.getPlayerUUIDs()) {
            final Player p = Bukkit.getPlayer(id);
            if (p != null) {
                JukeBox.sendMessage(p, Lang.MUSIC_STOPPED);
            }
        }
        this.songPlayer.destroy();
    }
}
