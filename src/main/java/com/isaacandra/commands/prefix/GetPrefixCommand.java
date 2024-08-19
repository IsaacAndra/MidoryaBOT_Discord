package com.isaacandra.commands.prefix;

import com.isaacandra.database.DataBaseConfigPrefixCommands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GetPrefixCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        String message = event.getMessage().getContentRaw();
        long guildId = event.getGuild().getIdLong();
        String[] args = message.split(" ", 2);

        // Comando para obter o prefixo atual
        if (args[0].equalsIgnoreCase( "currentPrefix")) {
            if (event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                String currentPrefix = DataBaseConfigPrefixCommands.getPrefix(guildId);
                event.getChannel().sendMessage("O prefixo atual é: " + currentPrefix).queue();
            } else {
                event.getChannel().sendMessage("Você não tem permissão para usar esse comando").queue();
            }
        }
    }
}
