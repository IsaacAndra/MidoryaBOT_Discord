package com.isaacandra.commands.config;

import com.isaacandra.DevBot;
import com.isaacandra.database.DataBaseConfigPrefixCommands;
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
        if (args[0].startsWith(prefix)) {
            String command = args[0].substring(prefix.length()).toLowerCase();

            if (command.equals("ping")) {
                TextChannel textChannel = (TextChannel) event.getChannel();

                textChannel.sendMessage((DevBot.jda.getGatewayPing()) + "ms").queue();

            }


        }
    }
}
