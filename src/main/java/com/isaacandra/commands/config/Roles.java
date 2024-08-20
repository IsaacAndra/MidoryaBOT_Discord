package com.isaacandra.commands.config;

import com.isaacandra.database.DataBaseConfigMemberJoinCommand;
import com.isaacandra.database.DataBaseConfigPrefixCommands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class Roles extends ListenerAdapter {

    private final Map<Byte, String> rolesMap = new HashMap<>();
    private final Map<String, Map<Byte, String>> guildRolesMapMap = new HashMap<>();
    private final Map<String, Boolean> isEditingAutoRole = new HashMap<>();
    private final Map<String, Member> memberEditingAutoRoleMap = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        byte roleIndexes = 0;

        String[] args = event.getMessage().getContentRaw().split(" ");
        String gId = event.getGuild().getId();


        long guildId = event.getGuild().getIdLong();
        String prefix = DataBaseConfigPrefixCommands.getPrefix(guildId);

        if (args[0].equalsIgnoreCase(prefix + "autoRole")) {
            if (!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(event.getMember().getAsMention() +
                        " Você não possui permissão para usar esse comando!"
                );
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (Role role : event.getGuild().getRoles()) {
                if (!role.isPublicRole()) {

                    rolesMap.put(roleIndexes, role.getId());

                    stringBuilder
                            .append(roleIndexes)
                            .append(" - ")
                            .append(role.getName()).append("\n");

                    roleIndexes++;
                }

            }

            guildRolesMapMap.put(gId, rolesMap);
            isEditingAutoRole.put(gId, true);
            memberEditingAutoRoleMap.put(gId, event.getMember());

            event.getChannel().sendMessage(stringBuilder).queue();

        } else if (args[0].matches("^[0-9]{1,3}$") &&
                isEditingAutoRole.get(gId)) {

            if (!event.getMember().equals(memberEditingAutoRoleMap.get(gId)) ||
                    event.getAuthor().isBot() ||
                    guildRolesMapMap.get(gId) == null) {
                return;
            }

            String selectedRoleId = guildRolesMapMap.get(gId).get(Byte.parseByte(args[0]));
            Role selectedRole = event.getGuild().getRoleById(selectedRoleId);

            event.getChannel().sendMessage("O cargo selecionado foi " +
                    selectedRole.getName()).queue();

            long[] config = DataBaseConfigMemberJoinCommand.getConfig(guildId);
            long channelId = config[0];
            long stickerId = config[1];

            if (channelId != 0 && stickerId != 0) { // Verifica se os valores não são zero
                DataBaseConfigMemberJoinCommand.setConfig(guildId, channelId, stickerId, Long.valueOf(selectedRoleId));
                isEditingAutoRole.put(gId, false);
            } else {
                event.getChannel().sendMessage("Configuração falhou: channelId ou stickerId está faltando.").queue();
            }
        }
    }
}
