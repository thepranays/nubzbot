package MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;


//Completed Test v1.0


public class MusicPlayer extends ListenerAdapter {
    private String musicPlayerEmbedId;
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private final Map<String, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<>();

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
                        if(audioManager.isConnected()) {
                            if (!(audioPlayer.getPlayingTrack() == null)) {
                                trackScheduler.nextTrack();

                                break;
                            }
                        }

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
                        if(audioManager.isConnected()) {
                            if (!(audioPlayer.getPlayingTrack() == null)) {
                                trackScheduler.nextTrack();

                                break;
                            }
                        }

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


    //Create Player
    private void createPlayer(Guild guild){
        EmbedBuilder player = new EmbedBuilder();
        player.setTitle("MusicPlayer");
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





    //To Create A MusicPlayer
    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        super.onGuildVoiceJoin(event);
        Member selfMember = event.getGuild().getSelfMember();
        GuildVoiceState guildVoiceState = selfMember.getVoiceState();
        assert guildVoiceState != null;
        if (guildVoiceState.inVoiceChannel()) {
            PlayerManager playerManager = PlayerManager.getINSTANCE();
            GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
            AudioPlayer audioPlayer = guildMusicManager.player;
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
                 ScheduledFuture<?> scheduledFuture = executor.scheduleWithFixedDelay(() -> {
                     //To Check ,if Song changes
                    if (!lastPlayedSong.get().equalsIgnoreCase(audioPlayer.getPlayingTrack().getInfo().title)) {
                        lastPlayedSong.set(audioPlayer.getPlayingTrack().getInfo().title);
                        try {
                            EmbedBuilder musicPlayer = new EmbedBuilder();
                            musicPlayer.setTitle("Currently Playing/Last Played");
                            musicPlayer.addField("Song:", lastPlayedSong.get(), false);
                            musicPlayer.setColor(Color.CYAN);
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
                }, 1, 1, TimeUnit.SECONDS);

                 scheduledFutureMap.put(event.getChannelJoined().getId(), scheduledFuture);

            } catch (NullPointerException e) {
                System.out.println("MusicPlayer:No Track is playing..");
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        super.onGuildVoiceLeave(event);

        if((event.getMember().equals(event.getGuild().getSelfMember()))) {
            scheduledFutureMap.get(event.getChannelLeft().getId()).cancel(true);
        }
    }



}
