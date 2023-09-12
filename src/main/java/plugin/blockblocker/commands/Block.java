package plugin.blockblocker.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.blockblocker.Main;
import plugin.blockblocker.util.Listeners;

import java.util.ArrayList;
import java.util.List;

public class Block implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p)) return true;

        Main instance = Main.instance;
        String prefix = instance.prefix;

        if (strings.length == 0) {p.sendMessage(prefix + "§cVerwendung§7: /block [mark, clear, triggstart, triggend]"); return true;}

        //block mark [index]
        if (strings[0].equalsIgnoreCase("mark")) {
            if (!p.hasPermission("block.mark")) {p.sendMessage(prefix + "§7Du hast keine Rechte und dies zu tun!"); return true;}

            if (strings.length == 2) {
                int index;

                try {
                    index = Integer.parseInt(strings[1]);
                }catch (NumberFormatException e) {
                    p.sendMessage(prefix + "§cDer index ist eine Zahl von 1 - 100§7!");
                    return true;
                }

                if (index > 100 || index < 0) {
                    p.sendMessage(prefix + "§cDer index ist eine Zahl von 0 - 100§7!");
                    return true;
                }

                String lookedBlock = p.getTargetBlock(5).getType().name();

                if (lookedBlock.equalsIgnoreCase("air")) {
                    p.sendMessage(prefix + "§cDu kannst nicht die Luft makieren, versuche es erneut§7!");
                    return true;
                }

                instance.getConfig().set("mark." + index, lookedBlock);
                instance.saveConfig();
                p.sendMessage(prefix + "§7Es wurde erfolgreich ein Block Markiert!");
                return true;

            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /mark [index]");
                return true;
            }
        }

        //block clear [index]
        if (strings[0].equalsIgnoreCase("clear")) {
            if (!p.hasPermission("block.clear")) {p.sendMessage(prefix + "§7Du hast keine Rechte und dies zu tun!"); return true;}

            if (strings.length == 2) {
                int index;

                try {
                    index = Integer.parseInt(strings[1]);
                }catch (NumberFormatException e) {
                    p.sendMessage(prefix + "§cDer index ist eine Zahl von 1 - 100§7!");
                    return true;
                }

                if (!instance.getConfig().contains("mark." + index)) {
                    p.sendMessage(prefix + "§cDieser index ist nicht Markiert§7!");
                    return true;
                }

                instance.getConfig().set("mark." + index, null);
                instance.saveConfig();
                p.sendMessage(prefix + "§7Es wurde erfolgreich ein Block Entmarkiert!");
                return true;

            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /clear [index]");
                return true;
            }
        }

        //block triggstart [index] [mode]
        if (strings[0].equalsIgnoreCase("triggstart")) {
            if (!p.hasPermission("block.triggstart")) {p.sendMessage(prefix + "§7Du hast keine Rechte und dies zu tun!"); return true;}

            if (strings.length == 3) {
                int mode;

                try {
                    mode = Integer.parseInt(strings[2]);
                }catch (NumberFormatException e) {
                    p.sendMessage(prefix + "§cEs gibt nur mode 1 und 2§7!");
                    return true;
                }

                //mode 1
                if (mode == 1) {
                    int index;

                    try {
                        index = Integer.parseInt(strings[1]);
                    }catch (NumberFormatException e) {
                        p.sendMessage(prefix + "§cDer index ist eine Zahl von 1 - 100§7!");
                        return true;
                    }

                    if (!instance.getConfig().contains("mark." + index)) {
                        p.sendMessage(prefix + "§cDieser index ist nicht Markiert§7!");
                        return true;
                    }

                    if (instance.getConfig().contains("trigg." + p.getUniqueId().toString() + index)) {
                        p.sendMessage(prefix + "§cDieser index ist bereits getriggert§7!");
                        return true;
                    }

                    instance.getConfig().set("trigg." + p.getUniqueId().toString() + index, instance.getConfig().getString("mark." + index));
                    instance.saveConfig();

                    p.sendMessage(prefix + "§7Es wurde erfolgreich ein Block getriggert!");
                    return true;
                }

                //mode 2
                if (mode == 2) {
                    int index;

                    try {
                        index = Integer.parseInt(strings[1]);
                    }catch (NumberFormatException e) {
                        p.sendMessage(prefix + "§cDer index ist eine Zahl von 1 - 100§7!");
                        return true;
                    }

                    if (!instance.getConfig().contains("mark." + index)) {
                        p.sendMessage(prefix + "§cDieser index ist nicht Markiert§7!");
                        return true;
                    }

                    if (instance.getConfig().contains("trigg2." + p.getUniqueId().toString() + index)) {
                        p.sendMessage(prefix + "§cDieser index ist bereits getriggert§7!");
                        return true;
                    }

                    instance.getConfig().set("trigg2." + p.getUniqueId().toString() + index, instance.getConfig().getString("mark." + index));
                    instance.saveConfig();
                    Listeners.triggMode2 = true;

                    p.sendMessage(prefix + "§7Es wurde erfolgreich ein Block getriggert!");
                    return true;
                }

                p.sendMessage(prefix + "§cVerwendung§7: /triggstart [index] [mode]");
                return true;
            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /triggstart [index] [mode]");
                return true;
            }
        }

        //block triggend [index] [mode]
        if (strings[0].equalsIgnoreCase("triggend")) {
            if (!p.hasPermission("block.mark")) {p.sendMessage(prefix + "§7Du hast keine Rechte und dies zu tun!"); return true;}

            if (strings.length == 3) {
                int mode;

                try {
                    mode = Integer.parseInt(strings[2]);
                }catch (NumberFormatException e) {
                    p.sendMessage(prefix + "§cEs gibt nur mode 1 und 2§7!");
                    return true;
                }

                //mode 1
                if (mode == 1) {
                    int index;

                    try {
                        index = Integer.parseInt(strings[1]);
                    }catch (NumberFormatException e) {
                        p.sendMessage(prefix + "§cDer index ist eine Zahl von 1 - 100§7!");
                        return true;
                    }

                    if (!instance.getConfig().contains("trigg." + p.getUniqueId().toString() + index)) {
                        p.sendMessage(prefix + "§cDieser index ist nicht getriggert§7!");
                        return true;
                    }

                    instance.getConfig().set("trigg." + p.getUniqueId().toString() + index, null);
                    instance.saveConfig();
                    p.sendMessage(prefix + "§7Es wurde erfolgreich ein Block entriggert!");
                    return true;
                }

                //mode 2
                if (mode == 2) {
                    int index;

                    try {
                        index = Integer.parseInt(strings[1]);
                    }catch (NumberFormatException e) {
                        p.sendMessage(prefix + "§cDer index ist eine Zahl von 1 - 100§7!");
                        return true;
                    }

                    if (!instance.getConfig().contains("trigg2." + p.getUniqueId().toString() + index)) {
                        p.sendMessage(prefix + "§cDieser index ist nicht getriggert§7!");
                        return true;
                    }

                    Listeners.triggMode2 = false;

                    //replace broken blocks
                    for (int i = 0; i != 5001; i++) {
                        if (instance.getConfig().contains("break." + p.getUniqueId().toString() + "." + "block." + i)) {
                            Material mat = Material.getMaterial(instance.getConfig().getString("break." + p.getUniqueId().toString() + "." + "block." + i));
                            Location loc = instance.getConfig().getLocation("break." + p.getUniqueId().toString() + "." + "loc." + i);

                            if (mat.name().equalsIgnoreCase(instance.getConfig().getString("trigg2." + p.getUniqueId().toString() + index))) {
                                loc.getBlock().setType(mat);
                            }
                        }
                    }

                    //break placed blocks
                    for (int i = 0; i != 5001; i++) {
                        if (instance.getConfig().contains("place." + p.getUniqueId().toString() + "." + "block." + i)) {
                            Material mat = Material.getMaterial(instance.getConfig().getString("place." + p.getUniqueId().toString() + "." + "block." + i));
                            Location loc = instance.getConfig().getLocation("place." + p.getUniqueId().toString() + "." + "loc." + i);

                            if (mat.name().equalsIgnoreCase(instance.getConfig().getString("trigg2." + p.getUniqueId().toString() + index))) {
                                loc.getBlock().setType(Material.AIR);
                            }
                        }
                    }

                    instance.getConfig().set("trigg2." + p.getUniqueId().toString() + index, null);
                    instance.saveConfig();

                    p.sendMessage(prefix + "§7Es wurde erfolgreich ein Block entriggert!");
                    return true;
                }

                p.sendMessage(prefix + "§cVerwendung§7: /triggend [index] [mode]");
                return true;
                
            }else {
                p.sendMessage(prefix + "§cVerwendung§7: /triggend [index] [mode]");
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ArrayList<String> list = new ArrayList<>();

        if (strings.length == 0) return list;

        if (strings.length == 1) {
            list.add("mark".toLowerCase());
            list.add("clear".toLowerCase());
            list.add("triggstart".toLowerCase());
            list.add("triggend".toLowerCase());
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = strings[strings.length-1].toLowerCase();

        for (String i : list) {
            String i1 = i.toLowerCase();

            if (i1.startsWith(currentarg)) {
                completerList.add(i);
            }
        }

        return completerList;
    }
}
