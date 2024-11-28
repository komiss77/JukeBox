package old;

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
        
        if (!p.hasPermission("ostrov.music")) {
            sender.sendMessage("§6Музыка доступна тем, кто хотя бы §3Герой..");
            return false;
        }
        
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
            } else if (args[0].equalsIgnoreCase("reload")){
                if (!p.isOp()) {
                    p.sendMessage("§cop!");
                    return true;
                }
                try {
                    JukeBox.getInstance().disableAll();
                    JukeBox.getInstance().initAll();
                }
                catch (Exception ex) {
                    sender.sendMessage("�cError while reloading. Please check the console and send the stacktrace to SkytAsul on SpigotMC.");
                    ex.printStackTrace();
                }
                sender.sendMessage(Lang.RELOAD_FINISH);
                return false;
            }
            
        }
        
        return false;
    }
    
    public static void open(final Player p) {
        
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
