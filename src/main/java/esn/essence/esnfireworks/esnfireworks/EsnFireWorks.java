package esn.essence.esnfireworks.esnfireworks;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public final class EsnFireWorks extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("§aвключён.");
//  this.getCommand("esnfireworks").setExecutor(this);
        getCommand("esnfireworks").setExecutor(new EsnFireWorksCommand());
// Plugin startup logic

    }

    public void onDisable() {
        getLogger().info("§cотключён.");
        // Plugin shutdown logic
    }

    private static class EsnFireWorksCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender.hasPermission("esnfw.start")) {
                if (args.length != 6) {
                    return false;
                }

                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = Integer.parseInt(args[2]);
                int interval = Integer.parseInt(args[3]);
                int power = Integer.parseInt(args[4]);
                int count = Integer.parseInt(args[5]);

                Location location = new Location(Bukkit.getWorlds().get(0), x, y, z);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < count; i++) {
                            Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                            FireworkMeta meta = firework.getFireworkMeta();
                            meta.addEffect(FireworkEffect.builder()
                                    .with(FireworkEffect.Type.BALL_LARGE)
                                    .withColor(Color.RED)
                                    .withFade(Color.ORANGE)
                                    .withTrail()
                                    .withFlicker()
                                    .build());
                            meta.setPower(power);
                            firework.setFireworkMeta(meta);
                        }
                    }
                }.runTaskTimer(Bukkit.getPluginManager().getPlugin("EsnFireWorks"), 0, interval * 20);
                sender.sendMessage("§6[EsnFireWorks] §fАвтоматический запуск фейерверков начался в " + location + "с интервалом " + interval + " секунд");
                return true;
            } else {
                sender.sendMessage("§cУ вас нет разрешения на использование этой команды!");
                return true;
            }
        }
    }

}
