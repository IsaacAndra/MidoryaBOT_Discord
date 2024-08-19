package com.isaacandra;

import com.isaacandra.commands.slash.AnimalSlashCommand;
import com.isaacandra.commands.slash.EchoSlashCommand;
import net.dv8tion.jda.api.JDA;

public class RegisterCommands {


    public static void registerCommands(JDA jda) {
        jda.updateCommands().addCommands(
                EchoSlashCommand.createCommand(),
                AnimalSlashCommand.createCommand()
        ).queue();
    }
}
