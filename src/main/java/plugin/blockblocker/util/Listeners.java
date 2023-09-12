package plugin.blockblocker.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import plugin.blockblocker.Main;

public class Listeners implements Listener {

    Main instance = Main.instance;
    public static boolean triggMode2 = false;

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (p.hasPermission("block.imun")) return;

        //Trigg mode 2 logic
        if (triggMode2) {
            //save block data
            for (int iAsIndex = 0; iAsIndex != 101; iAsIndex++) {
                if (instance.getConfig().contains("trigg2." + p.getUniqueId().toString() + iAsIndex)) {
                    String brokenBlockName = e.getBlock().getType().name();
                    String triggerBlockName = instance.getConfig().getString("trigg2." + p.getUniqueId().toString() + iAsIndex);

                    if (brokenBlockName.equalsIgnoreCase(triggerBlockName)) {
                        int nextNumber = 1;

                        while (instance.getConfig().contains("break." + p.getUniqueId().toString() + "." + "block." + nextNumber)) {
                            nextNumber++;
                        }

                        Location brokenBlockLoc = e.getBlock().getLocation();
                        instance.getConfig().set("break." + p.getUniqueId().toString() + "." + "block." + nextNumber, brokenBlockName);
                        instance.getConfig().set("break." + p.getUniqueId().toString() + "." + "loc." + nextNumber, brokenBlockLoc);
                        instance.saveConfig();
                    }
                }
            }

            //delete replaced
            for (int iBreak = 0; iBreak != 5001; iBreak++) {
                if (instance.getConfig().contains("break." + p.getUniqueId().toString() + "." + "block." + iBreak)) {
                    String breakBlock = instance.getConfig().getString("break." + p.getUniqueId().toString() + "." + "block." + iBreak);
                    Location breakLoc = instance.getConfig().getLocation("break." + p.getUniqueId().toString() + "." + "loc." + iBreak);

                    for (int iPlace = 0; iPlace != 5001; iPlace++) {
                        if (instance.getConfig().contains("place." + p.getUniqueId().toString() + "." + "block." + iPlace)) {
                            String placeBlock = instance.getConfig().getString("place." + p.getUniqueId().toString() + "." + "block." + iPlace);
                            Location placeLoc = instance.getConfig().getLocation("place." + p.getUniqueId().toString() + "." + "loc." + iPlace);

                            if (breakBlock.equalsIgnoreCase(placeBlock) && breakLoc.equals(placeLoc)) {
                                instance.getConfig().set("place." + p.getUniqueId().toString() + "." + "block." + iPlace, null);
                                instance.getConfig().set("place." + p.getUniqueId().toString() + "." + "loc." + iPlace, null);
                                instance.getConfig().set("break." + p.getUniqueId().toString() + "." + "block." + iBreak, null);
                                instance.getConfig().set("break." + p.getUniqueId().toString() + "." + "loc." + iBreak, null);
                                instance.saveConfig();
                            }
                        }
                    }
                }
            }
        }

        //trigg2
        for (int iAsIndex = 0; iAsIndex != 101; iAsIndex++) {
            if (instance.getConfig().contains("trigg2." + p.getUniqueId().toString() + iAsIndex)) {
                String brokenBlockName = e.getBlock().getType().name();

                if (brokenBlockName.equals(instance.getConfig().getString("trigg2." + p.getUniqueId().toString() + iAsIndex))) {
                    return;
                }
            }
        }

        //trigg
        for (int iAsIndex = 0; iAsIndex != 101; iAsIndex++) {
            if (instance.getConfig().contains("trigg." + p.getUniqueId().toString() + iAsIndex)) {
                String brokenBlockName = e.getBlock().getType().name();

                if (brokenBlockName.equals(instance.getConfig().getString("trigg." + p.getUniqueId().toString() + iAsIndex))) {
                    return;
                }
            }
        }

