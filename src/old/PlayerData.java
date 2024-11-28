package old;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import java.util.UUID;
import java.util.Map;
import org.bukkit.event.Listener;

public final class PlayerData implements Listener {

    public static Map<UUID, PlayerData> players;

    private UUID id;
    private final String name;
    protected boolean join;
    protected boolean shuffle;
    protected int volume;
    protected boolean particles;
    protected boolean repeat;
    protected boolean favoritesRemoved;
    protected Playlist favorites;
    protected Playlists listening;
    public CustomSongPlayer songPlayer;
    private List<Integer> randomPlaylist;
    JukeBoxInventory linked;

    private PlayerData(final String name, final UUID uuid) {
        this.id = uuid;
        this.name = name;
        this.join = false;
        this.shuffle = false;
        this.volume = 100;
        this.particles = true;
        this.repeat = false;
        this.favoritesRemoved = false;
        this.listening = Playlists.PLAYLIST;
        this.randomPlaylist = new ArrayList<>();
        this.linked = null;
        //Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) JukeBox.getInstance());
    }

    public PlayerData(final Player p, final PlayerData defaults) {
        this(p.getName(), p.getUniqueId());
        this.setJoinMusic(defaults.hasJoinMusic());
        this.setShuffle(defaults.isShuffle());
        this.setVolume(defaults.getVolume());
        this.setParticles(defaults.hasParticles());
        this.setRepeat(defaults.isRepeatEnabled());
    }

    /*  @EventHandler
    public void onLeave(final PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!p.getUniqueId().equals(this.id)) {
            return;
        }
        if (this.songPlayer != null) {
            this.songPlayer.setPlaying(false);
        }
        p = null;
    }*/
    public void playList(final Playlist list) {
        if (this.listening == Playlists.RADIO) {
            JukeBox.sendMessage(Bukkit.getPlayerExact(name), Lang.UNAVAILABLE_RADIO);
            return;
        }
        if (this.songPlayer != null) {
            this.stopPlaying(false);
        }
        if (list == null) {
            return;
        }
        (this.songPlayer = new CustomSongPlayer(list)).setParticlesEnabled(this.particles);
        this.songPlayer.getFadeIn().setFadeDuration(0);
        this.songPlayer.setAutoDestroy(true);
        this.songPlayer.addPlayer(Bukkit.getPlayerExact(name));
        this.songPlayer.setPlaying(true);
        this.songPlayer.setRandom(this.shuffle);
        this.songPlayer.setRepeatMode(this.repeat ? RepeatMode.ONE : RepeatMode.ALL);
        this.playSong(false);
        if (this.linked != null) {
            this.linked.playingStarted();
        }
    }

    public boolean playSong(final Song song) {
        if (this.listening == Playlists.RADIO) {
            JukeBox.sendMessage(Bukkit.getPlayerExact(name), Lang.UNAVAILABLE_RADIO);
            return false;
        }
        if (this.songPlayer != null) {
            this.stopPlaying(false);
        }
        if (song == null) {
            return false;
        }
        this.addSong(song, true);
        return this.listening == Playlists.FAVORITES;
    }

    public boolean addSong(final Song song, final boolean playIndex) {
        Playlist toPlay = null;
        switch (this.listening) {
            case FAVORITES: {
                if (this.favorites == null) {
                    this.favorites = new Playlist(new Song[]{song});
                } else if (playIndex && this.songPlayer != null) {
                    this.favorites.insert(this.songPlayer.getPlayedSongIndex() + 1, new Song[]{song});
                    this.finishPlaying();
                } else {
                    this.favorites.add(new Song[]{song});
                }
                toPlay = this.favorites;
                break;
            }
            case PLAYLIST: {
                this.randomPlaylist.add(JukeBox.getPlaylist().getIndex(song));
                if (playIndex) {
                    this.finishPlaying();
                }
                toPlay = JukeBox.getPlaylist();
                break;
            }
            case RADIO: {
                return false;
            }
        }
        if (this.songPlayer == null && Bukkit.getPlayerExact(name) != null) {
            this.playList(toPlay);
            return this.listening == Playlists.FAVORITES;
        }
        return true;
    }

    public void removeSong(final Song song) {
        switch (this.listening) {
            case FAVORITES -> {
                if (this.favorites.getCount() == 1) {
                    this.favorites = null;
                    this.favoritesRemoved = true;
                    this.songPlayer.setRepeatMode(this.repeat ? RepeatMode.ONE : RepeatMode.NO);
                }
                this.favorites.remove(new Song[]{song});
            }
            case PLAYLIST -> {
                this.randomPlaylist.remove((Object) JukeBox.getPlaylist().getIndex(song));
            }
        }
    }

