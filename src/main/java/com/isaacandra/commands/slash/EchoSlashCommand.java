package com.isaacandra.commands.slash;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class EchoSlashCommand {

    public static CommandData createCommand(){
        return Commands.slash("echo", "Repeats messages back to you.")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS))
                .addOption(OptionType.STRING, "message", "The message to repeat.")
                .addOption(OptionType.INTEGER, "times", "The number of times to repeat the message")
                .addOption(OptionType.BOOLEAN, "ephemeral", "Whether or not the message should be sent as an ephemeral message.");
    }
}
