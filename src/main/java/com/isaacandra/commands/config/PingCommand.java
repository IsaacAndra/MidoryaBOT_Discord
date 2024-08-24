package com.isaacandra.commands.config;

import com.isaacandra.DevBot;
import com.isaacandra.database.DataBaseConfigPrefixCommands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        long guildId = event.getGuild().getIdLong();
        String prefix = DataBaseConfigPrefixCommands.getPrefix(guildId);

        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String command = args[0].toLowerCase();

        if (command.equals(prefix + "ping")) {
            if (!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(event.getMember().getAsMention() +
                        "Você não tem permissão para usar esse comando!");
            }
            TextChannel textChannel = (TextChannel) event.getChannel();

            textChannel.sendMessage((DevBot.jda.getGatewayPing()) + "ms").queue();

        }
    }
}
