package com.nubzbot.MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import java.awt.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;


//Completed Test v1.0


public class MusicPlayer extends ListenerAdapter {
    private String musicPlayerEmbedId;
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//    private final Map<String, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<>();
    private ScheduledFuture<?> scheduledFuture;
    private SelfUser mySelf;
    private javax.swing.Timer repeatForCurrentSongEmbed;

    //To Setup
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {

        if(!(Objects.equals(e.getMember(), e.getGuild().getSelfMember()))){
                if (e.getMessage().getContentRaw().equalsIgnoreCase("nubzsetup")) {

                    if (e.getGuild().getTextChannelsByName("NubzMusicRequest", true).isEmpty() && e.getGuild().getTextChannelsByName("NubzMusicPlayer", true).isEmpty()) {

                        e.getGuild().createTextChannel("NubzMusicRequest").queue();
                        e.getGuild().createTextChannel("NubzMusicPlayer").queue();
                        e.getChannel().sendMessage("NubzMusicRequest:To Play A Song").queue();
                        e.getChannel().sendMessage("NubzMusicPlayer:To Access The Player").queue();
                        e.getChannel().sendMessage("Boja Bistar Bandh liya!!").queue();

                        Thread thread = new Thread(() -> {
                            try {
                                Thread.sleep(3000);

                            } catch (InterruptedException e1) {
                                System.out.println("Music Player got interrputed");
                            }
                            createPlayer(e.getGuild());
                        });
                        thread.start();



                    }else{
                        e.getChannel().sendMessage("Boja Bistar Idhar hi hai!").queue();
                    }
                }
            }
    }


    //When Member Access The MusicPlayerFeature
    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        super.onGuildMessageReactionAdd(event);
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        AudioManager audioManager = event.getGuild().getAudioManager();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
        AudioPlayer audioPlayer =guildMusicManager.player;


        TrackScheduler trackScheduler = guildMusicManager.scheduler;