    public boolean isInPlaylist(final Song song) {
        switch (this.listening) {
            case FAVORITES -> {
                if (this.favorites != null) {
                    return this.favorites.contains(song);
                }
            }
            case PLAYLIST -> {
                return this.randomPlaylist.contains(JukeBox.getPlaylist().getIndex(song));
            }
        }
        return false;
    }

    public void clearPlaylist() {
        switch (this.listening) {
            case FAVORITES -> {
                if (this.favorites == null) {
                }
                for (int i = 0; i < this.favorites.getCount() - 1; ++i) {
                    this.favorites.remove(new Song[]{this.favorites.get(0)});
                }
                this.removeSong(this.favorites.get(0));
            }
            case PLAYLIST -> {
                this.randomPlaylist.clear();
            }
        }
    }

    public Song playRandom() {
        if (JukeBox.getSongs().isEmpty()) {
            return null;
        }
        this.setPlaylist(Playlists.PLAYLIST, false);
        final Song song = JukeBox.randomSong();
        this.playSong(song);
        return song;
    }

    public void stopPlaying(final boolean msg) {
        if (this.songPlayer == null) {
            return;
        }
        final CustomSongPlayer tmp = this.songPlayer;
        this.songPlayer = null;
        tmp.destroy();
        if (msg && Bukkit.getPlayerExact(name) != null) {
            JukeBox.sendMessage(Bukkit.getPlayerExact(name), Lang.MUSIC_STOPPED);
        }
        if (this.linked != null) {
            this.linked.playingStopped();
        }
    }

    public Playlists getPlaylistType() {
        return this.listening;
    }

    public void nextPlaylist() {
        if (this.listening == Playlists.RADIO) {
            JukeBox.radio.leave(Bukkit.getPlayerExact(name));
        }
        switch (this.listening) {
            case PLAYLIST -> {
                this.setPlaylist(Playlists.FAVORITES, true);
            }
            case FAVORITES -> {
                this.setPlaylist(JukeBox.radioEnabled ? Playlists.RADIO : Playlists.PLAYLIST, true);
            }
            case RADIO -> {
                this.setPlaylist(Playlists.PLAYLIST, true);
            }
        }
    }

    public void setPlaylist(final Playlists list, final boolean play) {
        this.listening = list;
        if (this.linked != null) {
            this.linked.playlistItem();
        }
        if (!play || Bukkit.getPlayerExact(name) == null) {
            return;
        }
        this.stopPlaying(false);
        switch (this.listening) {
            case PLAYLIST -> {
                this.playList(JukeBox.getPlaylist());
            }
            case FAVORITES -> {
                this.playList(this.favorites);
            }
            case RADIO -> {
                JukeBox.radio.join(Bukkit.getPlayerExact(name));
            }
        }
    }

    private void finishPlaying() {
        if (this.songPlayer == null) {
            return;
        }
        this.songPlayer.setTick((short) (this.songPlayer.getSong().getLength() + 1));
        if (!this.songPlayer.isPlaying()) {
            this.songPlayer.setPlaying(true);
        }
    }

    public void nextSong() {
        if (this.listening == Playlists.RADIO) {
            JukeBox.sendMessage(Bukkit.getPlayerExact(name), Lang.UNAVAILABLE_RADIO);
            return;
        }
        if (this.songPlayer == null) {
            this.playList((this.listening == Playlists.PLAYLIST) ? JukeBox.getPlaylist() : this.favorites);
        } else {
            this.finishPlaying();
        }
    }

    /* public void playerJoin(final Player player) {
        this.p = player;
        //if (!replay) {
        //    return;
        //}
        // if (JukeBox.radioOnJoin) {
        //    this.setPlaylist(Playlists.RADIO, true);
        //    return;
        // }
        //if (this.listening == Playlists.RADIO) {
        //    return;
        //}
        if (p.hasPermission("ostrov.music")) {
            if (this.songPlayer == null) {
                if (this.hasJoinMusic()) {
                    this.playRandom();
                }
            }
        }// else if (!this.songPlayer.adminPlayed && JukeBox.autoReload) {
        //   this.songPlayer.setPlaying(true);
        //    JukeBox.sendMessage(this.p, String.valueOf(Lang.RELOAD_MUSIC) + " (" + JukeBox.getSongName(this.songPlayer.getSong()) + ")");
        // }
    }*/
    //public void playerLeave() {
    //if (!JukeBox.autoReload) {
    //       this.stopPlaying(false);
    //}
    //}
    protected void playSong(final boolean next) {
        if (this.listening == Playlists.PLAYLIST && !this.randomPlaylist.isEmpty()) {
            this.songPlayer.playSong((int) this.randomPlaylist.get(0));
            final int id = this.randomPlaylist.remove(0);
            if (next && this.linked != null) {
                this.linked.songItem(id);
            }
        }
        JukeBox.sendMessage(Bukkit.getPlayerExact(name), String.valueOf(Lang.MUSIC_PLAYING) + " " + JukeBox.getSongName(this.songPlayer.getSong()));
    }

