package com.nubzbot.MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;


import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;



import java.util.HashMap;

import java.util.Map;

public class PlayerManager  {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long,GuildMusicManager> musicManagerMap;



    private PlayerManager() {
        this.playerManager = new DefaultAudioPlayerManager();
        this.musicManagerMap = new HashMap<>();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);


    }
    public GuildMusicManager getGuildMusicManger(Guild guild) {
            long guildId = guild.getIdLong();
            GuildMusicManager musicManager = musicManagerMap.get(guildId);
            if(musicManager == null){
                musicManager = new GuildMusicManager(playerManager,guild);
                musicManagerMap.put(guildId,musicManager);
            }
            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

            return musicManager;
    }



    public void loadAndPlay(TextChannel channel, String trackUrl){
        GuildMusicManager musicManager = getGuildMusicManger(channel.getGuild());
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                if(audioTrack.getInfo().length==0){
                    play(musicManager,audioTrack);
                    channel.sendMessage("Playing:"+audioTrack.getInfo().title).queue();



                }else {
                    channel.sendMessage("Adding To Queue:" + audioTrack.getInfo().title).queue();
                    play(musicManager, audioTrack);


                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                AudioTrack firstTrack = audioPlaylist.getSelectedTrack();
                if(firstTrack==null){
                    firstTrack = audioPlaylist.getTracks().get(0);

                }

                channel.sendMessage("Adding to queue:"+firstTrack.getInfo().title).queue();
                play(musicManager,firstTrack);

            }

            @Override
            public void noMatches() {
                channel.sendMessage("Invalid URL:"+trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Unexpected Error:"+e.getMessage()).queue();
            }
        });
    }

    private void play(GuildMusicManager musicManager,AudioTrack track){
        musicManager.scheduler.queue(track);
    }

    //FOR LAVA THREADS {SYNCRHONIZED}
    public static synchronized PlayerManager getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
