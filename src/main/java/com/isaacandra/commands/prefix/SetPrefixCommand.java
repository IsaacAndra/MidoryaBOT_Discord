package com.isaacandra.commands.prefix;

import com.isaacandra.database.DataBaseConfigPrefixCommands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SetPrefixCommand  extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        long guildId = event.getGuild().getIdLong();
        String settings = DataBaseConfigPrefixCommands.getPrefix(guildId);

        if (args[0].equalsIgnoreCase(settings + "setPrefix")) {
            if (event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                if (args.length < 2) {  // Verifica se há um argumento adicional para o novo prefixo
                    event.getChannel().sendMessage("Por favor, forneça um prefixo! Exemplo: !setPrefix $").queue();
                    return;
                }

                String newPrefix = args[1];

                DataBaseConfigPrefixCommands.setPrefix(guildId, newPrefix);
                event.getChannel().sendMessage("Prefixo alterado para: " + newPrefix).queue();
            } else {
                event.getChannel().sendMessage("Você não tem permissão para usar este comando!").queue();
            }
        }
    }
}
