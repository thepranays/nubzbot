package com.nubzbot.Events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;

public class Vote extends ListenerAdapter {
    //v1.0

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        String commandString = e.getMessage().getContentRaw();
        String[] commandI = commandString.split(" ");

        if(commandI[0].equalsIgnoreCase("matdan")){
            try{
                if(!(commandI[1]==null)){
                    String voteTopic = commandString.substring(7);
                    EmbedBuilder votePage = new EmbedBuilder();
                    votePage.setTitle(voteTopic);
                    votePage.setDescription("MATDAN BY:"+e.getAuthor().getName());
                    votePage.setColor(Color.CYAN);
                    e.getChannel().sendMessage(MessageCreateData.fromEmbeds(votePage.build())).queue(message -> {
//                        message.addReaction("✅").queue();
//                        message.addReaction("❌").queue();
                        message.addReaction(Emoji.fromFormatted("✅")).queue();
                        message.addReaction(Emoji.fromFormatted("❌")).queue();
                    });


                }
            }catch(IndexOutOfBoundsException noVoteTopic){
                e.getChannel().sendMessage("Kripiya Matdan kis chiz ka ho rha bataye..").queue();
            }

        }
    }

}
