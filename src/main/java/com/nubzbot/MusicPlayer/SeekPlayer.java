package com.nubzbot.MusicPlayer;


import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class SeekPlayer extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
        AudioPlayer audioPlayer = guildMusicManager.player;

        String input = event.getMessage().getContentRaw();
        if (event.getChannel().getName().equalsIgnoreCase("nubzmusicplayer")) {
            if (event.getMember() != event.getGuild().getSelfMember()) {
                if ((inputToms(input) * 1000) <= audioPlayer.getPlayingTrack().getDuration()) {
                    audioPlayer.getPlayingTrack().setPosition((inputToms(input)) * 1000);
                    event.getMessage().delete().queue();
                } else {
                    event.getMember().getUser().openPrivateChannel().queue((channel) ->
                    {
                        channel.sendMessage("Seek Request Failed:Incorrect Timestamp Mentioned").queue();
                    });
                    event.getMessage().delete().queue();


                }
            }
        }
    }


    public long inputToms(String input){
        String[] inputToStr = input.split(":");
        long min = (Long.parseLong(inputToStr[0]))*60;
        long seconds = Long.parseLong(inputToStr[1]);
        return min+seconds;

    }




}
