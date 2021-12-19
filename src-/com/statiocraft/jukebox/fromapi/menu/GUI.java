package com.statiocraft.jukebox.fromapi.menu;

import com.statiocraft.jukebox.fromapi.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GUI {

    public static final List l = new ArrayList();
    public static final String currentPage = "#CURRENT";
    public static final String totalPage = "#TOTAL";
    private Menu[] ms;
    private String t;
    private int s;
    private ItemStack[] c;
    private ItemStack pF;
    private ItemStack pB;
    private ItemStack cl;
    private ItemStack tL;
    private ItemStack c1;
    private ItemStack c2;
    private ItemStack c3;
    private ItemStack c4;
    private Map m;

    /** @deprecated */
    @Deprecated
    public static GUI getByInventory(Inventory inventory) {
        Iterator iterator = GUI.l.iterator();

        while (iterator.hasNext()) {
            GUI g = (GUI) iterator.next();
            Menu[] amenu = g.ms;
            int i = g.ms.length;

            for (int j = 0; j < i; ++j) {
                Menu m = amenu[j];

                if (m.getInventory().equals(inventory)) {
                    return g;
                }
            }
        }

        return null;
    }

    public GUI(String title, int size, ItemStack[] content, ItemStack pageForward, ItemStack pageBackward, ItemStack close, ItemStack topLine, ItemStack custom1, ItemStack custom2, ItemStack custom3, ItemStack custom4) {
        this.t = title;
        this.s = size;
        this.c = content;
        this.pF = pageForward;
        this.pB = pageBackward;
        this.cl = close;
        this.tL = topLine;
        this.c1 = custom1;
        this.c2 = custom2;
        this.c3 = custom3;
        this.c4 = custom4;
        this.m = new HashMap();
        this.l();
        GUI.l.add(this);
    }

    public GUI(String title, int size, ItemStack[] content, ItemStack pageForward, ItemStack pageBackward, ItemStack close, ItemStack topLine) {
        this(title, size, content, pageForward, pageBackward, close, topLine, (ItemStack) null, (ItemStack) null, (ItemStack) null, (ItemStack) null);
    }

    public GUI(String title, int size, ItemStack[] content, ItemStack pageForward, ItemStack pageBackward, ItemStack close) {
        this(title, size, content, pageForward, pageBackward, close, (ItemStack) null);
    }

    private void l() {
        this.l(this.t, this.s, this.c, this.pF, this.pB, this.cl, this.tL, this.c1, this.c2, this.c3, this.c4);
    }

    private void l(String title, int size, ItemStack[] content, ItemStack pageForward, ItemStack pageBackward, ItemStack close, ItemStack topLine, ItemStack custom1, ItemStack custom2, ItemStack custom3, ItemStack custom4) {
        int ipi = size;
        int is = content.length;
        int r = is % size;
        int pages = (is - r) / size + 1;

        this.ms = new Menu[pages];

        for (int cp = 1; cp <= pages; ++cp) {
            String t = title.replaceAll("#CURRENT", String.valueOf(cp));

            t = t.replaceAll("#TOTAL", String.valueOf(pages));
            t = StringUtil.limit(t, 0, 32);
            Inventory inv = Bukkit.createInventory((InventoryHolder) null, size + 9, t);
            int sa;

            if (topLine != null) {
                for (sa = 0; sa < 9; ++sa) {
                    inv.setItem(sa, topLine);
                }
            }

            if (this.c1 != null) {
                inv.setItem(3, this.c1);
            }

            if (this.c2 != null) {
                inv.setItem(4, this.c2);
            }

            if (this.c3 != null) {
                inv.setItem(5, this.c3);
            }

            if (this.c4 != null) {
                inv.setItem(6, this.c4);
            }

            if (cp != 1) {
                inv.setItem(0, pageBackward);
            }

            if (cp != pages) {
                inv.setItem(1, pageForward);
            }

            inv.setItem(8, close);
            sa = (cp - 1) * ipi;
            int ea = cp * ipi;
            int cs = 9;

            for (int x = sa; x < ea && x < content.length; ++x) {
                inv.setItem(cs, content[x]);
                ++cs;
            }

            this.ms[cp - 1] = new Menu(inv);
        }

    }

    public void open(Player player, int page) {
        if (this.ms.length != 0) {
            if (this.ms.length <= page) {
                this.open(player, 0);
            }

            player.openInventory(this.ms[page].getInventory());
            this.m.put(player, Integer.valueOf(page));
        }
    }

    public void open(Player player) {
        this.open(player, 0);
    }

    public void openNext(Player player) {
        if (!this.m.containsKey(player)) {
            this.open(player, 0);
        } else {
            this.open(player, ((Integer) this.m.get(player)).intValue() + 1);
        }

    }

    public void openPrevious(Player player) {
        if (!this.m.containsKey(player)) {
            this.open(player, 0);
        } else {
            this.open(player, ((Integer) this.m.get(player)).intValue() - 1);
        }

    }

    public String getTitle() {
        return this.t;
    }

    public int getSize() {
        return this.s;
    }

    public ItemStack[] getContent() {
        return this.c;
    }

    public ItemStack getPageForward() {
        return this.pF;
    }

    public ItemStack getPageBackward() {
        return this.pB;
    }

    public ItemStack getClose() {
        return this.cl;
    }

    public ItemStack getTopLine() {
        return this.tL;
    }

    public ItemStack getCustom1() {
        return this.c1;
    }

    public ItemStack getCustom2() {
        return this.c2;
    }

    public ItemStack getCustom3() {
        return this.c3;
    }

    public ItemStack getCustom4() {
        return this.c4;
    }

    public void setTitle(String title) {
        this.t = title;
        this.l();
    }

    public void setSize(int size) {
        this.s = size;
        this.l();
    }

    public void setContent(ItemStack[] content) {
        this.c = content;
        this.l();
    }

    public void setPageForward(ItemStack pageForward) {
        this.pF = pageForward;
        this.l();
    }

    public void setPageBackward(ItemStack pageBackward) {
        this.pB = pageBackward;
        this.l();
    }

    public void setClose(ItemStack close) {
        this.cl = close;
        this.l();
    }

    public void setTopline(ItemStack topLine) {
        this.tL = topLine;
        this.l();
    }

    public void setCustom1(ItemStack custom1) {
        this.c1 = custom1;
    }

    public void setCustom2(ItemStack custom2) {
        this.c2 = custom2;
    }

    public void setCustom3(ItemStack custom3) {
        this.c3 = custom3;
    }

    public void setCustom4(ItemStack custom4) {
        this.c4 = custom4;
    }
}
