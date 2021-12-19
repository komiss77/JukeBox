package com.statiocraft.jukebox.fromapi.menu;

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;

public class Menu {

    private static ArrayList list = new ArrayList();
    protected Inventory inv;

    /** @deprecated */
    @Deprecated
    public static Menu getByInventory(Inventory inventory) {
        Iterator iterator = Menu.list.iterator();

        while (iterator.hasNext()) {
            Menu menu = (Menu) iterator.next();

            if (menu.getInventory().equals(inventory)) {
                return menu;
            }
        }

        return null;
    }

    public Menu(Inventory menu) {
        this.inv = menu;
        Menu.list.add(this);
    }

    public Inventory getInventory() {
        return this.inv;
    }
}
