package xaviermc.top.timelimitdrop;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TimeLimitDrop extends JavaPlugin implements Listener {
    private int maxPlayTime;
    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        FileConfiguration config = getConfig();
        maxPlayTime = config.getInt("maxPlayTime");
        luckPerms = LuckPermsProvider.get();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("TimeLimitDrop 已加载！");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int playTimeMinutes = player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE) / 20 / 60;
        if (!player.hasPlayedBefore() || playTimeMinutes < maxPlayTime) {
            setPermission(player, true);
        }
        player.incrementStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        int playTimeMinutes = player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE) / 20 / 60;
        if (playTimeMinutes < maxPlayTime) {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                player.performCommand("acfetch 1");
                String message = ChatColor.GREEN + "[新手保护] " + ChatColor.YELLOW + "死亡不掉落时间：" + ChatColor.GOLD + maxPlayTime + ChatColor.YELLOW + " 分钟，当前剩余 " + ChatColor.GOLD + (maxPlayTime - playTimeMinutes) + ChatColor.YELLOW + " 分钟。\n" +
                        ChatColor.YELLOW + "若系统未送回死亡掉落物，可使用 " + ChatColor.GREEN + "/acfetch 1" + ChatColor.YELLOW + " 命令领取。\n" +
                        ChatColor.YELLOW + "若在死亡后的 " + ChatColor.GREEN + "5 分钟" + ChatColor.YELLOW + " 内未领取掉落物，将视为您主动放弃掉落物。\n";
                player.sendMessage(message);
            }, 20 * 3);
        } else {
            setPermission(player, false);
        }
    }

    private void setPermission(Player player, boolean hasPermission) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        String playerName = player.getName();
        int playTimeMinutes = player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE) / 20 / 60;

        if (user != null) {
            user.data().add(Node.builder("angelchest.fetch").value(hasPermission).build());
            luckPerms.getUserManager().saveUser(user);

            getLogger().info(playerName + " 游玩时间：" + playTimeMinutes + " 分钟，权限设置情况：" + (hasPermission ? "已设置" : "未设置"));
        }
    }
}