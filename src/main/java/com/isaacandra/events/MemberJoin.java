package com.isaacandra.events;

import com.isaacandra.database.DataBaseConfigMemberJoinCommand;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoin extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        long guildId = event.getGuild().getIdLong();
        long[] config = DataBaseConfigMemberJoinCommand.getConfig(guildId);

        long channelId = config[0]; // ID do canal
        long stickerId = config[1]; // ID do sticker

        MessageChannel channel = event.getGuild().getTextChannelById(channelId);

        if (channel != null && stickerId != 0) {
            String welcomeMessage = "Seja bem-vindo ao Servidor, " + event.getUser().getAsMention() + "!";
            channel.sendMessage(welcomeMessage)
                    .setStickers(StickerSnowflake.fromId(stickerId))
                    .queue();
        } else {
            System.out.println("Canal ou sticker não configurados corretamente.");
        }
    }

}
