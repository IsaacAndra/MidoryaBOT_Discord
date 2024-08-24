package com.isaacandra.events;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.logging.Logger;

public class ReadyEventListener extends ListenerAdapter {


    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);

        System.out.println("O bot está rodando e está online!");


    }

}
