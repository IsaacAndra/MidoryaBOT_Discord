package com.isaacandra.commands.slash;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class AnimalSlashCommand {


    public static CommandData createCommand(){
        return Commands.slash("animal", "Finds a random animal.")
                .addOptions(
                        new OptionData(OptionType.STRING, "type", "The type of animal to find")
                                .addChoice("Bird", "bird")
                                .addChoice("Big Cat", "bigcat")
                                .addChoice("Canine", "canine")
                                .addChoice("Fish", "fish")
                );
    }

}
