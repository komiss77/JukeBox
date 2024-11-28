package fr.skytasul.music;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skytasul.music.utils.Lang;

public class CommandMusic implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(Lang.PLAYER);
			return false;
		}
		
		Player p = (Player) sender;
                if (!p.hasPermission("ostrov.music")) {
                    sender.sendMessage("§6Музыка доступна тем, кто хотя бы §3Герой..");
                    return false;
                }

		open(p);
		
		return false;
	}
	
	public static void open(Player p){
		if (JukeBox.worlds && !JukeBox.worldsEnabled.contains(p.getWorld().getName())) return;
		PlayerData pdata = JukeBox.getInstance().datas.getDatas(p);
		if (pdata == null) {
			p.sendMessage("§cLoading player... Try again!");
			return;
		}
		if (pdata.linked != null){
			JukeBoxInventory inv = pdata.linked;
			inv.setSongsPage(p);
			inv.openInventory(p);
		}else new JukeBoxInventory(p, pdata);
	}

}
