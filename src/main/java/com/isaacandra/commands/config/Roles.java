package com.isaacandra.commands.config;

import com.isaacandra.database.DataBaseConfigMemberJoinCommand;
import com.isaacandra.database.DataBaseConfigPrefixCommands;
import com.isaacandra.messages.RolesEmbedMessages;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Roles extends ListenerAdapter {

    private final Map<Byte, String> rolesMap = new HashMap<>();
    private final Map<String, Map<Byte, String>> guildRolesMapMap = new HashMap<>();
    private final Map<String, Boolean> isEditingAutoRole = new HashMap<>();
    private final Map<String, Member> memberEditingAutoRoleMap = new HashMap<>();
    private final RolesEmbedMessages rolesEmbedMessages = new RolesEmbedMessages();
    private final Map<String, ScheduledExecutorService> timers = new HashMap<>();


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        byte roleIndexes = 0;
        StringBuilder stringBuilder = new StringBuilder();

        String[] args = event.getMessage().getContentRaw().split(" ");
        String gId = event.getGuild().getId();
        List<Role> roles = event.getGuild().getRoles();
        int roleBotPosition = event.getGuild().getBotRole().getPosition();


        long guildId = event.getGuild().getIdLong();
        String prefix = DataBaseConfigPrefixCommands.getPrefix(guildId);


        if (args[0].equals(prefix + "autorole")) {
            if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(event.getMember().getAsMention() +
                        " Você não possui permissão para usar esse comando!"
                ).queue();
                return;
            }

            for (Role role : roles) {
                if (!role.isPublicRole() && role.getPosition() < roleBotPosition) {

                    stringBuilder
                            .append(roleIndexes)
                            .append(" - ")
                            .append(role.getName())
                            .append("\n");

                    rolesMap.put(roleIndexes, role.getId());
                    roleIndexes++;
                }
            }


            if (stringBuilder.length() == 0) {
                event
                        .getChannel()
                        .sendMessage("")
                        .setEmbeds(
                                rolesEmbedMessages.embedRoleErrorMessage(
                                        event.getMember(), event.getGuild().getIconUrl()
                                )
                        )
                        .queue();
                return;
            }

            event.getChannel().sendMessage("")
                    .setEmbeds(rolesEmbedMessages
                            .embedRoleSelectionMessage(
                                    event.getMember(), stringBuilder.toString(),
                                    event.getGuild().getIconUrl()
                            )
                    )
                    .queue();


            guildRolesMapMap.put(gId, rolesMap);
            isEditingAutoRole.put(gId, true);
            memberEditingAutoRoleMap.put(gId, event.getMember());

            ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
            timer.schedule(() -> {
                if (isEditingAutoRole.getOrDefault(gId, false)) {
                    event.getChannel().sendMessage("")
                            .setEmbeds(rolesEmbedMessages
                                    .embedRoleTimerMessage(
                                            event.getMember(), event.getGuild().getIconUrl()
                                    )
                            )
                            .queue();
                    isEditingAutoRole.put(gId, false);
                }
            }, 10, TimeUnit.SECONDS);

            timers.put(gId, timer);


        } else if (args[0].matches("^[0-9]{1,3}+$") &&
                isEditingAutoRole.getOrDefault(gId, false)) {

            if (!Objects.equals(event.getMember(), memberEditingAutoRoleMap.get(gId)) ||
                    event.getAuthor().isBot() ||
                    guildRolesMapMap.get(gId) == null ||
                    Byte.parseByte(args[0]) >= guildRolesMapMap.get(gId).size()
            ) {

                event
                        .getChannel()
                        .sendMessage("")
                        .setEmbeds(
                                rolesEmbedMessages.embedRoleErrorMessage(
                                        event.getMember(), event.getGuild().getIconUrl()
                                )
                        )
                        .queue();
                return;
            }

            String selectedRoleId = guildRolesMapMap.get(gId).get(Byte.parseByte(args[0]));
            Role selectedRole = event.getGuild().getRoleById(selectedRoleId);
            RolesEmbedMessages rolesEmbedMessages = new RolesEmbedMessages();

            event.getChannel().sendMessage("").setEmbeds(rolesEmbedMessages
                    .embedRoleSelectedMessage(
                            event.getMember(), selectedRole.getName(), event.getGuild().getIconUrl())).queue();

            long[] config = DataBaseConfigMemberJoinCommand.getConfig(guildId);
            long channelId = config[0];
            long autoRole = Long.valueOf(selectedRoleId);


            DataBaseConfigMemberJoinCommand.setConfig(guildId, channelId, autoRole);
            isEditingAutoRole.put(gId, false);

            // Cancela o timer quando a role é selecionada
            ScheduledExecutorService timer = timers.get(gId);
            if (timer != null) {
                timer.shutdown();
            }

        }
    }
}
