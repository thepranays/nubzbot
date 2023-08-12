package com.nubzbot.Events;

import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;


import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;

import javax.annotation.Nonnull;
import java.util.List;


public class SelfDefense extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(@Nonnull GuildVoiceUpdateEvent event) {
        if(event.getChannelLeft()==null) return; //as join event
        //ASYNC Request to get page of audit log
        AuditLogPaginationAction auditLogEntries = event.getGuild().retrieveAuditLogs();
        List<AuditLogEntry> auditLogEntryList = auditLogEntries.complete();
        if(auditLogEntryList.get(0).getType().toString().equals("MEMBER_VOICE_KICK") && event.getMember().getUser().getId().equals("246511799464099840")){
                           try {

                               User sinUser = auditLogEntryList.get(0).getUser();
                               Member sinMember = event.getGuild().getMember(sinUser);
                               event.getGuild().kickVoiceMember(sinMember).complete();


                               sinUser.openPrivateChannel().queue((channel) ->{
                                    channel.sendMessage("आत्मरक्षा!,जनहित में जारी.").queue();
                               });




                           }catch(IllegalStateException e){
                               System.out.println("Caught Null pointer in selfdefense");
                           }
        }


//////        246511799464099840 -->My ID

    }


    }


