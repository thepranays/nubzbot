package MusicPlayer;



import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import net.dv8tion.jda.api.entities.TextChannel;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand extends ListenerAdapter {

    //v1.0

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] messageReceived = event.getMessage().getContentRaw().split(" ");
        if(messageReceived[0].equalsIgnoreCase("chuphoja")){
            TextChannel channel = event.getChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();
            PlayerManager playerManager = PlayerManager.getINSTANCE();
            GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
            AudioPlayer audioPlayer =guildMusicManager.player;
            TrackScheduler trackScheduler = guildMusicManager.scheduler;

            if (audioManager.isConnected()) {
                channel.sendMessage("Chup Ho Rha!").queue();
                audioPlayer.stopTrack();    //Stops the track
                trackScheduler.clearQueue();    //Clears The Queue
                audioManager.closeAudioConnection();        //Disconnect From Channel


            }

        }

    }

}
