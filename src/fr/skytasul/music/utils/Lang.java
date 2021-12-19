// 
// Decompiled by Procyon v0.5.36
// 

package fr.skytasul.music.utils;

import java.util.Iterator;
import fr.skytasul.music.JukeBox;
import java.io.IOException;
import java.lang.reflect.Field;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.ChatColor;

public class Lang
{
    public static String NEXT_PAGE;
    public static String LATER_PAGE;
    public static String PLAYER;
    public static String RELOAD_MUSIC;
    public static String INV_NAME;
    public static String TOGGLE_PLAYING;
    public static String VOLUME;
    public static String RIGHT_CLICK;
    public static String LEFT_CLICK;
    public static String RANDOM_MUSIC;
    public static String STOP;
    public static String MUSIC_STOPPED;
    public static String ENABLE;
    public static String DISABLE;
    public static String ENABLED;
    public static String DISABLED;
    public static String SHUFFLE_MODE;
    public static String LOOP_MODE;
    public static String CONNEXION_MUSIC;
    public static String PARTICLES;
    public static String MUSIC_PLAYING;
    public static String INCORRECT_SYNTAX;
    public static String RELOAD_LAUNCH;
    public static String RELOAD_FINISH;
    public static String AVAILABLE_COMMANDS;
    public static String INVALID_NUMBER;
    public static String PLAYER_MUSIC_STOPPED;
    public static String IN_PLAYLIST;
    public static String PLAYLIST_ITEM;
    public static String OPTIONS_ITEM;
    public static String MENU_ITEM;
    public static String CLEAR_PLAYLIST;
    public static String NEXT_ITEM;
    public static String CHANGE_PLAYLIST;
    public static String PLAYLIST;
    public static String FAVORITES;
    public static String RADIO;
    public static String UNAVAILABLE_RADIO;
    public static String NONE;
    
    static {
        Lang.NEXT_PAGE = ChatColor.AQUA + "Next page";
        Lang.LATER_PAGE = ChatColor.AQUA + "Previous page";
        Lang.PLAYER = ChatColor.RED + "You must be a player to do this command.";
        Lang.RELOAD_MUSIC = ChatColor.GREEN + "Music reloaded.";
        Lang.INV_NAME = ChatColor.LIGHT_PURPLE + "�lJukebox !";
        Lang.TOGGLE_PLAYING = ChatColor.GOLD + "Pause/play";
        Lang.VOLUME = ChatColor.BLUE + "Music volume : �b";
        Lang.RIGHT_CLICK = "�eRight click: decrease by 10%";
        Lang.LEFT_CLICK = "�eLeft click: increase by 10%";
        Lang.RANDOM_MUSIC = ChatColor.DARK_AQUA + "Random music";
        Lang.STOP = ChatColor.RED + "Stop the music";
        Lang.MUSIC_STOPPED = ChatColor.GREEN + "Music stopped.";
        Lang.ENABLE = "Enable";
        Lang.DISABLE = "Disable";
        Lang.ENABLED = "Enabled";
        Lang.DISABLED = "Disabled";
        Lang.SHUFFLE_MODE = "the shuffle mode";
        Lang.LOOP_MODE = "the loop mode";
        Lang.CONNEXION_MUSIC = "music when connecting";
        Lang.PARTICLES = "particles";
        Lang.MUSIC_PLAYING = ChatColor.GREEN + "Music while playing:";
        Lang.INCORRECT_SYNTAX = ChatColor.RED + "Incorrect syntax.";
        Lang.RELOAD_LAUNCH = ChatColor.GREEN + "Trying to reload.";
        Lang.RELOAD_FINISH = ChatColor.GREEN + "Reload finished.";
        Lang.AVAILABLE_COMMANDS = ChatColor.GREEN + "Available commands:";
        Lang.INVALID_NUMBER = ChatColor.RED + "Invalid number.";
        Lang.PLAYER_MUSIC_STOPPED = ChatColor.GREEN + "Music stopped for player: �b";
        Lang.IN_PLAYLIST = ChatColor.BLUE + "�oIn Playlist";
        Lang.PLAYLIST_ITEM = ChatColor.LIGHT_PURPLE + "Playlists";
        Lang.OPTIONS_ITEM = ChatColor.AQUA + "Options";
        Lang.MENU_ITEM = ChatColor.GOLD + "Return to menu";
        Lang.CLEAR_PLAYLIST = ChatColor.RED + "Clear the current playlist";
        Lang.NEXT_ITEM = ChatColor.YELLOW + "Next song";
        Lang.CHANGE_PLAYLIST = ChatColor.GOLD + "�lSwitch playlist: �r";
        Lang.PLAYLIST = ChatColor.DARK_PURPLE + "Playlist";
        Lang.FAVORITES = ChatColor.DARK_RED + "Favorites";
        Lang.RADIO = ChatColor.DARK_AQUA + "Radio";
        Lang.UNAVAILABLE_RADIO = ChatColor.RED + "This action is unavailable while listening to the radio.";
        Lang.NONE = ChatColor.RED + "none";
    }
    
    public static void saveFile(final YamlConfiguration cfg, final File file) throws IllegalArgumentException, IllegalAccessException, IOException {
        Field[] declaredFields;
        for (int length = (declaredFields = Lang.class.getDeclaredFields()).length, i = 0; i < length; ++i) {
            final Field f = declaredFields[i];
            if (!cfg.contains(f.getName())) {
                cfg.set(f.getName(), f.get(null));
            }
        }
        cfg.save(file);
    }
    
    public static void loadFromConfig(final YamlConfiguration cfg) {
        for (final String key : cfg.getValues(false).keySet()) {
            try {
                Lang.class.getDeclaredField(key).set(key, cfg.get(key));
            }
            catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                //final Exception ex;
                //final Exception e = ex;
                JukeBox.getInstance().getLogger().warning("Error when loading language value \"" + key + "\".");
                ex.printStackTrace();
            }
        }
    }
}
