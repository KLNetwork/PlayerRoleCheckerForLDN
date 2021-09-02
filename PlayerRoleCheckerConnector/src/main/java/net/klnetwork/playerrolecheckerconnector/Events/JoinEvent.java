package net.klnetwork.playerrolecheckerconnector.Events;

import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.Command.JoinMode;
import net.klnetwork.playerrolecheckerconnector.Util.CheckerUtil;
import net.klnetwork.playerrolecheckerconnector.Util.JDAUtil;
import net.klnetwork.playerrolecheckerconnector.Util.SQLUtil;
import net.klnetwork.playerrolecheckerconnector.Util.SQLiteUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        new Thread(() -> {
            Player player = e.getPlayer();
            if (!JoinMode.joinMode) return;

            if(SQLiteUtil.getUUIDFromSQLite(e.getPlayer().getUniqueId().toString()) != null){
                player.sendMessage(ChatColor.GREEN + "処理はスキップされました 理由: あなたがバイパスリストに入っているため");
                return;
            }

            if (!CheckerUtil.CheckPlayer(player.getUniqueId())) {
                player.kickPlayer(ChatColor.GOLD + "あなたには参加権限がありません。\n" + ChatColor.AQUA + "Discordを確認してみてください。");
                return;
            }
            e.setJoinMessage(player.getName() + "がログインしました。");
            player.sendMessage(ChatColor.GOLD + "ご苦労さまです。" + ChatColor.AQUA + player.getName() + ChatColor.WHITE + "さん。");
            player.sendMessage(ChatColor.GREEN + "-----------------情報------------------");
            player.sendMessage("MinecraftName: " + e.getPlayer().getName());
            String[] result = SQLUtil.getDiscordFromSQL(e.getPlayer().getUniqueId().toString());
            player.sendMessage("DiscordID: " + result[1]);

            StringBuilder stringBuilder = new StringBuilder();
            List<Role> roleList = JDAUtil.getRolesById(result[1]);
            if (roleList == null) return;
            for (Role role : roleList) {
                stringBuilder.append(role.getName()).append(" ");
            }
            player.sendMessage("DiscordRole: " + stringBuilder);
            player.sendMessage(ChatColor.GREEN + "-------------------------------------");
        }).start();
    }
}