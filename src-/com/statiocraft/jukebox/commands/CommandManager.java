package com.statiocraft.jukebox.commands;

import com.statiocraft.jukebox.scJukeBox;

public class CommandManager {

    public CommandManager(scJukeBox plugin) {
        plugin.getCommand("music").setExecutor(new cmdMusic());
        plugin.getCommand("radio").setExecutor(new cmdRadio());
    }
}
