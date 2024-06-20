package fr.skytasul.music;

import fr.skytasul.music.utils.Playlists;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import org.bukkit.OfflinePlayer;
import java.io.InputStream;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.net.URL;
import java.io.File;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import fr.skytasul.music.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandAdmin implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Lang.INCORRECT_SYNTAX);
            return false;
        }
        final String s2;
        switch (s2 = args[0]) {
            case "particles": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(String.valueOf(p.getName()) + " : " + this.particles(p));
                    }
                    return false;
                }
                final Player cp = Bukkit.getPlayer(args[1]);
                if (cp == null) {
                    sender.sendMessage("§cUnknown player.");
                    return false;
                }
                sender.sendMessage(this.particles(cp));
                return false;
            }
            case "player": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                final OfflinePlayer pp = Bukkit.getOfflinePlayer(args[1]);
                if (pp == null) {
                    sender.sendMessage("§cUnknown player.");
                    return false;
                }
                final PlayerData pdata = PlayerData.players.get(pp.getUniqueId());
                String s = String.valueOf(Lang.MUSIC_PLAYING) + " ";
                if (pdata == null) {
                    s = String.valueOf(s) + "§cx";
                }
                else if (pdata.songPlayer == null) {
                    s = String.valueOf(s) + "§cx";
                }
                else {
                    final Song song = pdata.songPlayer.getSong();
                    s = JukeBox.getSongName(song);
                }
                sender.sendMessage(s);
                return false;
            }
            case "random": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(String.valueOf(p.getName()) + " : " + this.random(p));
                    }
                    return false;
                }
                final Player cp = Bukkit.getPlayer(args[1]);
                if (cp == null) {
                    sender.sendMessage("�cUnknown player.");
                    return false;
                }
                sender.sendMessage(this.random(cp));
                return false;
            }
            case "reload": {
                sender.sendMessage(Lang.RELOAD_LAUNCH);
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
            case "toggle": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    for (final Player p2 : Bukkit.getOnlinePlayers()) {
                        this.toggle(p2);
                    }
                    return false;
                }
                final Player cp2 = Bukkit.getPlayer(args[1]);
                if (cp2 == null) {
                    sender.sendMessage("�cUnknown player.");
                    return false;
                }
                this.toggle(cp2);
                return false;
            }
            case "volume": {
                if (args.length < 3) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                final Player cp = Bukkit.getPlayer(args[1]);
                if (cp == null) {
                    sender.sendMessage("§cUnknown player.");
                    return false;
                }
                final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
                try {
                    pdata.setVolume(Byte.parseByte(args[2]));
                    sender.sendMessage("§aVolume : " + pdata.getVolume());
                }
                catch (NumberFormatException ex2) {
                    sender.sendMessage(Lang.INVALID_NUMBER);
                }
                return false;
            }
            case "join": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(String.valueOf(p.getName()) + " : " + this.join(p));
                    }
                    return false;
                }
                final Player cp = Bukkit.getPlayer(args[1]);
                if (cp == null) {
                    sender.sendMessage("§cUnknown player.");
                    return false;
                }
                sender.sendMessage(this.join(cp));
                return false;
            }
            case "loop": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    for (final Player p3 : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(String.valueOf(p3.getName()) + " : " + this.loop(p3));
                    }
                    return false;
                }
                final Player cp = Bukkit.getPlayer(args[1]);
                if (cp == null) {
                    sender.sendMessage("§cUnknown player.");
                    return false;
                }
                sender.sendMessage(this.loop(cp));
                return false;
            }
            case "next": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    int i = 0;
                    for (final Player p4 : Bukkit.getOnlinePlayers()) {
                        PlayerData.players.get(p4.getUniqueId()).nextSong();
                        ++i;
                    }
                    sender.sendMessage("§aNext song for " + i + "players.");
                    return false;
                }
                final Player cp = Bukkit.getPlayer(args[1]);
                if (cp == null) {
                    sender.sendMessage("§cUnknown player.");
                    return false;
                }
                PlayerData.players.get(cp.getUniqueId()).nextSong();
                sender.sendMessage("§aNext song for " + cp.getName());
                return false;
            }
            case "play": {
                if (args.length < 3) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    for (final Player p2 : Bukkit.getOnlinePlayers()) {
                        args[1] = p2.getName();
                        final String msg = this.play(args);
                        if (!msg.isEmpty()) {
                            sender.sendMessage(String.valueOf(p2.getName()) + " : " + msg);
                        }
                    }
                    return false;
                }
                final String msg2 = this.play(args);
                if (!msg2.isEmpty()) {
                    sender.sendMessage(msg2);
                    return false;
                }
                return false;
            }
            case "stop": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    for (final Player p2 : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(this.stop(p2));
                    }
                    return false;
                }
                final Player cp2 = Bukkit.getPlayer(args[1]);
                if (cp2 == null) {
                    sender.sendMessage("§cUnknown player.");
                    return false;
                }
                sender.sendMessage(this.stop(cp2));
                return false;
            }
            case "download": {
                if (args.length < 3) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                try {
                    final File file = new File(JukeBox.songsFolder, String.valueOf(args[2]) + ".nbs");
                    Files.copy(new URL(args[1]).openStream(), file.toPath(), new CopyOption[0]);
                    boolean valid = true;
                    final FileInputStream stream = new FileInputStream(file);
                    Label_1236: {
                        try {
                            final Song song2 = NBSDecoder.parse((InputStream)stream);
                            if (song2 == null) {
                                valid = false;
                            }
                        }
                        catch (Throwable e2) {
                            valid = false;
                            break Label_1236;
                        }
                        finally {
                            stream.close();
                            if (!valid) {
                                sender.sendMessage("§cDownloaded file is not a nbs song file.");
                            }
                        }
                        stream.close();
                        if (!valid) {
                            sender.sendMessage("§cDownloaded file is not a nbs song file.");
                        }
                    }
                    if (valid) {
                        sender.sendMessage("§aSong downloaded. To add it to the list, you must reload the plugin. (�o/amusic reload�r�a)");
                    }
                    else {
                        file.delete();
                    }
                }
                catch (Throwable e) {
                    sender.sendMessage("§cError when downloading file.");
                    e.printStackTrace();
                }
                return false;
            }
            /*case "setitem": {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cYou have to be a player to do that.");
                    return false;
                }
                final ItemStack is = ((Player)sender).getInventory().getItemInHand();
                if (is == null || is.getType() == Material.AIR) {
                    JukeBox.getInstance().jukeboxItem = null;
                }
                else {
                    JukeBox.getInstance().jukeboxItem = is;
                }
                sender.sendMessage("§aItem edited. Now : �2" + ((JukeBox.getInstance().jukeboxItem == null) ? "null" : JukeBox.getInstance().jukeboxItem.toString()));
                return false;
            }*/
            case "shuffle": {
                if (args.length < 2) {
                    sender.sendMessage(Lang.INCORRECT_SYNTAX);
                    return false;
                }
                if (args[1].equals("@a")) {
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(String.valueOf(p.getName()) + " : " + this.shuffle(p));
                    }
                    return false;
                }
                final Player cp = Bukkit.getPlayer(args[1]);
                if (cp == null) {
                    sender.sendMessage("§cUnknown player.");
                    return false;
                }
                sender.sendMessage(this.shuffle(cp));
                return false;
            }
            default:
                break;
        }
        sender.sendMessage(String.valueOf(Lang.AVAILABLE_COMMANDS) + " <reload|player|play|stop|toggle|setitem|download|shuffle|particles|join|random|volume|loop|next> ...");
        return false;
    }
    
    private String play(final String[] args) {
        final Player cp = Bukkit.getPlayer(args[1]);
        if (cp == null) {
            return "§cUnknown player.";
        }

        Song song;
        try {
            final int id = Integer.parseInt(args[2]);
            try {
                song = JukeBox.getSongs().get(id);
            }
            catch (IndexOutOfBoundsException ex) {
                return "§cError on §l" + id + " §r§c(inexistant)";
            }
        }
        catch (NumberFormatException ex2) {
            String fileName = args[2];
            for (int i = 3; i < args.length; ++i) {
                fileName = String.valueOf(fileName) + args[i] + ((i == args.length - 1) ? "" : " ");
            }
            song = JukeBox.getSongByFile(fileName);
            if (song == null) {
                return Lang.INVALID_NUMBER;
            }
        }
        final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
        pdata.setPlaylist(Playlists.PLAYLIST, false);
        pdata.playSong(song);
        pdata.songPlayer.adminPlayed = true;
        return "";
    }
    
    private String stop(final Player cp) {
        final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
        pdata.stopPlaying(true);
        return String.valueOf(Lang.PLAYER_MUSIC_STOPPED) + cp.getName();
    }
    
    private void toggle(final Player cp) {
        final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
        if (pdata.songPlayer == null) {
            return;
        }
        pdata.songPlayer.setPlaying(!pdata.songPlayer.isPlaying());
    }
    
    private String shuffle(final Player cp) {
        final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
        return "§aShuffle: " + pdata.setShuffle(!pdata.isShuffle());
    }
    
    private String join(final Player cp) {
        final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
        return "§aJoin: " + pdata.setJoinMusic(!pdata.hasJoinMusic());
    }
    
    private String particles(final Player cp) {
        final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
        return "§aParticles: " + pdata.setParticles(!pdata.hasParticles());
    }
    
    private String loop(final Player cp) {
        final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
        return "§aLoop: " + pdata.setRepeat(!pdata.isRepeatEnabled());
    }
    
    private String random(final Player cp) {
        final PlayerData pdata = PlayerData.players.get(cp.getUniqueId());
        final Song song = pdata.playRandom();
        if (song == null) {
            return "§aShuffle: �cnothing to play";
        }
        return "§aShuffle: " + song.getTitle();
    }
}
