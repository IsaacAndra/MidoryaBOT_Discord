package com.isaacandra.commands.prefix;

import com.isaacandra.database.DataBaseConfigPrefixCommands;
import com.isaacandra.messages.PrefixEmbedMessages;
import com.isaacandra.utils.RegexUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SetPrefixCommand extends ListenerAdapter {

    RegexUtils regexUtils = new RegexUtils();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        long guildId = event.getGuild().getIdLong();
        String getPrefix = DataBaseConfigPrefixCommands.getPrefix(guildId);
        PrefixEmbedMessages embedMessage = new PrefixEmbedMessages();


        if (args[0].equals(getPrefix + "setprefix")) {
            if (!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(event.getMember().getAsMention() +
                        " Você não possui permissão para usar esse comando!"
                ).queue();
                return;
            }

            // Verifica se é um caractere válido e se é apenas 1 caractére
            if (args.length != 2
                    || !args[1].matches(regexUtils.getSpecialCharacteresRegex())
                    || args[1].length() != 1)
            {
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
