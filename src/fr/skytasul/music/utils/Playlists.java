// 
// Decompiled by Procyon v0.5.36
// 

package fr.skytasul.music.utils;

import fr.skytasul.music.JukeBoxInventory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Playlists
{
    PLAYLIST("PLAYLIST", 0, Lang.PLAYLIST, JukeBoxInventory.item(Material.JUKEBOX, String.valueOf(Lang.CHANGE_PLAYLIST) + Lang.PLAYLIST, new String[0])), 
    FAVORITES("FAVORITES", 1, Lang.FAVORITES, JukeBoxInventory.item(Material.NOTE_BLOCK, String.valueOf(Lang.CHANGE_PLAYLIST) + Lang.FAVORITES, new String[0])), 
    RADIO("RADIO", 2, Lang.RADIO, JukeBoxInventory.radioItem);
    
    public final ItemStack item;
    public final String name;
    
    private Playlists(final String s, final int n, final String name, final ItemStack item) {
        this.item = item;
        this.name = name;
    }
    
    public static int indexOf(final Playlists playlist) {
        for (int i = 0; i < values().length; ++i) {
            if (values()[i] == playlist) {
                return i;
            }
        }
        return -1;
    }
}
