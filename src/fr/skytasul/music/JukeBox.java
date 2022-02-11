package fr.skytasul.music;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import java.io.InputStream;
import fr.skytasul.music.utils.Lang;
import java.io.Reader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.UUID;
import java.util.Collections;
import java.text.Collator;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import java.util.HashMap;
import org.bukkit.command.CommandExecutor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.HandlerList;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.Random;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;
import java.util.List;
import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import java.util.Map;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import java.util.LinkedList;
import fr.skytasul.music.utils.JukeBoxRadio;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.komiss77.events.BungeeDataRecieved;

public class JukeBox extends JavaPlugin implements Listener
{
    public static int version;
    private static JukeBox instance;
    private boolean disable;
    public static File songsFolder;
    public static JukeBoxRadio radio;
    private static LinkedList<Song> songs;
    private static Map<String, Song> fileNames;
    private static Playlist playlist;
    public static int maxPage;
    public static boolean jukeboxClick;
    public static boolean async;
    public static boolean autoJoin;
    public static boolean radioEnabled;
    public static PlayerData defaultPlayer;
    public static List<World> worldsEnabled;
    public static String itemFormat;
    public static String songFormat;
    public ItemStack jukeboxItem;
    private static Random random;
    
    static {
        JukeBox.version = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].split("_")[1]);
        JukeBox.radio = null;
        JukeBox.jukeboxClick = false;
        JukeBox.async = false;
        JukeBox.autoJoin = false;
        JukeBox.radioEnabled = true;
        JukeBox.defaultPlayer = null;
        JukeBox.random = new Random();
    }
    
    public JukeBox() {
        this.disable = false;
    }
    
    public void onEnable() {
        JukeBox.instance = this;
        if (!this.getServer().getPluginManager().isPluginEnabled("NoteBlockAPI") || this.getServer().getPluginManager().getPlugin("NoteBlockAPI") == null) {
            this.getLogger().severe("NoteBlockAPI isn't loaded. Please install it on your server and restart it.");
            this.disable = true;
            this.getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        //if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
        //    Placeholders.registerPlaceholders();
        //}
        //this.getLogger().info("This JukeBox version requires NoteBlockAPI version 1.4.3 or more. Please ensure that before using JukeBox (you are using NBAPI ver. " + ((NoteBlockAPI)getPlugin((Class)NoteBlockAPI.class)).getDescription().getVersion() + ")");
        this.saveDefaultConfig();
        this.initAll();
    }
    
    public void onDisable() {
        if (!this.disable) {
            this.disableAll();
        }
    }
    
    public void disableAll() {
        if (JukeBox.radio != null) {
            JukeBox.radio.stop();
            JukeBox.radio = null;
        }
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (final PlayerData pdata : PlayerData.players.values()) {
            if (pdata.songPlayer != null) {
                pdata.stopPlaying(true);
            }
            if (!pdata.isDefault(JukeBox.defaultPlayer)) {
                list.add(pdata.serialize());
            }
        }
        HandlerList.unregisterAll((Plugin)this);
    }
    
    public void initAll() {
        this.reloadConfig();
        this.loadLang();
        final FileConfiguration config = this.getConfig();
        JukeBox.jukeboxClick = config.getBoolean("jukeboxClick");
        JukeBox.async = config.getBoolean("asyncLoading");
        JukeBox.autoJoin = config.getBoolean("forceJoinMusic");
        JukeBox.defaultPlayer = PlayerData.deserialize(config.getConfigurationSection("defaultPlayerOptions").getValues(false), null);
        JukeBox.radioEnabled = config.getBoolean("radio");
        JukeBox.itemFormat = config.getString("itemFormat");
        JukeBox.songFormat = config.getString("songFormat");
        JukeBox.worldsEnabled = new ArrayList<World>();
        for (final String name : config.getStringList("enabledWorlds")) {
            final World world = Bukkit.getWorld(name);
            if (world != null) {
                JukeBox.worldsEnabled.add(world);
            }
        }
        if (JukeBox.async) {
            new BukkitRunnable() {
                public void run() {
                    JukeBox.this.loadDatas();
                    JukeBox.this.finishEnabling();
                }
            }.runTaskAsynchronously((Plugin)this);
        }
        else {
            this.loadDatas();
            this.finishEnabling();
        }
    }
    
    private void finishEnabling() {
        this.getCommand("music").setExecutor((CommandExecutor)new CommandMusic());
        //this.getCommand("adminmusic").setExecutor((CommandExecutor)new CommandAdmin());
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        JukeBox.radioEnabled = (JukeBox.radioEnabled && !JukeBox.songs.isEmpty());
        if (JukeBox.radioEnabled) {
            JukeBox.radio = new JukeBoxRadio(JukeBox.playlist);
        }
    }
    
    private void loadDatas() {
        JukeBox.songs = new LinkedList<Song>();
        JukeBox.fileNames = new HashMap<String, Song>();
        JukeBox.songsFolder = new File(this.getDataFolder(), "songs");
        if (!JukeBox.songsFolder.exists()) {
            JukeBox.songsFolder.mkdirs();
        }
        final Map<String, Song> tmpSongs = new HashMap<String, Song>();
        File[] listFiles;
        for (int length = (listFiles = JukeBox.songsFolder.listFiles()).length, i = 0; i < length; ++i) {
            final File file = listFiles[i];
            if (file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("nbs")) {
                final Song song = NBSDecoder.parse(file);
                if (song != null) {
                    final String n = getInternal(song);
                    if (tmpSongs.containsKey(n)) {
                        this.getLogger().warning("Song \"" + n + "\" is duplicated. Please delete one from the songs directory. File name: " + file.getName());
                    }
                    else {
                        JukeBox.fileNames.put(file.getName(), song);
                        tmpSongs.put(n, song);
                    }
                }
            }
        }
        this.getLogger().info(String.valueOf(tmpSongs.size()) + " songs loadeds. Sorting by name... ");
        final List<String> names = new ArrayList<String>(tmpSongs.keySet());
        Collections.sort(names, Collator.getInstance());
        for (final String str : names) {
            JukeBox.songs.add(tmpSongs.get(str));
        }
        this.setMaxPage();
        this.getLogger().info("Songs sorted ! " + JukeBox.songs.size() + " songs. Number of pages : " + JukeBox.maxPage);
        if (!JukeBox.songs.isEmpty()) {
            JukeBox.playlist = new Playlist((Song[])JukeBox.songs.toArray(new Song[0]));
        }
        PlayerData.players = new HashMap<UUID, PlayerData>();


    }
    
    void setMaxPage() {
        JukeBox.maxPage = (int)StrictMath.ceil(JukeBox.songs.size() * 1.0 / 45.0);
    }
    
    private YamlConfiguration loadLang() {
        String s = "en.yml";
        if (this.getConfig().getString("lang") != null) {
            s = String.valueOf(this.getConfig().getString("lang")) + ".yml";
        }
        final File lang = new File(this.getDataFolder(), s);
        if (!lang.exists()) {
            try {
                this.getDataFolder().mkdir();
                lang.createNewFile();
                final InputStream defConfigStream = this.getResource(s);
                if (defConfigStream != null) {
                    final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration((Reader)new InputStreamReader(defConfigStream, StandardCharsets.UTF_8));
                    defConfig.save(lang);
                    Lang.loadFromConfig(defConfig);
                    this.getLogger().info("Created language file " + s);
                    return defConfig;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                this.getLogger().severe("Couldn't create language file.");
                this.getLogger().severe("This is a fatal error. Now disabling.");
                this.disable = true;
                this.setEnabled(false);
            }
        }
        final YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        try {
            Lang.saveFile(conf, lang);
        }
        catch (IOException | IllegalArgumentException | IllegalAccessException ex) {
            //final Exception ex;
            //final Exception e2 = ex;
            this.getLogger().warning("Failed to save lang.yml.");
            this.getLogger().warning("Report this stack trace to SkytAsul on SpigotMC.");
            ex.printStackTrace();
        }
        Lang.loadFromConfig(conf);
        this.getLogger().info("Loaded language file " + s);
        return conf;
    }
    
    @EventHandler
    public void onBungeeData(final BungeeDataRecieved e) {
        final Player p = e.getPlayer();
        final UUID id = p.getUniqueId();
        PlayerData pdata = PlayerData.players.get(id);
        if (pdata == null) {
            pdata = PlayerData.create(id);
            PlayerData.players.put(id, pdata);
        }
        pdata.playerJoin(p);
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        PlayerData.players.get(e.getPlayer().getUniqueId()).playerLeave();
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getItem() == null) {
            return;
        }
        if (this.jukeboxItem != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem().equals((Object)this.jukeboxItem)) {
            CommandMusic.open(e.getPlayer());
            e.setCancelled(true);
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && JukeBox.jukeboxClick && e.getClickedBlock().getType() == Material.JUKEBOX) {
            final String disc = e.getItem().getType().name();
            if (JukeBoxInventory.version < 13) {
                if (!JukeBoxInventory.discs8.contains(disc)) {
                    return;
                }
            }
            else if (!JukeBoxInventory.discs13.contains(disc)) {
                return;
            }
            CommandMusic.open(e.getPlayer());
            e.setCancelled(true);
        }
    }
    

    
    public static JukeBox getInstance() {
        return JukeBox.instance;
    }
    
    public static Song randomSong() {
        if (JukeBox.songs.isEmpty()) {
            return null;
        }
        if (JukeBox.songs.size() == 1) {
            return JukeBox.songs.get(0);
        }
        return JukeBox.songs.get(JukeBox.random.nextInt(JukeBox.songs.size() - 1));
    }
    
    public static Playlist getPlaylist() {
        return JukeBox.playlist;
    }
    
    public static List<Song> getSongs() {
        return JukeBox.songs;
    }
    
    public static Song getSongByFile(final String fileName) {
        return JukeBox.fileNames.get(fileName);
    }
    
    public static String getInternal(final Song s) {
        if (s.getTitle() == null || s.getTitle().isEmpty()) {
            return s.getPath().getName();
        }
        return s.getTitle();
    }
    
    public static String getItemName(final Song s) {
        return format(JukeBox.itemFormat, s);
    }
    
    public static String getSongName(final Song song) {
        return format(JukeBox.songFormat, song);
    }
    
    public static String format(final String base, final Song song) {
        final String name = song.getTitle().isEmpty() ? song.getPath().getName() : song.getTitle();
        final String author = song.getAuthor();
        final String id = String.valueOf(JukeBox.songs.indexOf(song));
        return base.replace("{NAME}", name).replace("{AUTHOR}", author).replace("{ID}", id);
    }
    
    public static boolean sendMessage(final Player p, final String msg) {
     //   if (JukeBox.sendMessages) {
          //  if (JukeBox.actionBar) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
         //   }
           // else {
          //      p.sendMessage(msg);
          //  }
            return true;
        }
       // return false;
  //  }
}
