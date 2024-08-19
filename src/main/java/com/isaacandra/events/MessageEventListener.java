package com.isaacandra.events;


import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MessageEventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if (event.getAuthor().isBot()) return;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        User user = event.getAuthor();
        String channelId= "1274816871052611664"; //Log-mensagens channel ID

        MessageChannel message = event.getGuild().getTextChannelById(channelId);

        message.sendMessage(formattedDateTime + " "
                + user.getEffectiveName()
                + " enviou: "
                + event.getMessage().getContentRaw()
                + " [Canal]: "
                + event.getChannel().getName()).queue();

//            System.out.println(formattedDateTime + " "
//                    + user.getEffectiveName()
//                    + " enviou: "
//                    + event.getMessage().getContentDisplay()
//                    + " [Canal]: "
//                    + event.getChannel().getName());

    }
}
