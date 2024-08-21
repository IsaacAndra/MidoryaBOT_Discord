package com.isaacandra.events;

import com.isaacandra.database.DataBaseConfigMemberJoinCommand;
import com.isaacandra.database.DataBaseConfigMemberLeaveCommand;
import com.isaacandra.database.DataBaseConfigPrefixCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.nio.channels.Channel;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.isaacandra.DevBot.jda;

public class GuildJoin extends ListenerAdapter {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        super.onGuildJoin(event);

        long guildId = event.getGuild().getIdLong();
        String defaultPrefix = "!";
        long channelId = event.getGuild().getDefaultChannel().getIdLong();
        long defaultStickerId = 749054660769218631L;
        long defaultRole = 0L;

        TextChannel defaultChannel = (TextChannel) event.getGuild().getDefaultChannel();

        DataBaseConfigPrefixCommands.setPrefix(guildId, defaultPrefix);
        DataBaseConfigMemberJoinCommand.setConfig(guildId, channelId, defaultStickerId, defaultRole);
        DataBaseConfigMemberLeaveCommand.setConfig(guildId, channelId);


        if (defaultChannel != null) {
            defaultChannel.sendMessage(
                    "Obrigado por adicionar o bot ao servidor! O prefixo padrão para executar um comando é `" +
                            defaultPrefix +
                            "`." +
                            " Você pode alterá-lo usando o comando `!setPrefix <caractere>`.").queue();
        }

        DataBaseConfigPrefixCommands.getPrefix(guildId);
        DataBaseConfigMemberJoinCommand.getConfig(guildId);
        DataBaseConfigMemberLeaveCommand.getConfig(guildId);

        try {
            setActivity(event.getJDA());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setActivity(JDA jda) throws InterruptedException {
        jda.getPresence().setActivity(Activity.playing("Em " + jda.awaitReady().getGuilds().size() + " Servidores!"));
    }
}



