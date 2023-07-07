package com.nubzbot.Events;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;


import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;


public class GreetUser extends ListenerAdapter {

    //EVENT WHEN NEW USER JOINS
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        //GET NEW USER AND TEXT CHANNEL TO PRINT WELCOME MESSAGE
        User newUserJoined = event.getUser();
        TextChannel textChannel;
        try {
            textChannel = event.getGuild().getTextChannelsByName("welcome_members", true).get(0);
        }catch(IndexOutOfBoundsException excep){
            textChannel = event.getGuild().createTextChannel("welcome_members").complete();
        }

        //EMBED BUILDER
        EmbedBuilder embedNewMemberJoined = new EmbedBuilder();
        embedNewMemberJoined.setTitle("Dhol Bajao re!,Swagat Karo Naye Sadsya Ka");
        embedNewMemberJoined.addField("Suswagatam:",newUserJoined.getName(),false);
        embedNewMemberJoined.setImage(newUserJoined.getAvatarUrl());
        embedNewMemberJoined.setColor(Color.CYAN);

        textChannel.sendMessage(embedNewMemberJoined.build()).queue();

    }



    //EVENT WHEN USER LEAVES
    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        super.onGuildMemberRemove(event);

        //GET LEFT USER AND TEXT CHANNEL TO JOIN
        User userLeft = event.getUser();
        TextChannel textChannel;
        try {
            textChannel = event.getGuild().getTextChannelsByName("left_members", true).get(0);
        }catch(IndexOutOfBoundsException excep){
            textChannel = event.getGuild().createTextChannel("left_members").complete();
        }

        //EMBED BUILDER
        EmbedBuilder embedNewMemberJoined = new EmbedBuilder();
        embedNewMemberJoined.setTitle("Dukh Ki Baat Hai,Humne Ek Launda Kho Diya");
        embedNewMemberJoined.addField("Alvida:",userLeft.getName(),false);
        embedNewMemberJoined.setImage(userLeft.getAvatarUrl());
        embedNewMemberJoined.setColor(Color.CYAN);

        textChannel.sendMessage(embedNewMemberJoined.build()).queue();
    }
}
