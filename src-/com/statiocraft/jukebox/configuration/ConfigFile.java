package com.statiocraft.jukebox.configuration;

import com.statiocraft.jukebox.fromapi.util.ItemUtil;
import com.statiocraft.jukebox.fromapi.util.NumberUtil;
import com.statiocraft.jukebox.fromapi.util.StringUtil;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ConfigFile {

    private File f;
    private final String[] d;
    private boolean a = false;
    public final ItemStack is_mpb_;
    public final ItemStack is_mpf_;
    public final ItemStack is_mpc_;
    public final ItemStack is_mmd_;
    public final ItemStack is_mmR_;
    public final ItemStack is_mmr_;
    public final ItemStack is_mms_;
    public final ItemStack is_pms_;
    public final ItemStack is_pmr_;
    public final ItemStack is_pmR_;
    public final ItemStack is_pml_;
    public final String s_meT_;
    public final String s_muD_;
    public final String s_muS_;
    public final String s_npt_;
    public final String s_Re_;
    public final String s_Rd_;
    public final String s_RE_;
    public final String s_Rs_;
    public final String s_se_;
    public final String s_sd_;
    public final String s_ss_;
    public final String s_ig_;
    public final int i_dms_;
    public final int i_aoj_;
    public final boolean b_usnfd_;
    public final boolean b_pmRe_;
    public final boolean b_pmRc_;
    public final boolean b_pmse_;
    public final boolean b_pmsc_;
    public final boolean b_pmre_;
    public final boolean b_pmrc_;
    public final boolean b_pmle_;
    public final boolean b_pmlc_;

    public ConfigFile(File file) throws IOException {
        this.f = file;
        char[] c = new char[4096];
        StringBuffer s = new StringBuffer();
        FileReader f = new FileReader(file);

        int l;

        while ((l = f.read(c)) > 0) {
            s.append(c, 0, l);
        }

        try {
            f.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.d = StringUtil.split(s.toString(), '\n');
        this.is_mpb_ = (ItemStack) this.g("item_menu_page_backward", ItemUtil.create(Material.ENDER_PEARL, 1, "§5§lPrevious Page"));
        this.is_mpf_ = (ItemStack) this.g("item_menu_page_forward", ItemUtil.create(Material.ENDER_EYE, 1, "§6§lNext Page"));
        this.is_mpc_ = (ItemStack) this.g("item_menu_page_close", ItemUtil.create(Material.MAGMA_CREAM, 1, "§4§lClose Page"));
        this.is_mmd_ = (ItemStack) this.g("331:0 1 name:&c&lВыключить", ItemUtil.create(Material.REDSTONE, 1, "§c§lВыключить"));
        this.is_mmR_ = (ItemStack) this.g("item_menu_music_radio", ItemUtil.create(Material.DIAMOND, 1, "§3§lServer Radio"));
        this.is_mmr_ = (ItemStack) this.g("item_menu_music_random", ItemUtil.create(Material.JUKEBOX, 1, "§2§lRandom"));
        this.is_mms_ = (ItemStack) this.g("item_menu_music_shuffle", ItemUtil.create(Material.COBWEB, 1, "§9§lShuffle"));
        this.is_pmR_ = (ItemStack) this.g("item_player_music_radio", ItemUtil.create(Material.DIAMOND, 1, "§3§lToggle Radio"));
        this.is_pmr_ = (ItemStack) this.g("item_player_music_random", ItemUtil.create(Material.JUKEBOX, 1, "§6§lPlay Random Song"));
        this.is_pms_ = (ItemStack) this.g("item_player_music_shuffle", ItemUtil.create(Material.MUSIC_DISC_MELLOHI, 1, "§a§lToggle Music"));
        this.is_pml_ = (ItemStack) this.g("item_player_music_list", ItemUtil.create(Material.PAPER, 1, "§5§lMusic"));
        this.s_meT_ = (String) this.g("string_menu_title", "§5Music List  -  Page §d#CURRENT§5/#TOTAL");
        this.s_muD_ = (String) this.g("string_music_disable", "§cMusic Disabled");
        this.s_muS_ = (String) this.g("string_music_shuffle", "§6Shuffle-Mode Enabled");
        this.s_npt_ = (String) this.g("string_music_nowplaying", "§aNow playing track§2: #TRACK");
        this.s_Re_ = (String) this.g("string_radio_enable", "§aRadio Enabled");
        this.s_Rd_ = (String) this.g("string_radio_disable", "§cRadio Disabled");
        this.s_RE_ = (String) this.g("string_radio_alreadyenabled", "§6Your radio has already been turned on");
        this.s_Rs_ = (String) this.g("string_radio_skip", "§6Skipped to the next radio-song");
        this.s_se_ = (String) this.g("string_shuffle_enable", "&aShuffle Enabled");
        this.s_sd_ = (String) this.g("string_shuffle_disable", "&cShuffle Disabled");
        this.s_ss_ = (String) this.g("string_shuffle_skip", "&6Skipped to the next song");
        this.s_ig_ = (String) this.g("string_item_give", "§aSuccesfully sent item §2#ITEM §ato player §2#PLAYER");
        this.i_dms_ = ((Integer) this.g("other_default_menu_size", Integer.valueOf(0))).intValue();
        this.i_aoj_ = ((Integer) this.g("other_action_on_join", Integer.valueOf(0))).intValue();
        this.b_usnfd_ = ((Boolean) this.g("other_use_song_names_for_discs", Boolean.valueOf(true))).booleanValue();
        this.b_pmRe_ = ((Boolean) this.g("other_item_radio_enabled", Boolean.valueOf(true))).booleanValue();
        this.b_pmRc_ = ((Boolean) this.g("other_item_radio_candrop", Boolean.valueOf(false))).booleanValue();
        this.b_pmre_ = ((Boolean) this.g("other_item_random_enabled", Boolean.valueOf(true))).booleanValue();
        this.b_pmrc_ = ((Boolean) this.g("other_item_random_candrop", Boolean.valueOf(false))).booleanValue();
        this.b_pmse_ = ((Boolean) this.g("other_item_shuffle_enabled", Boolean.valueOf(true))).booleanValue();
        this.b_pmsc_ = ((Boolean) this.g("other_item_shuffle_candrop", Boolean.valueOf(false))).booleanValue();
        this.b_pmle_ = ((Boolean) this.g("other_item_list_enabled", Boolean.valueOf(true))).booleanValue();
        this.b_pmlc_ = ((Boolean) this.g("other_item_list_candrop", Boolean.valueOf(false))).booleanValue();
        if (this.a) {
            this.save();
        }

    }

    public boolean save() {
        return this.save(this.f);
    }

    private boolean save(File f) {
        String[] s = new String[] { "=====================================================", "=                    StatioCraft                    =", "=                      JukeBox                      =", "=---------------------------------------------------=", "=                Plugin Configuration               =", "=====================================================", " ", "# The size of the musiclist GUI", "# NOTE: make sure the size is at least 9", "# also make sure the size can be divided by 9", "# (for instance: 9, 18, 27, 36, 45, 54, etc.)", "# The specified size does NOT include the top-row", "# containing navigation items", "# If the requirements above are not met, the plugin", "# will use it\'s default-value", "other_default_menu_size: " + this.i_dms_, " ", "# The action which will be performed when a player", "# joins the server", "# 3 = turn on server-radio", "# 2 = play random song", "# 1 = enable shuffle-mode", "# 0 = do nothing", "other_action_on_join: " + this.i_aoj_, " ", "# The message shown in the music-list inventory title", "# Use #CURRENT to insert the current pagenumber", "# Use #TOTAL to insert the total amount of pages", "string_menu_title: " + this.s_meT_, " ", "# The message shown when someone disables a music track", "string_music_disable: " + this.s_muD_, " ", "# The message shown when someone enables shuffle-mode", "string_music_shuffle: " + this.s_muS_, " ", "# The message shown when a new track is starting", "# Use #TRACK to insert the song name", "string_music_nowplaying: " + this.s_npt_, " ", "# The message shown when someone enables radio-mode", "string_radio_enable: " + this.s_Re_, " ", "# The message shown when someone disables radio-mode", "string_radio_disable: " + this.s_Rd_, " ", "# The message shown when someone already has radio-mode enabled", "string_radio_alreadyenabled: " + this.s_RE_, " ", "# The message shown when a radio-song is being skipped", "string_radio_skip: " + this.s_Rs_, " ", "# The message shown when someone enables shuffle-mode", "string_shuffle_enable: " + this.s_se_, " ", "# The message shown when someone disables shuffle-mode", "string_shuffle_disable: " + this.s_sd_, " ", "# The message shown when someone skips a track in shuffle-mode", "string_shuffle_skip: " + this.s_ss_, " ", "# The message shown when an item is given", "# Use #ITEM to insert the item name", "# Use #PLAYER to insert the player name", "string_item_give: " + this.s_ig_, " ", " ", " ", "# The item used inside the GUI to move one page backward", "item_menu_page_backward: " + this.f(this.is_mpb_), " ", "# The item used inside the GUI to move one page forward", "item_menu_page_forward: " + this.f(this.is_mpf_), " ", "# The item used inside the GUI to close the page", "item_menu_page_close: " + this.f(this.is_mpc_), " ", "# The item used inside the GUI to disable music", "item_menu_music_disable: " + this.f(this.is_mmd_), " ", "# The item used inside the GUI to enable the server radio", "item_menu_music_radio: " + this.f(this.is_mmR_), " ", "# The item used inside the GUI to enable a random song", "item_menu_music_random: " + this.f(this.is_mmr_), " ", "# The item used inside the GUI to enable shuffle-mode", "item_menu_music_shuffle: " + this.f(this.is_mms_), " ", "# The item which can be held by a player and used to enable the server radio", "item_player_music_radio: " + this.f(this.is_pmR_), "# Whether the item above is enabled", "other_item_radio_enabled: " + this.b_pmRe_, "# Whether the item above can be dropped", "other_item_radio_candrop: " + this.b_pmRc_, " ", "# The item which can be held by a player and used to enable a random song", "item_player_music_random: " + this.f(this.is_pmr_), "# Whether the item above is enabled", "other_item_random_enabled: " + this.b_pmre_, "# Whether the item above can be dropped", "other_item_random_candrop: " + this.b_pmrc_, " ", "# The item which can be held by a player and used to enable shuffle-mode", "item_player_music_shuffle: " + this.f(this.is_pms_), "# Whether the item above is enabled", "other_item_shuffle_enabled: " + this.b_pmse_, "# Whether the item above can be dropped", "other_item_shuffle_candrop: " + this.b_pmsc_, " ", "# The item which can be held by a player and used to open the music GUI", "item_player_music_list: " + this.f(this.is_pml_), "# Whether the item above is enabled", "other_item_list_enabled: " + this.b_pmle_, "# Whether the item above can be dropped", "other_item_list_candrop: " + this.b_pmlc_, " ", "# When set to true the plugin will use the name stored inside a song-file", "# When set to false the plugin will use the name of a song-file itself", "other_use_song_names_for_discs: " + this.b_usnfd_};

        try {
            FileWriter e = new FileWriter(f);

            e.write(StringUtil.toFormattedList(s, 0, "\n").toCharArray());

            try {
                e.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return true;
        } catch (Exception exception1) {
            exception1.printStackTrace();
            return false;
        }
    }

    private Object g(String p, Object d) {
        try {
            Object o = null;
            String[] astring = this.d;
            int i = this.d.length;

            for (int j = 0; j < i; ++j) {
                String S = astring[j];
                String[] e;

                if ((e = S.split(": "))[0].equals(p)) {
                    e[1] = this.s(StringUtil.toFormattedList(e, 1, ": "));
                    if (d instanceof String) {
                        o = e[1];
                    } else if (d instanceof Integer) {
                        o = NumberUtil.toInt(e[1]);
                    } else if (d instanceof Boolean) {
                        if (StringUtil.isConfirming(e[1])) {
                            o = Boolean.valueOf(true);
                        } else if (StringUtil.isRejecting(e[1])) {
                            o = Boolean.valueOf(false);
                        } else {
                            o = d;
                        }
                    } else if (d instanceof ItemStack) {
                        o = this.f(e[1]);
                    }

                    return o;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.a = true;
        return d;
    }

    private String s(String s) {
        char[] c = s.toCharArray();

        if (Character.getNumericValue(c[c.length - 1]) != -1) {
            return s;
        } else {
            StringBuilder b = new StringBuilder();

            for (int n = 0; n < c.length - 1; ++n) {
                b.append(c[n]);
            }

            return b.toString();
        }
    }

    private String f(ItemStack i) {
        if (i == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();

            sb.append(i.getTypeId() + ":" + i.getData().getData());
            sb.append(" ");
            sb.append(i.getAmount());
            sb.append(" ");
            sb.append("name:" + i.getItemMeta().getDisplayName().replaceAll(" ", "_"));
            sb.append(" ");
            if (i.getItemMeta().getLore() != null && !i.getItemMeta().getLore().isEmpty()) {
                sb.append("lore:" + StringUtil.toFormattedList(i.getItemMeta().getLore(), 0, "|").replaceAll(" ", "_"));
                sb.append(" ");
            }

            Iterator iterator = i.getEnchantments().keySet().iterator();

            while (iterator.hasNext()) {
                Enchantment e = (Enchantment) iterator.next();

                sb.append(e.getName().toUpperCase() + ":" + i.getEnchantmentLevel(e));
            }

            return sb.toString();
        }
    }

    private ItemStack f(String l) {
        if (!l.isEmpty() && l != null) {
            String[] s = StringUtil.split(l, ' ');

            if (s.length == 0) {
                return null;
            } else {
                ItemUtil.BlockId b = ItemUtil.fromCode(s[0]);

                if (b != null && b.getType() != null) {
                    ItemStack i = b.toItemStack();
                    int n = 0;

                    while (s.length > n + 1) {
                        ++n;
                        if (n == 1) {
                            Integer integer = NumberUtil.toInt(s[n]);

                            if (integer != null) {
                                i.amount(n);
                            }
                        } else {
                            String[] a = StringUtil.split(s[n], ':');

                            if (a.length >= 2) {
                                if (a[0].equalsIgnoreCase("name")) {
                                    i = ItemUtil.rename(i, StringUtil.convertCodes(StringUtil.toFormattedList(a, 1, ":")).replaceAll("_", " "));
                                } else if (a[0].equalsIgnoreCase("lore")) {
                                    i = ItemUtil.setLore(i, StringUtil.split(StringUtil.convertCodes(StringUtil.toFormattedList(a, 1, ":")).replaceAll("_", " "), '|'));
                                } else {
                                    Enchantment[] aenchantment;
                                    int i2 = (aenchantment = Enchantment.values()).length;

                                    for (int j = 0; j < i2; ++j) {
                                        Enchantment e = aenchantment[j];

                                        if (s[0].equalsIgnoreCase(e.getName())) {
                                            Integer v = NumberUtil.toInt(s[1]);

                                            if (v == null) {
                                                v = Integer.valueOf(1);
                                            }

                                            i = ItemUtil.enchant(i, e, v.intValue());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    return i;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }
}
