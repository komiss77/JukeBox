package com.statiocraft.jukebox;

import com.statiocraft.jukebox.fromapi.util.ItemUtil;
import com.statiocraft.jukebox.fromapi.util.StringUtil;
import com.statiocraft.xxmicloxx.NoteBlockAPI.AbstractSong;
import com.statiocraft.xxmicloxx.NoteBlockAPI.SongPlayer;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.IllegalPluginAccessException;

public class Song extends AbstractSong {

    private static int cc = 1;
    private static int dt = 0;
    private final ItemStack d;
    private final String n;
    private final String dn;

    public Song(AbstractSong other) {
        this(other.getSpeed(), other.getLayerHashMap(), other.getSongHeight(), other.getLength(), other.getTitle(), other.getAuthor(), other.getDescription(), other.getPath());
    }

    public Song(float speed, HashMap layerHashMap, short songHeight, short length, String title, String author, String description, File path) {
        super(speed, layerHashMap, songHeight, length, title, author, description, path);
        this.n = scJukeBox.c().b_usnfd_ ? super.getTitle() : super.getPath().getName().replaceAll(".nbs", "");
        this.dn = "§" + Song.cc + StringUtil.convertCodes(this.n);
        if (Song.cc >= 9) {
            Song.cc = 1;
        } else {
            ++Song.cc;
        }

        int d = 2256 + Song.dt;

        if (Song.dt >= 9) {
            Song.dt = 0;
        } else {
            ++Song.dt;
        }

        this.d = ItemUtil.create(Material.getMaterial(d), 1, this.dn);
    }

    public String getName() {
        return this.n;
    }

    public String getDisplayName() {
        return this.dn;
    }

    public ItemStack getMenuItem() {
        return this.d;
    }

    public SongPlayer play(List players, Class clazz) {
        try {
            if (scJukeBox.d()) {
                throw new IllegalPluginAccessException("Cannot start a new song whilst plugin disabled!");
            } else {
                final SongPlayer e = (SongPlayer) clazz.getDeclaredConstructor(new Class[] { AbstractSong.class}).newInstance(new Object[] { this});
                Iterator iterator = players.iterator();

                while (iterator.hasNext()) {
                    Player p = (Player) iterator.next();

                    e.addPlayer(p);
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(scJukeBox.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        e.setPlaying(true);
                    }
                }, 60L);
                return e;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public SongPlayer play(Player player, Class clazz) {
        return this.play(Arrays.asList(new Player[] { player}), clazz);
    }
}
