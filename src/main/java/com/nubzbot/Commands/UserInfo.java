package com.nubzbot.Commands;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import javax.swing.plaf.ColorUIResource;



public class UserInfo extends ListenerAdapter {
    //UNDER WORK


    public void onMessageReceived(MessageReceivedEvent e){
   

        String[] commandMessage = e.getMessage().getContentRaw().split(" ");
        try {
            if (commandMessage[0].equalsIgnoreCase("userinfo")) {
                Member mentionedMember = e.getMessage().getMentions().getMembers().get(0);
                try {
                    Activity mentionedMemberActivity = mentionedMember.getActivities().get(0);
                    EmbedBuilder userInfo = new EmbedBuilder();
                    userInfo.setTitle("USER INFO");
                    userInfo.setDescription("Launde Ki Jankari");
                    userInfo.setThumbnail(mentionedMember.getUser().getAvatarUrl());
                    userInfo.addField("Asli naam:", mentionedMember.getEffectiveName(), false);
                    userInfo.addField("Currently Playing:",mentionedMember.getActivities().get(0).toString(), false);
                    userInfo.addField("Sadsya since:", mentionedMember.getTimeJoined().toString(), false);
                    userInfo.addField("Vartaman Stithi:",mentionedMember.getOnlineStatus().toString(),false);

//                    if(mentionedMember.getRoles().get(0) == null) {
////                      userInfo.addField("Awkaad:", "Awkaadless", false);
////                      }else{
////                      userInfo.addField("Awkaad:", mentionedMember.getRoles().get(0).getName(), false);
////                       }

                    userInfo.setColor(ColorUIResource.CYAN);
                    e.getChannel().sendMessage((CharSequence) userInfo.build()).queue();

                }catch(IndexOutOfBoundsException noActivityExcep){
                    EmbedBuilder userInfo = new EmbedBuilder();
                    userInfo.setTitle("USER INFO");
                    userInfo.setDescription("Launde Ki Jankari");
                    userInfo.setThumbnail(mentionedMember.getUser().getAvatarUrl());
                    userInfo.addField("Asli naam:", mentionedMember.getEffectiveName(), false);
                    userInfo.addField("Currently Playing:","NALLA BETHA HAI", false);
                    userInfo.addField("Sadsya since:", mentionedMember.getTimeJoined().toString(), false);
                    userInfo.addField("Vartaman Stithi:",mentionedMember.getOnlineStatus().toString(),false);
//                    if(mentionedMember.getRoles().get(0) ==) {
//                        userInfo.addField("Awkaad:", "Awkaadless", false);
//                     }else{
//                        userInfo.addField("Awkaad:", mentionedMember.getRoles().get(0).getName(), false);
//                    }


                    userInfo.setColor(ColorUIResource.CYAN);
                    e.getChannel().sendMessage((MessageCreateData.fromEmbeds(userInfo.build()))).queue();
                }
            }
        }catch(IndexOutOfBoundsException noMentionException){
            e.getChannel().sendMessage("Please Mention Someone").queue();

        }



    }
}
