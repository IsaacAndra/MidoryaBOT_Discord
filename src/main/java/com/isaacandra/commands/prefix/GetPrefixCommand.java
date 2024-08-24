package com.isaacandra.commands.prefix;

import com.isaacandra.database.DataBaseConfigPrefixCommands;
import com.isaacandra.messages.PrefixEmbedMessages;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GetPrefixCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        String content = event.getMessage().getContentRaw();
        long guildId = event.getGuild().getIdLong();
        String[] args = content.split(" ", 2);
        String botMention = event.getJDA().getSelfUser().getAsMention();
        boolean prefixString = args[0].equals("prefix");

        // Comando para obter o prefixo atual
        if (args.length > 0 && prefixString || content.startsWith(botMention)) {
            if (!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(event.getMember().getAsMention() +
                        " Você não possui permissão para usar esse comando!"
                ).queue();
                return;
            }
            String currentPrefix = DataBaseConfigPrefixCommands.getPrefix(guildId);
            PrefixEmbedMessages embedMessage = new PrefixEmbedMessages();
            event.getChannel().sendMessage("").setEmbeds(embedMessage.embedGetPrefixMessage(event.getMember(), currentPrefix, event.getGuild().getIconUrl())).queue();

        }
    }
}