        //mark
        for (int iAsIndex = 0; iAsIndex != 101; iAsIndex++) {
            if (instance.getConfig().contains("mark." + iAsIndex)) {
                String placedBlockName = e.getBlock().getType().name();

                if (placedBlockName.equals(instance.getConfig().getString("mark." + iAsIndex))) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {

        Player p = e.getPlayer();

        if (p.hasPermission("block.imun")) return;

        //Trigg mode 2 logic
        if (triggMode2) {
            //save block data
            for (int iAsIndex = 0; iAsIndex != 101; iAsIndex++) {
                if (instance.getConfig().contains("trigg2." + p.getUniqueId().toString() + iAsIndex)) {
                    String brokenBlockName = e.getBlock().getType().name();
                    String triggerBlockName = instance.getConfig().getString("trigg2." + p.getUniqueId().toString() + iAsIndex);

                    if (brokenBlockName.equalsIgnoreCase(triggerBlockName)) {
                        int nextNumber;

                        if (instance.getConfig().isConfigurationSection("place." + p.getUniqueId().toString() + "." + "block.")) {
                            nextNumber = instance.getConfig().getConfigurationSection("place." + p.getUniqueId().toString() + "." + "block.").getKeys(false).size()+1;
                        }else {
                            nextNumber = 1;
                        }

                        Location brokenBlockLoc = e.getBlock().getLocation();
                        instance.getConfig().set("place." + p.getUniqueId().toString() + "." + "block." + nextNumber, brokenBlockName);
                        instance.getConfig().set("place." + p.getUniqueId().toString() + "." + "loc." + nextNumber, brokenBlockLoc);
                        instance.saveConfig();
                    }
                }
            }

            //delete replaced
            for (int iBreak = 0; iBreak != 5001; iBreak++) {
                if (instance.getConfig().contains("break." + p.getUniqueId().toString() + "." + "block." + iBreak)) {
                    String breakBlock = instance.getConfig().getString("break." + p.getUniqueId().toString() + "." + "block." + iBreak);
                    Location breakLoc = instance.getConfig().getLocation("break." + p.getUniqueId().toString() + "." + "loc." + iBreak);

                    for (int iPlace = 0; iPlace != 5001; iPlace++) {
                        if (instance.getConfig().contains("place." + p.getUniqueId().toString() + "." + "block." + iPlace)) {
                            String placeBlock = instance.getConfig().getString("place." + p.getUniqueId().toString() + "." + "block." + iPlace);
                            Location placeLoc = instance.getConfig().getLocation("place." + p.getUniqueId().toString() + "." + "loc." + iPlace);

                            if (breakBlock.equalsIgnoreCase(placeBlock) && breakLoc.equals(placeLoc)) {
                                instance.getConfig().set("place." + p.getUniqueId().toString() + "." + "block." + iPlace, null);
                                instance.getConfig().set("place." + p.getUniqueId().toString() + "." + "loc." + iPlace, null);
                                instance.getConfig().set("break." + p.getUniqueId().toString() + "." + "block." + iBreak, null);
                                instance.getConfig().set("break." + p.getUniqueId().toString() + "." + "loc." + iBreak, null);
                                instance.saveConfig();
                            }
                        }
                    }
                }
            }
        }

        //trigg2
        for (int iAsIndex = 0; iAsIndex != 101; iAsIndex++) {
            if (instance.getConfig().contains("trigg2." + p.getUniqueId().toString() + iAsIndex)) {
                String brokenBlockName = e.getBlock().getType().name();

                if (brokenBlockName.equals(instance.getConfig().getString("trigg2." + p.getUniqueId().toString() + iAsIndex))) {
                    return;
                }
            }
        }

        //trigg
        for (int iAsIndex = 0; iAsIndex != 101; iAsIndex++) {
            if (instance.getConfig().contains("trigg." + p.getUniqueId().toString() + iAsIndex)) {
                String brokenBlockName = e.getBlock().getType().name();

                if (brokenBlockName.equals(instance.getConfig().getString("trigg." + p.getUniqueId().toString() + iAsIndex))) {
                    return;
                }
            }
        }

        //mark
        for (int iAsIndex = 0; iAsIndex != 101; iAsIndex++) {
            if (instance.getConfig().contains("mark." + iAsIndex)) {
                String placedBlockName = e.getBlock().getType().name();

                if (placedBlockName.equals(instance.getConfig().getString("mark." + iAsIndex))) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
