package com.isaacandra.events;

import com.isaacandra.database.DataBaseConfigMemberLeaveCommand;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberLeave extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        super.onGuildMemberRemove(event);

        long guildId = event.getGuild().getIdLong();
        long[] config = DataBaseConfigMemberLeaveCommand.getConfig(guildId);

        long channelId = config[0]; // ID do canal

        String welcomeMessage =event.getUser().getAsMention() + " Meteu o pé do Servidor!";

        MessageChannel channel = event.getGuild().getTextChannelById(channelId);

        channel.sendMessage(welcomeMessage).queue();
    }
}
