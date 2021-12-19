// 
// Decompiled by Procyon v0.5.36
// 

package fr.skytasul.music;

import fr.skytasul.music.utils.Lang;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandMusic implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.PLAYER);
            return false;
        }
        final Player p = (Player)sender;
        
        
        if(args.length==0) {
            
            open(p);
            
        } else if (args.length==1) {
            
            final PlayerData pdata = PlayerData.players.get(p.getUniqueId());
            
            if (args[0].equalsIgnoreCase("play")) {
                pdata.playRandom();
            } else if (args[0].equalsIgnoreCase("stop")) {
                pdata.stopPlaying(false);
            } else if (args[0].equalsIgnoreCase("switch")) {
                if (pdata.songPlayer!=null) {
                    pdata.stopPlaying(false);
                } else {
                    pdata.playRandom();
                }
            }
            
        }
        
        return false;
    }
    
    public static void open(final Player p) {
        if (JukeBox.worlds && !JukeBox.worldsEnabled.contains(p.getWorld())) {
            return;
        }
        final PlayerData pdata = PlayerData.players.get(p.getUniqueId());
        if (pdata.linked != null) {
            final JukeBoxInventory inv = pdata.linked;
            inv.setSongsPage();
            inv.openInventory(p);
        }
        else {
            new JukeBoxInventory(p);
        }
    }
}
