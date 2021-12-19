package com.statiocraft.jukebox;

import com.statiocraft.jukebox.commands.CommandManager;
import com.statiocraft.jukebox.configuration.ConfigFile;
import com.statiocraft.jukebox.events.EventManager;
import com.statiocraft.jukebox.fromapi.menu.GUI;
import com.statiocraft.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.statiocraft.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class scJukeBox extends JavaPlugin {

    private static scJukeBox instance = null;
    private static ConfigFile c;
    private final NoteBlockPlayerMain np;
    private final List s;
    private final List j;
    private Shuffle r;
    private MusicGUI g;
    private boolean d;

    public scJukeBox() {
        if (scJukeBox.instance != null) {
            throw new IllegalArgumentException();
        } else {
            scJukeBox.instance = this;
            this.d = false;
            this.np = new NoteBlockPlayerMain(this);
            this.s = new ArrayList();
            this.j = new ArrayList();
        }
    }

    @Override
    public void onEnable() {
        this.np.onEnable();
        new CommandManager(this);
        new EventManager(this);
        this.reloadConfig();
    }

    @Override
    public void onDisable() {
        this.d = true;
        Iterator iterator = (new ArrayList(this.j)).iterator();

        while (iterator.hasNext()) {
            JukeBox j = (JukeBox) iterator.next();

            j.destroy();
        }

        this.np.onDisable();
    }

    @Override
    public void reloadConfig() {
        this.d = true;
        Iterator o = Bukkit.getOnlinePlayers().iterator();

        while (o.hasNext()) {
            Player f = (Player) o.next();

            if (GUI.getByInventory(f.getOpenInventory().getTopInventory()) != null) {
                f.closeInventory();
            }
        }

        this.s.clear();
        o = (new ArrayList(this.j)).iterator();

        while (o.hasNext()) {
            JukeBox jukebox = (JukeBox) o.next();

            jukebox.destroy();
        }

        if (this.r != null) {
            this.r.removePlayers(this.r.listPlayers());
            this.r.destroy();
        }

        this.d = false;
        super.saveResource("configuration.yml", false);
        File file = new File(super.getDataFolder().getPath() + "/configuration.yml");

        try {
            scJukeBox.c = new ConfigFile(file);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            Console.severe("==============================================");
            Console.severe("= Failed to load scJukeBox Configuration!    =");
            Console.severe("=--------------------------------------------=");
            Console.severe("= Disabling plugin...                        =");
            Console.severe("==============================================");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        File file1 = new File(super.getDataFolder().getPath() + "/tracks/");
        int i;

        if (!file1.exists()) {
            String[] t = new String[] { "DJ Got Us Fallin\' in Love", "Fireflies", "Gangnam Style", "Hold The Line", "Nothing Else Matters", "Nyan Cat", "Pushing Onwards", "Sweden", "Tetris A Theme", "The Edge of Glory", "What is Love"};
            String[] astring = t;
            int j = t.length;

            for (i = 0; i < j; ++i) {
                String s = astring[i];

                super.saveResource("tracks/" + s + ".nbs", true);
            }
        }

        File[] afile;

        i = (afile = file1.listFiles()).length;

        for (int k = 0; k < i; ++k) {
            File file2 = afile[k];

            if (!file2.isDirectory() && file2.getName().endsWith(".nbs")) {
                registerNewSong(NBSDecoder.parse(file2));
            }
        }

        this.r = new Shuffle();
        this.g = new MusicGUI();
    }

    public static scJukeBox getInstance() {
        return scJukeBox.instance;
    }

    public static ConfigFile c() {
        return scJukeBox.c;
    }

    public static void registerNewSong(Song song) {
        if (song != null) {
            scJukeBox.instance.s.add(song);
        }
    }

    public static void registerNewJukebox(JukeBox jukebox) {
        if (jukebox != null) {
            scJukeBox.instance.j.add(jukebox);
        }
    }

    public static void unregisterJukebox(JukeBox jukebox) {
        scJukeBox.instance.j.remove(jukebox);
    }

    public static JukeBox getCurrentJukebox(Player player) {
        Iterator iterator = scJukeBox.instance.j.iterator();

        while (iterator.hasNext()) {
            JukeBox j = (JukeBox) iterator.next();

            if (j.listPlayers().contains(player)) {
                return j;
            }
        }

        return null;
    }

    public static List listSongs() {
        return new ArrayList(scJukeBox.instance.s);
    }

    public static Shuffle getRadio() {
        return scJukeBox.instance.r;
    }

    public static MusicGUI getGui() {
        return scJukeBox.instance.g;
    }

    public static boolean d() {
        return scJukeBox.instance.d;
    }
}
