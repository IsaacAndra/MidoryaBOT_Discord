package com.isaacandra.events;

import com.isaacandra.database.DataBaseConfigMemberJoinCommand;
import com.isaacandra.database.DataBaseConfigMemberLeaveCommand;
import com.isaacandra.database.DataBaseConfigPrefixCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class GuildJoin extends ListenerAdapter {


    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        super.onGuildJoin(event);

        long guildId = event.getGuild().getIdLong();
        String defaultPrefix = "!";
        long channelId = event.getGuild().getDefaultChannel().getIdLong();
        long defaultRole = 0L;

        TextChannel defaultChannel = (TextChannel) event.getGuild().getDefaultChannel();
        String guildName = event.getGuild().getName();

        DataBaseConfigPrefixCommands.setPrefix(guildId, defaultPrefix);
        DataBaseConfigMemberJoinCommand.setConfig(guildId, channelId, defaultRole);
        DataBaseConfigMemberLeaveCommand.setConfig(guildId, channelId);

        DataBaseConfigPrefixCommands.getPrefix(guildId);
        DataBaseConfigMemberJoinCommand.getConfig(guildId);
        DataBaseConfigMemberLeaveCommand.getConfig(guildId);

        if (defaultChannel != null) {
            defaultChannel.sendMessage(
                    "Obrigado por me adicionar ao servidor " +
                            guildName + "! :star_struck:\n" +
                            "O prefixo padrão para executar um comando é `" +
                            defaultPrefix +
                            "`.\n" +
                            "Você pode alterá-lo usando o comando `!setprefix <caractere>`.").queueAfter(2500, TimeUnit.MILLISECONDS);
        }

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



