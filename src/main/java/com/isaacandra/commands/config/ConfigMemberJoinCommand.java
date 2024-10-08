package com.isaacandra.commands.config;

import com.isaacandra.database.DataBaseConfigMemberJoinCommand;
import com.isaacandra.database.DataBaseConfigPrefixCommands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ConfigMemberJoinCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        String command = args[0].toLowerCase();
        long guildId = event.getGuild().getIdLong();
        String prefix = DataBaseConfigPrefixCommands.getPrefix(guildId);


        if (command.equalsIgnoreCase(prefix + "setChannelWelcome")) {
            if (!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(event.getMember().getAsMention() +
                        " Você não possui permissão para usar esse comando!"
                ).queue();
                return;
            }
                if (args.length == 2) {
                    try {
                        long channelId = Long.parseLong(args[1]);

                        DataBaseConfigMemberJoinCommand.setConfig(guildId, channelId, null);
                        event.getChannel().sendMessage("Configuração atualizada com sucesso!").queue();
                    } catch (NumberFormatException e) {
                        event.getChannel().sendMessage("IDs inválidos fornecidos.").queue();
                    }
                } else {
                    event.getChannel().sendMessage(
                                    "Uso incorreto ou ID inválido.\n Use: \n"
                                            + prefix
                                            + "setchannelwelcome "
                                            + "<channelId>")
                            .queue();
                }
        }
    }
}
