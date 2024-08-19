package com.isaacandra;

import com.isaacandra.commands.config.ConfigMemberJoinCommand;
import com.isaacandra.commands.config.ConfigMemberLeaveCommand;
import com.isaacandra.commands.config.PingCommand;
import com.isaacandra.commands.prefix.GetPrefixCommand;
import com.isaacandra.commands.prefix.SetPrefixCommand;
import com.isaacandra.events.MemberJoin;
import com.isaacandra.events.MemberLeave;
import com.isaacandra.interactions.EchoCommandInteraction;
import com.isaacandra.events.MessageEventListener;
import com.isaacandra.events.ReadyEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class DevBot {

    public static JDA jda;

    public static void main(String[] args){
        JDABuilder api = JDABuilder.createDefault(System.getenv("BOT_TOKEN"));
        jda = api
                .enableIntents(
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MEMBERS
                )
                .addEventListeners(
                        new ReadyEventListener(),
                        new MessageEventListener(),
                        new EchoCommandInteraction(),
                        new MemberJoin(),
                        new MemberLeave(),
                        new PingCommand(),
                        new SetPrefixCommand(),
                        new GetPrefixCommand(),
                        new ConfigMemberJoinCommand(),
                        new ConfigMemberLeaveCommand()
                )
                .build();

        RegisterCommands.registerCommands(jda);

    }
}