        if(!event.getMember().equals(event.getGuild().getSelfMember())){
            if(event.getChannel().getName().equalsIgnoreCase("nubzmusicplayer")){
                switch(event.getReaction().getReactionEmote().getEmoji()){
                    case "⏸":
                        if(audioManager.isConnected()) {
                            if (!(audioPlayer.getPlayingTrack() == null)) {
                                audioPlayer.setPaused(true);
                            }
                        }
                        break;

                    case "▶":
                        if(audioManager.isConnected()) {
                            if (audioPlayer.isPaused()) {
                                audioPlayer.setPaused(false);

                            }
                        }
                        break;

                    case "⏭":
                        if(trackScheduler.getQueue().isEmpty()){
                            audioPlayer.stopTrack();
                            audioManager.closeAudioConnection();
                        }else {
                            trackScheduler.nextTrack();
                        }
                        break;


                    case "⏹":
                        if(audioManager.isConnected()) {
                            if (!(audioPlayer.getPlayingTrack() == null)) {
                                audioPlayer.stopTrack();
                                trackScheduler.clearQueue();
                                audioManager.closeAudioConnection();

                            }
                        }
                        break;
                    default:

                        break;


                }
            }
        }
    }



    //As i am lazy in order to Do remove reaction properly therefore same when reaction removed :D
    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {
        super.onGuildMessageReactionRemove(event);
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        AudioManager audioManager = event.getGuild().getAudioManager();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
        AudioPlayer audioPlayer =guildMusicManager.player;

        TrackScheduler trackScheduler = guildMusicManager.scheduler;

        if(!Objects.equals(event.getMember(), event.getGuild().getSelfMember())){
            if(event.getChannel().getName().equalsIgnoreCase("nubzmusicplayer")){
                switch(event.getReaction().getReactionEmote().getEmoji()){
                    case "⏸":
                        if(audioManager.isConnected()) {
                            if (!(audioPlayer.getPlayingTrack() == null)) {
                                audioPlayer.setPaused(true);
                            }

                        }
                        break;

                    case "▶":
                        if(audioManager.isConnected()) {
                            if (audioPlayer.isPaused()) {
                                audioPlayer.setPaused(false);

                            }
                        }
                        break;

                    case "⏭":
                        if(trackScheduler.getQueue().isEmpty()){
                            audioPlayer.stopTrack();
                            audioManager.closeAudioConnection();
                        }else {
                            trackScheduler.nextTrack();
                        }
                        break;

                    case "⏹":
                        if(audioManager.isConnected()) {
                            if (!(audioPlayer.getPlayingTrack() == null)) {
                                audioPlayer.stopTrack();
                                trackScheduler.clearQueue();
                                audioManager.closeAudioConnection();
                                try {
                                    sendPatchReqCurrentSong("https://nubzapp-default-rtdb.asia-southeast1.firebasedatabase.app/" + event.getGuild().getName().toLowerCase() + ".json",
                                            "{\"currentsong\":\"" + "None" + "\"}");
                                }catch(Exception e){
                                        e.printStackTrace();
                                }

                            }
                        }
                        break;
                    default:

                        break;


                }
            }
        }
    }


    //Create Player
    private void createPlayer(Guild guild){
        EmbedBuilder player = new EmbedBuilder();
        player.setTitle(guild.getName()+"'s com.nubzbot.MusicPlayer");
        player.setImage(guild.getIconUrl());
        player.setColor(Color.RED);
        guild.getTextChannelsByName("NubzMusicPlayer",true).get(0).sendMessage(player.build()).queue((message -> {
            message.addReaction("⏸").queue();
            message.addReaction("▶").queue();
            message.addReaction("⏹").queue();
            message.addReaction("⏭").queue();

            //STORE EmbedMessageId in global variable
            this.musicPlayerEmbedId = message.getId();

        }));


    }

    //TO-FIREBASE
    public static void sendPatchReqCurrentSong(String url, String json) throws Exception {

        String charset = "UTF-8";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");

        //THIS SOLUTION WORKS FOR OUR APP USING FIREBASE AS FIREBASE SUPPORTS PATCH REQUEST LIKE THIS ,i.e. OVERRIDING HTTP-METHOD
        //HERE also a unknown extra entry is not made i.e.here directly inside the guildName then our fields are made/updated.

        try (OutputStream output = connection.getOutputStream()) {
            output.write(json.getBytes(charset));
            output.flush();
        }
        connection.disconnect();


    }


    //To Create A com.nubzbot.MusicPlayer
    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        super.onGuildVoiceJoin(event);
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
        AudioPlayer audioPlayer = guildMusicManager.player;
        AudioManager audioManager = event.getGuild().getAudioManager();
        TrackScheduler trackScheduler = guildMusicManager.scheduler;
        Member selfMember = event.getGuild().getSelfMember();
        GuildVoiceState guildVoiceState = selfMember.getVoiceState();
        mySelf= event.getJDA().getSelfUser();

        //CHECKS IF QUEUE IS EMPTY IF YES THEN CLOSES THE BOT CONNECTION FROM VC
        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if(trackScheduler.getQueue().isEmpty() && audioPlayer.getPlayingTrack()==null){
                            audioManager.closeAudioConnection();
                        }
                        //TO AVOID THREAD LEAKAGE
                        t.cancel();
                    }
                },
                2000  //in ms
        );


        assert guildVoiceState != null;
        if (guildVoiceState.inVoiceChannel()) {

          TextChannel tchannel;

            try {
                tchannel = event.getGuild().getTextChannelsByName("NubzMusicPlayer", true).get(0);
            } catch (IndexOutOfBoundsException n) {
                tchannel = event.getGuild().createTextChannel("NubzMusicPlayer").complete();

            }

            try {

                TextChannel finalTchannel = tchannel;

                //USED to check whether the new song matches the old song name
                AtomicReference<String> lastPlayedSong = new AtomicReference<>("NULL");

                //THREAD-POOL To Get Current playing music in tchannel
                repeatForCurrentSongEmbed = new javax.swing.Timer(1000, null);
                repeatForCurrentSongEmbed.addActionListener(e -> {
                    //TO CHECK WHEATHER THERE ARE MEMBERS IN VOICE CHANNEL LISTENING TO SONG
                    if(event.getChannelJoined().getMembers().size()==1){

                        audioManager.closeAudioConnection();//Disconnect From Channel

                    }

                    try {
                        //AS the getTitle method takes time to give value ,we using completeableFuture
                        CompletableFuture<String> songName = new CompletableFuture<>();
                        try {
                            songName.complete(audioPlayer.getPlayingTrack().getInfo().title);
                        }
                        catch(NullPointerException e2){
                            //DO NOTHING
                        }
                        sendPatchReqCurrentSong("https://nubzapp-default-rtdb.asia-southeast1.firebasedatabase.app/" + event.getGuild().getName().toLowerCase() + ".json",
                                "{\"currentsong\":\"" + songName.get() + "\"}");

                    }catch(Exception e1 ){
                        e1.printStackTrace();
                    }

                    //To Check ,if Song changes
                    if (!(lastPlayedSong.get().equalsIgnoreCase(audioPlayer.getPlayingTrack().getInfo().title))) {
                        lastPlayedSong.set(audioPlayer.getPlayingTrack().getInfo().title);
                        try {
                            EmbedBuilder musicPlayer = new EmbedBuilder();
                            musicPlayer.setTitle("Currently Playing/Last Played");
                            musicPlayer.addField("Song:", lastPlayedSong.get(), false);
                            musicPlayer.setColor(Color.CYAN);

                            //Changes Bots Name //CAUSING PROBLEM TO FEW USERS

//                            try {
//                                Objects.requireNonNull(event.getGuild().getMember(mySelf)).modifyNickname(lastPlayedSong.get().substring(0, 32)).complete();
//                            }catch(NullPointerException e){
//                                System.out.println("com.nubzbot.MusicPlayer ModifyName NullException");
//                            }

                            finalTchannel.sendMessage(musicPlayer.build()).queue();


                            //Deleting old msg if exists in that tchannel
                            if(!(finalTchannel.getLatestMessageId().equals(musicPlayerEmbedId))) {
                                finalTchannel.deleteMessageById(finalTchannel.getLatestMessageId()).queue();
                            }




                        } catch (NullPointerException n) {
                            //If nothing is playing
                            finalTchannel.sendMessage("Not Playing Anything!").queue();
                        }
                    }
                });

                repeatForCurrentSongEmbed.setRepeats(true);
                repeatForCurrentSongEmbed.setDelay(1000); //1 sec
                repeatForCurrentSongEmbed.start();



            } catch (NullPointerException e) {
                System.out.println("com.nubzbot.MusicPlayer:No Track is playing..");
            }
        }
    }


    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        super.onGuildVoiceLeave(event);
        AudioManager audioManager = event.getGuild().getAudioManager();
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
        AudioPlayer audioPlayer = guildMusicManager.player;
        TrackScheduler trackScheduler = new TrackScheduler(audioPlayer,event.getGuild());

        //WRITE CODE TO see if queue is zero or not if zero then set current song value to none
        if(trackScheduler.getQueue().isEmpty() && event.getMember().equals(event.getGuild().getSelfMember()) ){
            try {
                sendPatchReqCurrentSong("https://nubzapp-default-rtdb.asia-southeast1.firebasedatabase.app/" + event.getGuild().getName().toLowerCase() + ".json",
                        "{\"currentsong\":\"" + "None" + "\"}");
                try {

//                    repeatForCurrentSongEmbed.stop();

                }catch(NullPointerException e){
                    e.printStackTrace();
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(event.getChannelLeft().getMembers().size() == 1 && Objects.requireNonNull(event.getGuild().getSelfMember().getVoiceState()).inVoiceChannel()){
          try {

              try {

//                  repeatForCurrentSongEmbed.stop();

              }catch(NullPointerException e){
                  e.printStackTrace();
              }
              audioManager.closeAudioConnection();
          }catch(Exception e){
              e.printStackTrace();
          }


        }
        if(event.getMember()==event.getGuild().getSelfMember()){
            try {

//                repeatForCurrentSongEmbed.stop();
            }catch(NullPointerException e){
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onDisconnect(@NotNull DisconnectEvent event) {
        super.onDisconnect(event);
        try{

//            repeatForCurrentSongEmbed.stop();

        }catch(NullPointerException  e){
            e.printStackTrace();

        }

    }
}
