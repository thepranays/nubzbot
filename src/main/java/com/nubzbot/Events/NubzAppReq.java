package com.nubzbot.Events;

import com.nubzbot.MusicPlayer.GuildMusicManager;
import com.nubzbot.MusicPlayer.PlayerManager;
import com.nubzbot.MusicPlayer.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class NubzAppReq extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        AudioManager audioManager = event.getGuild().getAudioManager();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
        AudioPlayer audioPlayer =guildMusicManager.player;
        TrackScheduler trackScheduler = guildMusicManager.scheduler;

            String[] appReqMsg = event.getMessage().getContentRaw().split("<");  //as format is pause<@!246511799464099840>
            if(event.getChannel().getName().equals("nubzappreq")){

                switch (appReqMsg[0]){
                    case "pause":
                        if(!audioPlayer.isPaused()){
                            audioPlayer.setPaused(true);
                            break;
                        }
                    case "play":
                        audioPlayer.setPaused(false);
                        break;
                    case "skip":
                        if(trackScheduler.getQueue().isEmpty()){
                            audioPlayer.stopTrack();
                            audioManager.closeAudioConnection();
                        }else {
                            trackScheduler.nextTrack();
                        }
                        break;
                    case "seekf":
                        audioPlayer.getPlayingTrack().setPosition(audioPlayer.getPlayingTrack().getPosition()  + 10 *1000);
                        break;
                    case "seekb":
                        audioPlayer.getPlayingTrack().setPosition(audioPlayer.getPlayingTrack().getPosition() - 10 *1000);
                        break;
                    case "stop":
                        audioPlayer.stopTrack();
                        trackScheduler.clearQueue();
                        audioManager.closeAudioConnection();
                        break;
                    default:


                }
            }

    }

}

