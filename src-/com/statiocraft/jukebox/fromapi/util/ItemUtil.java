package com.statiocraft.jukebox.fromapi.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class ItemUtil {

    /** @deprecated */
    @Deprecated
    public static ItemUtil.BlockId fromCode(String code) {
        Material m = Material.AIR;
        byte data = 0;

      //  if (NumberUtil.isInt(code)) {
            m = Material.matchMaterial(code);
            if (m==null) m=Material.MUSIC_DISC_WARD;
      /*  } else {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            boolean b = false;
            char[] achar;
            int i = (achar = code.toCharArray()).length;

            for (int j = 0; j < i; ++j) {
                char c = achar[j];

                if (b) {
                    sb2.append(c);
                } else if (c == 58) {
                    b = true;
                } else {
                    sb1.append(c);
                }
            }

            String s1 = sb1.toString();
            String s2 = sb2.toString();

            if (!NumberUtil.isInteger(s1)) {
                return null;
            }

            m = Material.getMaterial(NumberUtil.toInt(s1).intValue());
            if (NumberUtil.isByte(s2)) {
                data = NumberUtil.toByte(s2).byteValue();
            } else {
                data = 0;
            }
        }*/

        return new ItemUtil.BlockId(m, data);
    }

    public static ItemUtil.BlockId[] fromCodeList(String code) {
        String[] sa = StringUtil.split(code, ',');
        ItemUtil.BlockId[] b = new ItemUtil.BlockId[sa.length];

        for (int n = 0; n < sa.length; ++n) {
            b[n] = fromCode(sa[n]);
        }

        return b;
    }

    public static ItemStack replaceInName(ItemStack item, String replace, String replacement) {
        return rename(item, item.getItemMeta().getDisplayName().replaceAll(replace, replacement));
    }

    public static boolean compare(ItemStack item1, ItemStack item2) {
        if (item1 != null && item2 != null) {
            if (!item1.isSimilar(item2)) {
                return false;
            } else {
                int amount = item1.getAmount();

                item1.amount(item2.getAmount());
                boolean value = item1.equals(item2);

                item1.amount(amount);
                return value;
            }
        } else {
            return false;
        }
    }

    public static ItemStack resize(ItemStack item, int amount) {
        item.amount(amount);
        return item;
    }

    public static ItemStack create(Material material, int amount, byte data, String displayname, String[] lore) {
        if (material == Material.AIR) {
            return new ItemStack(Material.AIR);
        } else {
            ItemStack item = (new MaterialData(material, data)).toItemStack(amount);
            ItemMeta itemMeta = item.getItemMeta();
            ArrayList l = new ArrayList();

            if (displayname != null) {
                itemMeta.setDisplayName(displayname);
            }

            String[] astring = lore;
            int i = lore.length;

            for (int j = 0; j < i; ++j) {
                String s = astring[j];

                if (s != null) {
                    l.add(s);
                }
            }

            itemMeta.setLore(l);
            item.setItemMeta(itemMeta);
            return item;
        }
    }

    public static ItemStack create(Material material, int amount, String displayname, String[] lore) {
        return create(material, amount, (byte) 0, displayname, lore);
    }

    public static ItemStack create(Material material, int amount, String[] lore) {
        return create(material, amount, (byte) 0, lore);
    }

    public static ItemStack create(Material material, int amount, byte data, String[] lore) {
        return create(material, amount, data, (String) null, lore);
    }

    public static ItemStack create(Material material, int amount, byte data, String displayname, String lore1, String lore2, String lore3, String lore4) {
        return create(material, amount, data, displayname, new String[] { lore1, lore2, lore3, lore4});
    }

    public static ItemStack create(Material material) {
        return create(material, 1, (byte) 0, (String) null, (String) null, (String) null, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount) {
        return create(material, amount, (byte) 0, (String) null, (String) null, (String) null, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, String displayname) {
        return create(material, amount, (byte) 0, displayname, (String) null, (String) null, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, String displayname, String lore1) {
        return create(material, amount, (byte) 0, displayname, lore1, (String) null, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, String displayname, String lore1, String lore2) {
        return create(material, amount, (byte) 0, displayname, lore1, lore2, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, String displayname, String lore1, String lore2, String lore3) {
        return create(material, amount, (byte) 0, displayname, lore1, lore2, lore3, (String) null);
    }

    public static ItemStack create(Material material, byte data) {
        return create(material, 1, data, (String) null, (String) null, (String) null, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, byte data) {
        return create(material, amount, data, (String) null, (String) null, (String) null, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, byte data, String displayname) {
        return create(material, amount, data, displayname, (String) null, (String) null, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, byte data, String displayname, String lore1) {
        return create(material, amount, data, displayname, lore1, (String) null, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, byte data, String displayname, String lore1, String lore2) {
        return create(material, amount, data, displayname, lore1, lore2, (String) null, (String) null);
    }

    public static ItemStack create(Material material, int amount, byte data, String displayname, String lore1, String lore2, String lore3) {
        return create(material, amount, data, displayname, lore1, lore2, lore3, (String) null);
    }

    public static ItemStack potion(ItemStack item, PotionType potionType) {
        Potion potion = new Potion(potionType);

        potion.apply(item);
        return item;
    }

    public static ItemStack enchant(ItemStack item, Enchantment enchantment1, int level1, Enchantment enchantment2, int level2, Enchantment enchantment3, int level3) {
        ItemMeta itemMeta = item.getItemMeta();

        if (enchantment1 != null) {
            itemMeta.enchant(enchantment1, level1, true);
        }

        if (enchantment2 != null) {
            itemMeta.enchant(enchantment2, level2, true);
        }

        if (enchantment3 != null) {
            itemMeta.enchant(enchantment3, level3, true);
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack enchant(ItemStack item, Enchantment enchantment1, int level1) {
        return enchant(item, enchantment1, level1, (Enchantment) null, 0, (Enchantment) null, 0);
    }

    public static ItemStack enchant(ItemStack item, Enchantment enchantment1, int level1, Enchantment enchantment2, int level2) {
        return enchant(item, enchantment1, level1, enchantment2, level2, (Enchantment) null, 0);
    }

    public static ItemStack recolourLeather(ItemStack item, Color colour) {
        if (item.getItemMeta() instanceof LeatherArmorMeta) {
            LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();

            lam.setColor(colour);
            item.setItemMeta(lam);
        }

        return item;
    }

    public static ItemStack recolorLeather(ItemStack item, Color color) {
        return recolourLeather(item, color);
    }

    public static ItemStack rename(ItemStack item, String name) {
        ItemMeta im = item.getItemMeta();

        im.setDisplayName(name);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack setLore(ItemStack item, List lore) {
        ItemMeta im = item.getItemMeta();

        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String[] lore) {
        ArrayList l = new ArrayList();

        for (int n = 0; n < lore.length; ++n) {
            l.add(lore[n]);
        }

        return setLore(item, (List) l);
    }

    public static class BlockId {

        private Material type;
        private byte data;

        public BlockId(Material type, byte data) {
            this.type = type;
            this.data = data;
        }

        public BlockId(Material type) {
            this.type = type;
            this.data = 0;
        }

        public Material getType() {
            return this.type;
        }

        public byte getData() {
            return this.data;
        }

        public ItemStack toItemStack() {
            return this.toItemStack(1);
        }

        public ItemStack toItemStack(int amount) {
            return ItemUtil.create(this.type, amount, this.data);
        }

        public String toCode() {
            return this.type.getId() + ":" + this.getData();
        }

        public String toString() {
            return this.toCode();
        }
    }
}
