package fr.skytasul.music.utils;

import com.xxmicloxx.NoteBlockAPI.model.Layer;
import org.bukkit.entity.Player;
import com.xxmicloxx.NoteBlockAPI.model.SoundCategory;
import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;

public class CustomSongPlayer extends RadioSongPlayer
{
    private boolean particlesEnabled;
    public boolean adminPlayed;
    
    public CustomSongPlayer(final Playlist playlist) {
        super(playlist, SoundCategory.RECORDS);
        this.particlesEnabled = false;
    }
    
    public void setParticlesEnabled(final boolean particles) {
       // if (JukeBox.particles) {
      //      this.particlesEnabled = particles;
      //  }
    }
    
    @Override
    public void playTick(final Player player, final int tick) {
        super.playTick(player, tick);
        if (!this.particlesEnabled) {
            return;
        }
        for (final Layer layer : this.song.getLayerHashMap().values()) {
            if (layer.getNote(tick) != null) {
               // Particles.sendParticles(player);
                break;
            }
        }
    }
}
