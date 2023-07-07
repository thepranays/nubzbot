package com.nubzbot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;


public class ServerInfo extends ListenerAdapter {

    public synchronized void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] inputFound = e.getMessage().getContentRaw().split(" ");
        if (inputFound[0].equalsIgnoreCase("serverinfo")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("SERVER INFO");
            embedBuilder.setDescription("Toli Ki Jankari");
            embedBuilder.setThumbnail(e.getGuild().getIconUrl());
            embedBuilder.addField("Sarvashaktishali:", e.getGuild().retrieveOwner().complete().getUser().getName(), true);
            embedBuilder.addField("Laundo Ki Sankhyan:", String.valueOf(e.getGuild().getMemberCount()), false);
            embedBuilder.addField("Zinda Launde:", String.valueOf(e.getGuild().getMembers().stream().filter(member -> !member.getUser().isBot()).count()), false);
            embedBuilder.addField("Janmadin", e.getGuild().getTimeCreated().toString(), false);
            embedBuilder.addField("Jamin:", e.getGuild().getRegionRaw(), false);
            embedBuilder.addField("Pramanikaran:", e.getGuild().getVerificationLevel().name(), false);


            embedBuilder.setColor(Color.CYAN);
            e.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }
}