package com.isaacandra.interactions;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class EchoCommandInteraction extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("echo")) {
            if (event.isAcknowledged()) {
                System.out.println("Já foi respondido!");;
            }

            OptionMapping messageOption = event.getOption("message");
            String message = messageOption.getAsString();

            OptionMapping timesOption = event.getOption("times");
            int times = timesOption != null ? timesOption.getAsInt() : 1;

            OptionMapping ephemeralOption = event.getOption("ephemeral");
            boolean isEphemeral = ephemeralOption != null && ephemeralOption.getAsBoolean();


            String response = message.concat(", ").repeat(times) + ".";

            try {

                event.reply(response).setEphemeral(isEphemeral).queue();

            } catch (IllegalArgumentException error) {
                if (response.length() >= 2000) {

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.RED);
                    embed.setTitle("Erro");
                    embed.setDescription("A resposta tem mais de 2000 caracteres, o que excede o limite permitido.");

                    event.replyEmbeds(embed.build()).queue();

                }
            }




        }
    }
}
