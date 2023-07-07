package com.nubzbot.Events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


public class Spam extends ListenerAdapter {

    //v1.0

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        String spamCommandString = e.getMessage().getContentRaw();
        String[] spamCommand = spamCommandString.split(" ");

        if (spamCommand[0].equalsIgnoreCase("spam")) {
            if (e.getMessage().getMember().hasPermission(Permission.BAN_MEMBERS)) {
                //CHECK WHEATHER HAS VALID PERMISSION TO USE THIS SPAM FEATURE
                try {
                    if (!spamCommand[1].isEmpty()) {
                        Member member = e.getMessage().getMentionedMembers().get(0);
                        User user = member.getUser();

                        user.openPrivateChannel().queue((channel) ->
                        {
                            try {
                                if (!(spamCommand[2] == null)) {
                                    for (int i = 0; i <= 10; i++) {
                                        channel.sendMessage(spamCommandString.substring(28)).queue();
                                        // DO NOT CHANGE 28 VALUE IT IS THERE to neglect some characters before the spam text
                                        //  {SPAM + 24 CHAR ID(MENTION)}

                                    }
                                } else {
                                    //IF NOT NO SPECIFIC SPAM MESSAGE INPUT FOUND
                                    System.out.println("asdasdas");
                                    for (int i = 0; i <= 10; i++) {
                                        channel.sendMessage(".").queue();
                                    }

                                }
                            }catch(ArrayIndexOutOfBoundsException arrayEx){
                                e.getChannel().sendMessage("Mention Message").queue();
                            }


                        });


                    }
                } catch (ArrayIndexOutOfBoundsException arrayE) {
                    e.getChannel().sendMessage("Mention Someone").queue();
                    //CHECK WHEATHER USER IS MENTIONED SOMEONE OR NOT IF NOT THEN PRINT THIS;


                }

            } else {
                e.getChannel().sendMessage("LAVDE AWKAAD NHI TERI!!").queue();
            }
        }
    }
}


