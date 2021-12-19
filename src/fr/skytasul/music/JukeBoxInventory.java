package fr.skytasul.music;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import fr.skytasul.music.utils.Playlists;
import org.bukkit.inventory.InventoryHolder;
import java.util.Random;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import fr.skytasul.music.utils.Lang;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.event.Listener;
import ru.komiss77.utils.ItemBuilder;

public class JukeBoxInventory implements Listener
{
    public static int version;
    public static List<String> discs13;
    public static List<String> discs8;
    private static ItemStack stopItem;
    private static ItemStack laterItem;
    private static ItemStack nextItem;
    private static ItemStack menuItem;
    private static ItemStack toggleItem;
    private static ItemStack randomItem;
    private static ItemStack playlistMenuItem;
    private static ItemStack optionsMenuItem;
    private static ItemStack nextSongItem;
    private static ItemStack clearItem;
    private static Material particles;
    private static Material sign;
    private static Material lead;
    private static List<String> playlistLore;
    private Material[] discs;
    private UUID id;
    public PlayerData pdata;
    private int page;
    private ItemsMenu menu;
    private Inventory inv;
    public static final ItemStack radioItem;
    
    static {
        JukeBoxInventory.version = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].split("_")[1]);
        JukeBoxInventory.discs13 = new ArrayList<String>(Arrays.asList("MUSIC_DISC_11", "MUSIC_DISC_13", "MUSIC_DISC_BLOCKS", "MUSIC_DISC_CAT", "MUSIC_DISC_CHIRP", "MUSIC_DISC_FAR", "MUSIC_DISC_MALL", "MUSIC_DISC_MELLOHI", "MUSIC_DISC_STAL", "MUSIC_DISC_STRAD", "MUSIC_DISC_WAIT", "MUSIC_DISC_WARD"));
        JukeBoxInventory.discs8 = new ArrayList<String>(Arrays.asList("RECORD_10", "RECORD_11", "RECORD_12", "RECORD_3", "RECORD_4", "RECORD_5", "RECORD_6", "RECORD_7", "RECORD_8", "RECORD_9", "GOLD_RECORD", "GREEN_RECORD"));
        JukeBoxInventory.stopItem = item(Material.BARRIER, Lang.STOP, new String[0]);
        JukeBoxInventory.laterItem = item(Material.ARROW, Lang.LATER_PAGE, new String[0]);
        JukeBoxInventory.nextItem = item(Material.ARROW, Lang.NEXT_PAGE, new String[0]);
        JukeBoxInventory.menuItem = item(Material.TRAPPED_CHEST, Lang.MENU_ITEM, new String[0]);
        JukeBoxInventory.toggleItem = item((JukeBoxInventory.version < 9) ? Material.STONE_BUTTON : Material.valueOf("END_CRYSTAL"), Lang.TOGGLE_PLAYING, new String[0]);
        JukeBoxInventory.randomItem = item(Material.valueOf((JukeBoxInventory.version > 12) ? "FIRE_CHARGE" : "FIREBALL"), Lang.RANDOM_MUSIC, new String[0]);
        JukeBoxInventory.playlistMenuItem = item(Material.CHEST, Lang.PLAYLIST_ITEM, new String[0]);
        JukeBoxInventory.optionsMenuItem = item(Material.valueOf((JukeBoxInventory.version > 12) ? "COMPARATOR" : "REDSTONE_COMPARATOR"), Lang.OPTIONS_ITEM, new String[0]);
        JukeBoxInventory.nextSongItem = item(Material.FEATHER, Lang.NEXT_ITEM, new String[0]);
        JukeBoxInventory.clearItem = item(Material.LAVA_BUCKET, Lang.CLEAR_PLAYLIST, new String[0]);
        JukeBoxInventory.particles = ((JukeBoxInventory.version < 13) ? Material.valueOf("FIREWORK") : Material.valueOf("FIREWORK_ROCKET"));
        JukeBoxInventory.sign = ((JukeBoxInventory.version < 14) ? Material.valueOf("SIGN") : Material.valueOf("OAK_SIGN"));
        JukeBoxInventory.lead = ((JukeBoxInventory.version < 13) ? Material.valueOf("LEASH") : Material.valueOf("LEAD"));
        JukeBoxInventory.playlistLore = Arrays.asList("", Lang.IN_PLAYLIST);
        
        
        radioItem = new ItemBuilder(Material.PLAYER_HEAD)
                .name(String.valueOf(Lang.CHANGE_PLAYLIST) + Lang.RADIO)
                .setCustomHeadTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ4YThjNTU4OTFkZWM3Njc2NDQ0OWY1N2JhNjc3YmUzZWU4OGEwNjkyMWNhOTNiNmNjN2M5NjExYTdhZiJ9fX0=")
                .build();
        
       /* final GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
        final PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        } else {
            propertyMap.put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ4YThjNTU4OTFkZWM3Njc2NDQ0OWY1N2JhNjc3YmUzZWU4OGEwNjkyMWNhOTNiNmNjN2M5NjExYTdhZiJ9fX0="));
        }
        ItemStack item;
        if (JukeBoxInventory.version < 13) {
            item = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short)3);
        }
        else {
            item = new ItemStack(Material.valueOf("PLAYER_HEAD"));
        }
        
        ItemMeta headMeta = item.getItemMeta();
        try {
            final Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        }
        catch (ReflectiveOperationException e) {
            e.printStackTrace();
            JukeBox.getInstance().getLogger().severe("An error occured during initialization of Radio item. Please report it to an administrator !");
            item = new ItemStack(Material.TORCH);
            headMeta = item.getItemMeta();
        }
        headMeta.setDisplayName(String.valueOf(Lang.CHANGE_PLAYLIST) + Lang.RADIO);
        item.setItemMeta(headMeta);
        radioItem = item;*/
    }
    
    public JukeBoxInventory(final Player p) {
        this.page = 0;
        this.menu = ItemsMenu.DEFAULT;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)JukeBox.getInstance());
        this.id = p.getUniqueId();
        this.pdata = PlayerData.players.get(p.getUniqueId());
        this.pdata.linked = this;
        final Random ran = new Random();
        this.discs = new Material[JukeBox.getSongs().size()];
        for (int i = 0; i < this.discs.length; ++i) {
            this.discs[i] = Material.valueOf((JukeBoxInventory.version > 12) ? JukeBoxInventory.discs13.get(ran.nextInt(12)) : JukeBoxInventory.discs8.get(ran.nextInt(12)));
        }
        (this.inv = Bukkit.createInventory((InventoryHolder)null, 54, Lang.INV_NAME)).setItem(52, JukeBoxInventory.laterItem);
        this.inv.setItem(53, JukeBoxInventory.nextItem);
        this.setSongsPage();
        this.openInventory(p);
    }
    
    public void openInventory(final Player p) {
        this.inv = p.openInventory(this.inv).getTopInventory();
        this.menu = ItemsMenu.DEFAULT;
        this.setItemsMenu();
    }
    
    public void setSongsPage() {
        for (int i = 0; i < 45; ++i) {
            this.inv.setItem(i, (ItemStack)null);
        }
        if (this.pdata.getPlaylistType() == Playlists.RADIO) {
            return;
        }
        if (JukeBox.getSongs().isEmpty()) {
            return;
        }
        for (int i = 0; i < 45; ++i) {
            final Song s = JukeBox.getSongs().get(this.page * 45 + i);
            final ItemStack is = this.getSongItem(s);
            if (this.pdata.isInPlaylist(s)) {
                loreAdd(is, JukeBoxInventory.playlistLore);
            }
            this.inv.setItem(i, is);
            if (JukeBox.getSongs().size() - 1 == this.page * 45 + i) {
                break;
            }
        }
    }
    
    public void setItemsMenu() {
        for (int i = 45; i < 52; ++i) {
            this.inv.setItem(i, (ItemStack)null);
        }
        if (this.menu != ItemsMenu.DEFAULT) {
            this.inv.setItem(45, JukeBoxInventory.menuItem);
        }
        switch (this.menu) {
            case DEFAULT: {
                this.inv.setItem(45, JukeBoxInventory.stopItem);
                if (this.pdata.songPlayer != null) {
                    this.inv.setItem(46, JukeBoxInventory.toggleItem);
                }
                if (!JukeBox.getSongs().isEmpty()) {
                    this.inv.setItem(47, JukeBoxInventory.randomItem);
                }
                this.inv.setItem(49, JukeBoxInventory.playlistMenuItem);
                this.inv.setItem(50, JukeBoxInventory.optionsMenuItem);
                break;
            }
            case OPTIONS: {
                this.inv.setItem(47, item(Material.BEACON, "�cerror", Lang.RIGHT_CLICK, Lang.LEFT_CLICK));
                //if (JukeBox.particles) {
                //    this.inv.setItem(48, item(JukeBoxInventory.particles, "�cerror", new String[0]));
                //}
                this.inv.setItem(49, item(JukeBoxInventory.sign, "�cerror", new String[0]));
                this.inv.setItem(50, item(Material.BLAZE_POWDER, "�cerror", new String[0]));
                this.inv.setItem(51, item(JukeBoxInventory.lead, "�cerror", new String[0]));
                this.volumeItem();
                this.shuffleItem();
                this.joinItem();
                this.particlesItem();
                this.repeatItem();
                break;
            }
            case PLAYLIST: {
                this.inv.setItem(47, JukeBoxInventory.nextSongItem);
                this.inv.setItem(48, JukeBoxInventory.clearItem);
                this.inv.setItem(50, this.pdata.getPlaylistType().item);
                break;
            }
        }
    }
    
    public UUID getID() {
        return this.id;
    }
    
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        final Player p = (Player)e.getWhoClicked();
        if (e.getClickedInventory() != this.inv) {
            return;
        }
        if (e.getCurrentItem() == null) {
            return;
        }
        if (!p.getUniqueId().equals(this.id)) {
            return;
        }
        e.setCancelled(true);
        final int slot = e.getSlot();
        if (e.getCurrentItem().getType().name().contains("RECORD") || e.getCurrentItem().getType().name().contains("DISC")) {
            final Song s = JukeBox.getSongs().get(this.page * 45 + slot);
            if (e.getClick() == ClickType.MIDDLE) {
                if (this.pdata.isInPlaylist(s)) {
                    this.pdata.removeSong(s);
                    this.inv.setItem(slot, this.getSongItem(s));
                }
                else if (this.pdata.addSong(s, false)) {
                    this.inv.setItem(slot, loreAdd(this.getSongItem(s), JukeBoxInventory.playlistLore));
                }
            }
            else if (this.pdata.playSong(s)) {
                this.inv.setItem(slot, loreAdd(this.getSongItem(s), JukeBoxInventory.playlistLore));
            }
            return;
        }
        Label_0867: {
            switch (slot) {
                case 52:
                case 53: {
                    if (JukeBox.maxPage == 0) {
                        break;
                    }
                    if (slot == 53) {
                        if (this.page == JukeBox.maxPage - 1) {
                            break;
                        }
                        ++this.page;
                    }
                    else if (slot == 52) {
                        if (this.page == 0) {
                            return;
                        }
                        --this.page;
                    }
                    this.setSongsPage();
                    break;
                }
                default: {
                    if (slot == 45) {
                        if (this.menu == ItemsMenu.DEFAULT) {
                            this.pdata.stopPlaying(true);
                            this.inv.setItem(46, (ItemStack)null);
                        }
                        else {
                            this.menu = ItemsMenu.DEFAULT;
                            this.setItemsMenu();
                        }
                        return;
                    }
                    switch (this.menu) {
                        case DEFAULT: {
                            switch (slot) {
                                case 46: {
                                    this.pdata.songPlayer.setPlaying(!this.pdata.songPlayer.isPlaying());
                                    break;
                                }
                                case 47: {
                                    this.pdata.playRandom();
                                    break;
                                }
                                case 49: {
                                    this.menu = ItemsMenu.PLAYLIST;
                                    this.setItemsMenu();
                                    break;
                                }
                                case 50: {
                                    if (this.pdata.getPlaylistType() == Playlists.RADIO) {
                                        JukeBox.sendMessage(p, Lang.UNAVAILABLE_RADIO);
                                        return;
                                    }
                                    this.menu = ItemsMenu.OPTIONS;
                                    this.setItemsMenu();
                                    break;
                                }
                            }
                            break Label_0867;
                        }
                        case OPTIONS: {
                            switch (slot) {
                                case 47: {
                                    if (e.getClick() == ClickType.RIGHT) {
                                        this.pdata.setVolume((byte)(this.pdata.getVolume() - 10));
                                    }
                                    if (e.getClick() == ClickType.LEFT) {
                                        this.pdata.setVolume((byte)(this.pdata.getVolume() + 10));
                                    }
                                    if (this.pdata.getVolume() < 0) {
                                        this.pdata.setVolume(0);
                                    }
                                    if (this.pdata.getVolume() > 100) {
                                        this.pdata.setVolume(100);
                                        break;
                                    }
                                    break;
                                }
                                case 48: {
                                    this.pdata.setParticles(!this.pdata.hasParticles());
                                    break;
                                }
                                case 49: {
                                    if (!JukeBox.autoJoin) {
                                        this.pdata.setJoinMusic(!this.pdata.hasJoinMusic());
                                        break;
                                    }
                                    break;
                                }
                                case 50: {
                                    this.pdata.setShuffle(!this.pdata.isShuffle());
                                    break;
                                }
                                case 51: {
                                    this.pdata.setRepeat(!this.pdata.isRepeatEnabled());
                                    break;
                                }
                            }
                            break Label_0867;
                        }
                        case PLAYLIST: {
                            switch (slot) {
                                case 47: {
                                    this.pdata.nextSong();
                                    break Label_0867;
                                }
                                case 48: {
                                    this.pdata.clearPlaylist();
                                    this.setSongsPage();
                                    break Label_0867;
                                }
                                case 50: {
                                    this.pdata.nextPlaylist();
                                    this.setSongsPage();
                                    break Label_0867;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public ItemStack getSongItem(final Song s) {
        final ItemStack is = item(this.discs[JukeBox.getSongs().indexOf(s)], JukeBox.getItemName(s), new String[0]);
        if (!s.getDescription().isEmpty()) {
            loreAdd(is, splitOnSpace(s.getDescription(), 30));
        }
        return is;
    }
    
    public void volumeItem() {
        if (this.menu == ItemsMenu.OPTIONS) {
            name(this.inv.getItem(47), String.valueOf(Lang.VOLUME) + this.pdata.getVolume() + "%");
        }
    }
    
    public void particlesItem() {
        if (this.menu != ItemsMenu.OPTIONS) {
            return;
        }
   //     if (!JukeBox.particles) {
   //         return;
   //     }
   //     if (!JukeBox.particles) {
            this.inv.setItem(48, (ItemStack)null);
   //     }
     //   name(this.inv.getItem(48), ChatColor.AQUA + (this.pdata.hasParticles() ? Lang.DISABLE : Lang.ENABLE) + " " + Lang.PARTICLES);
    }
    
    public void joinItem() {
        if (this.menu == ItemsMenu.OPTIONS) {
            name(this.inv.getItem(49), ChatColor.GREEN + (this.pdata.hasJoinMusic() ? Lang.DISABLE : Lang.ENABLE) + " " + Lang.CONNEXION_MUSIC);
        }
    }
    
    public void shuffleItem() {
        if (this.menu == ItemsMenu.OPTIONS) {
            name(this.inv.getItem(50), ChatColor.YELLOW + (this.pdata.isShuffle() ? Lang.DISABLE : Lang.ENABLE) + " " + Lang.SHUFFLE_MODE);
        }
    }
    
    public void repeatItem() {
        if (this.menu == ItemsMenu.OPTIONS) {
            name(this.inv.getItem(51), ChatColor.GOLD + (this.pdata.isRepeatEnabled() ? Lang.DISABLE : Lang.ENABLE) + " " + Lang.LOOP_MODE);
        }
    }
    
    public void playingStarted() {
        if (this.menu == ItemsMenu.DEFAULT) {
            this.inv.setItem(46, JukeBoxInventory.toggleItem);
        }
    }
    
    public void playingStopped() {
        if (this.menu == ItemsMenu.DEFAULT) {
            this.inv.setItem(46, (ItemStack)null);
        }
    }
    
    public void playlistItem() {
        if (this.menu == ItemsMenu.PLAYLIST) {
            this.inv.setItem(50, this.pdata.getPlaylistType().item);
        }
    }
    
    public void songItem(final int id) {
        if (id <= this.page * 45 || id >= (this.page + 1) * 45 || this.pdata.getPlaylistType() == Playlists.RADIO) {
            return;
        }
        final Song song = JukeBox.getSongs().get(id);
        final ItemStack is = this.getSongItem(song);
        if (this.pdata.isInPlaylist(song)) {
            loreAdd(is, JukeBoxInventory.playlistLore);
        }
        this.inv.setItem(id - this.page * 45, is);
    }
    
    public static ItemStack item(final Material type, final String name, final String... lore) {
        final ItemStack is = new ItemStack(type);
        final ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore((List)Arrays.asList(lore));
        im.addItemFlags(ItemFlag.values());
        is.setItemMeta(im);
        return is;
    }
    
    public static ItemStack loreAdd(final ItemStack is, final List<String> lore) {
        final ItemMeta im = is.getItemMeta();
        List<String> ls = (List<String>)im.getLore();
        if (ls == null) {
            ls = new ArrayList<String>();
        }
        ls.addAll(lore);
        im.setLore((List)ls);
        is.setItemMeta(im);
        return is;
    }
    
    public static ItemStack name(final ItemStack is, final String name) {
        final ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return is;
    }
    
    public static List<String> splitOnSpace(final String string, final int minSize) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        final List<String> ls = new ArrayList<>();
        if (string.length() <= minSize) {
            ls.add(string);
            return ls;
        }
        
        String[] splitByWholeSeparator = string.split("\\n");
        //for (int length = (splitByWholeSeparator = StringUtils.splitByWholeSeparator(string, "\\n")).length, j = 0; j < length; ++j) {
        for (String str : splitByWholeSeparator) {
            //final String str = splitByWholeSeparator[j];
            int lastI = 0;
            int ic = 0;
            for (int i = 0; i < str.length(); ++i) {
                String color = "";
                if (!ls.isEmpty()) {
                    color = ChatColor.getLastColors((String)ls.get(ls.size() - 1));
                }
                if (ic >= minSize) {
                    if (str.charAt(i) == ' ') {
                        ls.add(String.valueOf(color) + str.substring(lastI, i));
                        ic = 0;
                        lastI = i + 1;
                    }
                    else if (i + 1 == str.length()) {
                        ls.add(String.valueOf(color) + str.substring(lastI, i + 1));
                    }
                }
                else if (str.length() - lastI <= minSize) {
                    ls.add(String.valueOf(color) + str.substring(lastI, str.length()));
                    break;
                }
                ++ic;
            }
        }
        return ls;
    }
    
    enum ItemsMenu
    {
        DEFAULT("DEFAULT", 0), 
        OPTIONS("OPTIONS", 1), 
        PLAYLIST("PLAYLIST", 2);
        
        private ItemsMenu(final String s, final int n) {
        }
    }
}
