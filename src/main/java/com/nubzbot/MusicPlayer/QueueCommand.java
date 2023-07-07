package com.nubzbot.MusicPlayer;


import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import javax.annotation.Nonnull;



public class QueueCommand extends ListenerAdapter {


    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        GuildMusicManager musicManager = playerManager.getGuildMusicManger(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

        String[] messageReceived = event.getMessage().getContentRaw().split(" ");
        if(messageReceived[0].equalsIgnoreCase("gitmaala") ) {
            //CHECKS IF QUEUE IS EMPTY OR NOT
            if (queue.isEmpty()) {
                channel.sendMessage("The queue is empty").queue();

                return;
            }
            //GETS MINIMUM  20 NEXT SONGS
            int trackCount = Math.min(queue.size(), 20);
            List<AudioTrack> tracks = new ArrayList<>(queue);
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("QueueList:");

            for (int i = 0; i < trackCount; i++) {
                AudioTrack track = tracks.get(i);
                AudioTrackInfo info = track.getInfo();

                builder.appendDescription(String.format(
                        "%s - %s\n",
                        info.title,
                        info.author
                ));
            }

            channel.sendMessage(builder.build()).queue();

        }


    }
}
