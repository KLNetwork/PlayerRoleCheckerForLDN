package net.klnetwork.playerrolechecker.playerrolechecker.JDA;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.klnetwork.playerrolechecker.playerrolechecker.JDA.Events.Verify;

import javax.security.auth.login.LoginException;

import static net.klnetwork.playerrolechecker.playerrolechecker.PlayerRoleChecker.plugin;

public class JDA {

    public static net.dv8tion.jda.api.JDA jda;

    public static void init(){
        try {
            jda = JDABuilder.createDefault(plugin.getConfig().getString("Discord.DiscordToken"))
                    .setStatus(OnlineStatus.ONLINE)
                    .addEventListeners(new Verify())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}