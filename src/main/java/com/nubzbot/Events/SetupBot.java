package com.nubzbot.Events;


import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class SetupBot extends ListenerAdapter {
    private String roleName;

    private SetupBot(String roleName) {
        this.roleName = roleName;

    }


    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        User joinedUser = e.getUser();
        Role role =e.getGuild().getRolesByName("EXAMPLE",true).get(0);
        e.getGuild().addRoleToMember(joinedUser.getId(),role);

        }
    }



