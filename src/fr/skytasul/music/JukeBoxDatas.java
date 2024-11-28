package fr.skytasul.music;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;



public class JukeBoxDatas {

	
	private static Map<UUID, PlayerData> players = new HashMap<>();
	


	
	public PlayerData getDatas(UUID uuid) {
		return players.get(uuid);
	}
	
	public PlayerData getDatas(Player p) {
		return players.get(p.getUniqueId());
	}
	
	public Collection<PlayerData> getDatas() {
		return players.values();
	}
	
	//public Object getSerializedList() {
	//	List<Map<String, Object>> list = new ArrayList<>();
		//for (PlayerData pdata : players.values()) {
		//	if (pdata.getPlayer() != null && !JukeBox.canSaveDatas(pdata.getPlayer())) continue;
		//	if (pdata.songPlayer != null) pdata.stopPlaying(true);
		//	if (!pdata.isDefault(JukeBox.defaultPlayer)) list.add(pdata.serialize());
		//}
	//	return list;
	//}
	
	public void joins(Player p) {
		UUID id = p.getUniqueId();
			PlayerData pdata = players.get(id);
			if (pdata == null) {
				pdata = PlayerData.create(id);
				players.put(id, pdata);
			}
			pdata.playerJoin(p, !JukeBox.worlds || JukeBox.worldsEnabled.contains(p.getWorld().getName()));

	}
	
	public void quits(Player p) {
		UUID id = p.getUniqueId();
		PlayerData pdata = players.get(id);
		if (pdata != null) {
			pdata.playerLeave();
				 players.remove(id);

		}
	}
	
}