    public UUID getID() {
        return this.id;
    }

    public boolean hasJoinMusic() {
        return this.join;
    }

    public boolean setJoinMusic(final boolean join) {
        this.join = join;
        if (this.linked != null) {
            this.linked.joinItem();
        }
        return join;
    }

    public boolean isShuffle() {
        return this.shuffle;
    }

    public boolean setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
        if (this.songPlayer != null) {
            this.songPlayer.setRandom(true);
        }
        if (this.linked != null) {
            this.linked.shuffleItem();
        }
        return shuffle;
    }

    public int getVolume() {
        return this.volume;
    }

    public int setVolume(final int volume) {
        if (this.id != null) {
            NoteBlockAPI.setPlayerVolume(this.id, (byte) volume);
        }
        this.volume = volume;
        if (this.linked != null) {
            this.linked.volumeItem();
        }
        return volume;
    }

    public boolean hasParticles() {
        return this.particles;
    }

    public boolean setParticles(final boolean particles) {
        if (this.songPlayer != null) {
            this.songPlayer.setParticlesEnabled(particles);
        }
        this.particles = particles;
        if (this.linked != null) {
            this.linked.particlesItem();
        }
        return particles;
    }

    public boolean isRepeatEnabled() {
        return this.repeat;
    }

    public boolean setRepeat(final boolean repeat) {
        this.repeat = repeat;
        if (this.songPlayer != null) {
            this.songPlayer.setRepeatMode(repeat ? RepeatMode.ONE : ((this.listening == Playlists.FAVORITES && this.favorites == null) ? RepeatMode.NO : RepeatMode.ALL));
        }
        if (this.linked != null) {
            this.linked.repeatItem();
        }
        return repeat;
    }

    public boolean isDefault(final PlayerData base) {
        return (base.hasJoinMusic() == this.hasJoinMusic() || JukeBox.autoJoin) && base.isShuffle() == this.isShuffle() && base.getVolume() == this.getVolume() && base.hasParticles() == this.hasParticles() && base.isRepeatEnabled() == this.isRepeatEnabled();
    }

    /* public Map<String, Object> serialize() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", this.id.toString());
        map.put("join", this.hasJoinMusic());
        map.put("shuffle", this.isShuffle());
        map.put("volume", this.getVolume());
        map.put("particles", this.hasParticles());
        map.put("repeat", this.isRepeatEnabled());
        if (this.favorites != null) {
            final List<String> list = new ArrayList<String>();
            for (final Song song : this.favorites.getSongList()) {
                list.add(JukeBox.getInternal(song));
            }
            map.put("favorites", list);
        }
        return map;
    }*/
    public static PlayerData deserialize(final Map<String, Object> map, final Map<String, Song> songsName) {
        final PlayerData pdata = new PlayerData("defaultPlayerOptions", map.containsKey("id") ? UUID.fromString((String) map.get("id")) : null);
        pdata.setJoinMusic((boolean) map.get("join"));
        pdata.setShuffle((boolean) map.get("shuffle"));
        if (map.containsKey("volume")) {
            pdata.setVolume((int) map.get("volume"));
        }
        if (map.containsKey("particles")) {
            pdata.setParticles((boolean) map.get("particles"));
        }
        if (map.containsKey("repeat")) {
            pdata.setRepeat((boolean) map.get("repeat"));
        }
        if (map.containsKey("favorites")) {
            pdata.setPlaylist(Playlists.FAVORITES, false);
            List<String> favorites = (List<String>) map.get("favorites");
            for (final String s : favorites) {
                final Song song = songsName.get(s);
                if (song == null) {
                    JukeBox.getInstance().getLogger().warning("Song \"" + s + "\" for playlist of " + pdata.getID().toString());
                } else {
                    pdata.addSong(song, false);
                }
            }
            pdata.setPlaylist(Playlists.PLAYLIST, false);
        }
        return pdata;
    }
}
