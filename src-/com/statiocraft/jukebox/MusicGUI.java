package com.statiocraft.jukebox;

import com.statiocraft.jukebox.fromapi.menu.GUI;
import com.statiocraft.jukebox.fromapi.util.StringUtil;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MusicGUI extends GUI {

    public MusicGUI() {
        super(StringUtil.convertCodes(scJukeBox.c().s_meT_), a(scJukeBox.c().i_dms_), b(), scJukeBox.c().is_mpf_, scJukeBox.c().is_mpb_, scJukeBox.c().is_mpc_, new ItemStack(Material.AIR), scJukeBox.c().is_mmR_, scJukeBox.c().is_mms_, scJukeBox.c().is_mmr_, scJukeBox.c().is_mmd_);
    }

    private static int a(int n) {
        return n % 9 == 0 && n > 0 ? n : 9 * (n - n % 9 + 1);
    }

    private static ItemStack[] b() {
        List l = scJukeBox.listSongs();
        ItemStack[] i = new ItemStack[l.size()];

        for (int n = 0; n < i.length; ++n) {
            i[n] = ((Song) l.get(n)).getMenuItem();
        }

        return i;
    }
}
