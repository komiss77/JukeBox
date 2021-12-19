package com.statiocraft.jukebox.commands;

import com.statiocraft.jukebox.JukeBox;
import com.statiocraft.jukebox.Shuffle;
import com.statiocraft.jukebox.SingleSong;
import com.statiocraft.jukebox.Song;
import com.statiocraft.jukebox.scJukeBox;
import com.statiocraft.jukebox.fromapi.util.NumberUtil;
import com.statiocraft.jukebox.fromapi.util.StringUtil;
import java.util.Iterator;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class cmdMusic implements CommandExecutor {

    public static final String a = "This command can only be performed by players!";
    public static final String b = "§4Error§c: You are not permitted to perform this command!";
    public static final String c = "§4Error§c: That item has been disabled in the server configuration!";
    public static final String d = "§4Error§c: Player not found!";
    public static final String e = "§4Error§c: That command can only be used when you\'re in shuffle-mode!";
    public static final String f = "§4Error§c: No song was found by that name!";

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length <= 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be performed by players!");
           // } else if (!sender.hasPermission("music.list")) {
           //     sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
            } else {
                scJukeBox.getGui().open((Player) sender);
            }

            return true;
        } else if (StringUtil.match(args[0], new String[] { "help", "?"})) {
            return false;
        } else {
            Iterator iterator;
            Player b1;

            if (StringUtil.match(args[0], new String[] { "play", "playSong", "playTrack"})) {
                if (args.length <= 1) {
                    return false;
                } else if (args.length <= 2 && !(sender instanceof Player)) {
                    return false;
               // } else if (!sender.hasPermission("music.play")) {
                //    sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
                //    return true;
                } else {
                    Player i4 = null;

                    if (args.length > 2 && sender.hasPermission("music.play.others")) {
                        Iterator p1 = Bukkit.getOnlinePlayers().iterator();

                        while (p1.hasNext()) {
                            b1 = (Player) p1.next();
                            if (args[2].equalsIgnoreCase(b1.getName())) {
                                i4 = b1;
                                break;
                            }
                        }
                    } else {
                        i4 = (Player) sender;
                    }

                    if (i4 == null) {
                        sender.sendMessage("§4Error§c: Player not found!");
                        return true;
                    } else {
                        Song b2 = null;
                        Song p2;

                        if (scJukeBox.c().b_usnfd_) {
                            iterator = scJukeBox.listSongs().iterator();

                            while (iterator.hasNext()) {
                                p2 = (Song) iterator.next();
                                if (p2.getTitle().replaceAll(" ", "").equalsIgnoreCase(args[1])) {
                                    b2 = p2;
                                    break;
                                }
                            }
                        } else {
                            iterator = scJukeBox.listSongs().iterator();

                            while (iterator.hasNext()) {
                                p2 = (Song) iterator.next();
                                if (p2.getPath().getName().replaceAll(".nbs", "").replaceAll(" ", "").equalsIgnoreCase(args[1])) {
                                    b2 = p2;
                                    break;
                                }
                            }
                        }

                        if (b2 == null) {
                            sender.sendMessage("§4Error§c: No song was found by that name!");
                            return true;
                        } else {
                            (new SingleSong(b2)).addPlayer(i4);
                            if (!i4.equals(sender)) {
                               
                                
                                String s=("§7[§f" + i4.getName() + "§7] §f" + StringUtil.convertCodes(scJukeBox.c().s_npt_).replaceAll("#TRACK", b2.getName()));
                                JukeBox.sendActionBar((Player) sender, s);
                              //  sender.sendMessage(s);
                                
                            }

                            return true;
                        }
                    }
                }
            } else {
                JukeBox i;

                if (StringUtil.match(args[0], new String[] { "item", "items"})) {
                    if (args.length <= 1) {
                        return false;
                    } else if (args.length <= 2 && !(sender instanceof Player)) {
                        return false;
                    } else {
                        i = null;
                        b1 = null;
                        ItemStack i3;

                        if (args[1].equalsIgnoreCase("shuffle")) {
                            if (!scJukeBox.c().b_pmse_) {
                                sender.sendMessage("§4Error§c: That item has been disabled in the server configuration!");
                                return true;
                            }

                            i3 = scJukeBox.c().is_pms_;
                        } else if (args[1].equalsIgnoreCase("random")) {
                            if (!scJukeBox.c().b_pmre_) {
                                sender.sendMessage("§4Error§c: That item has been disabled in the server configuration!");
                                return true;
                            }

                            i3 = scJukeBox.c().is_pmr_;
                        } else if (args[1].equalsIgnoreCase("radio")) {
                            if (!scJukeBox.c().b_pmRe_) {
                                sender.sendMessage("§4Error§c: That item has been disabled in the server configuration!");
                                return true;
                            }

                            i3 = scJukeBox.c().is_pmR_;
                        } else {
                            if (!args[1].equalsIgnoreCase("list")) {
                                return false;
                            }

                            if (!scJukeBox.c().b_pmle_) {
                                sender.sendMessage("§4Error§c: That item has been disabled in the server configuration!");
                                return true;
                            }

                            i3 = scJukeBox.c().is_pml_;
                        }

                        if (args.length > 2) {
                            iterator = Bukkit.getOnlinePlayers().iterator();

                            while (iterator.hasNext()) {
                                Player p = (Player) iterator.next();

                                if (p.getName().equalsIgnoreCase(args[2])) {
                                    b1 = p;
                                    break;
                                }

                                if (b1 == null && p.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                                    b1 = p;
                                }
                            }
                        } else {
                            b1 = (Player) sender;
                        }

                        if (b1 == null) {
                            sender.sendMessage("§4Error§c: Player not found!");
                            return true;
                        } else {
                            b1.getInventory().addItem(new ItemStack[] { i3});
                            sender.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_ig_).replaceAll("#ITEM", args[1].toUpperCase()).replaceAll("#PLAYER", b1.getName()));
                            return true;
                        }
                    }
                } else if (StringUtil.match(args[0], new String[] { "off", "o", "disable", "false"})) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("This command can only be performed by players!");
                        return true;
                    } else {
                        if ((i = scJukeBox.getCurrentJukebox((Player) sender)) != null) {
                            i.removePlayer((Player) sender);
                        }

                       // sender.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_muD_));
                        JukeBox.sendActionBar((Player) sender, StringUtil.convertCodes(scJukeBox.c().s_muD_));
                        return true;
                    }
                } else if (StringUtil.match(args[0], new String[] { "radio", "ra"})) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("This command can only be performed by players!");
                        return true;
                    } else if (args.length > 1 && StringUtil.match(args[1], new String[] { "skip", "sk", "s"})) {
                     //   if (!sender.hasPermission("music.radio.skip")) {
                     //       sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
                     //       return true;
                    //    } else {
                          //  sender.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_Rs_));
             JukeBox.sendActionBar((Player) sender, StringUtil.convertCodes(scJukeBox.c().s_Rs_));
                            scJukeBox.getRadio().next();
                            return true;
                    //    }
                   // } else if (!sender.hasPermission("music.radio")) {
                   //     sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
                   //     return true;
                    } else {
                        Boolean i2 = Boolean.valueOf(!scJukeBox.getRadio().listPlayers().contains((Player) sender));

                        if (args.length > 1 && StringUtil.isConfirming(args[1])) {
                            i2 = Boolean.valueOf(true);
                        } else if (args.length > 1 && StringUtil.isRejecting(args[1])) {
                            i2 = Boolean.valueOf(false);
                        }

                        if (i2.booleanValue()) {
                          //  sender.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_Re_));
             JukeBox.sendActionBar((Player) sender, StringUtil.convertCodes(scJukeBox.c().s_Re_));
                            
                            scJukeBox.getRadio().addPlayer((Player) sender);
                            return true;
                        } else {
                            //sender.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_Rd_));
             JukeBox.sendActionBar((Player) sender, StringUtil.convertCodes(scJukeBox.c().s_Rd_));
                            scJukeBox.getRadio().removePlayer((Player) sender);
                            return true;
                        }
                    }
                } else if (StringUtil.match(args[0], new String[] { "random", "rand", "r"})) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("This command can only be performed by players!");
                        return true;
                  //  } else if (!sender.hasPermission("music.random")) {
                  //      sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
                 //       return true;
                    } else {
                        (new SingleSong((Song) scJukeBox.listSongs().get((new Random()).nextInt(scJukeBox.listSongs().size())))).addPlayer((Player) sender);
                        return true;
                    }
                } else if (StringUtil.match(args[0], new String[] { "reload", "rl"})) {
                    if (!sender.hasPermission("music.reload")) {
                        sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
                        return true;
                    } else {
                        scJukeBox.getInstance().reloadConfig();
                        sender.sendMessage("§6Configuration reloaded!");
                        return true;
                    }
                } else if (!StringUtil.match(args[0], new String[] { "shuffle", "sh", "s"})) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("This command can only be performed by players!");
                        return true;
                    } else {
                        Integer i1 = NumberUtil.toInt(args[0]);

                       // if (i1 != null && sender.hasPermission("music.list")) {
                        if (i1 != null ) {
                            scJukeBox.getGui().open((Player) sender, i1.intValue() - 1);
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else if (!(sender instanceof Player)) {
                    sender.sendMessage("This command can only be performed by players!");
                    return true;
               // } else if (!sender.hasPermission("music.shuffle")) {
               //     sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
              //      return true;
                } else {
                    i = scJukeBox.getCurrentJukebox((Player) sender);
                    Boolean b = Boolean.valueOf(i == null || !(i instanceof Shuffle) || i.equals(scJukeBox.getRadio()));

                    if (args.length > 1 && StringUtil.match(args[1], new String[] { "skip", "sk", "s"})) {
                      //  if (!sender.hasPermission("music.shuffle.skip")) {
                       //     sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
                      //      return true;
                      //  } else 
                        if (b.booleanValue()) {
                            sender.sendMessage("§4Error§c: That command can only be used when you\'re in shuffle-mode!");
                            return true;
                        } else {
                            sender.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_ss_));
             JukeBox.sendActionBar((Player) sender, StringUtil.convertCodes(scJukeBox.c().s_ss_));
                            i.next();
                            return true;
                        }
                  //  } else if (!sender.hasPermission("music.shuffle")) {
                  //      sender.sendMessage("§4Error§c: You are not permitted to perform this command!");
                 //       return true;
                    } else {
                        if (args.length > 1 && StringUtil.isConfirming(args[1])) {
                            b = Boolean.valueOf(true);
                        } else if (args.length > 1 && StringUtil.isRejecting(args[1])) {
                            b = Boolean.valueOf(false);
                        }

                        if (b.booleanValue()) {
                            //sender.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_se_));
             JukeBox.sendActionBar((Player) sender, StringUtil.convertCodes(scJukeBox.c().s_se_));
                            (new Shuffle()).addPlayer((Player) sender);
                            return true;
                        } else {
                          //  sender.sendMessage(StringUtil.convertCodes(scJukeBox.c().s_sd_));
             JukeBox.sendActionBar((Player) sender, StringUtil.convertCodes(scJukeBox.c().s_sd_));
                            i.removePlayer((Player) sender);
                            return true;
                        }
                    }
                }
            }
        }
    }
}
