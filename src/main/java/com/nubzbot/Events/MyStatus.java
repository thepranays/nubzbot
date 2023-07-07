package com.nubzbot.Events;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class MyStatus extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);
//        Activity activity = Activity.listening("Your Sweet Voice\n'helpme'");
//        event.getJDA().getPresence().setPresence(OnlineStatus.OFFLINE,true);
        Activity activity = Activity.playing("DO NOT USE !'");
//        Activity activity = Activity.playing("Under construction'");
        event.getJDA().getPresence().setActivity(activity);

    }
}
