package com.isaacandra.commands.prefix;

import com.isaacandra.database.DataBaseConfigPrefixCommands;
import com.isaacandra.messages.PrefixEmbedMessages;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SetPrefixCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        long guildId = event.getGuild().getIdLong();
        String settings = DataBaseConfigPrefixCommands.getPrefix(guildId);
        PrefixEmbedMessages embedMessage = new PrefixEmbedMessages();

        if (args[0].equalsIgnoreCase(settings + "setPrefix")) {
            if (!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(event.getMember().getAsMention() +
                        " Você não possui permissão para usar esse comando!"
                ).queue();
                return;
            }
            if (args.length < 2) {  // Verifica se há um argumento adicional para o novo prefixo
                event.getChannel()
                        .sendMessage("")
                        .setEmbeds(
                                embedMessage.embedSetPrefixVerficationMessage(
                                        event.getMember(), event.getGuild().getIconUrl()
                                )
                        )
                        .queue();
                return;
            }

            String newPrefix = args[1];

            DataBaseConfigPrefixCommands.setPrefix(guildId, newPrefix);
            event.getChannel().sendMessage("")
                    .setEmbeds(embedMessage.embedSetPrefixMessage(event.getMember(), newPrefix, event.getGuild().getIconUrl()))
                    .queue();
        }
    }
}
