package MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class SkipCommand extends ListenerAdapter {

    //v1.0

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] messageReceived = event.getMessage().getContentRaw().split(" ");
        if(messageReceived[0].equalsIgnoreCase("skip")){
            TextChannel channel = event.getChannel();
            PlayerManager playerManager = PlayerManager.getINSTANCE();
            GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
            TrackScheduler trackScheduler = guildMusicManager.scheduler;
            AudioPlayer audioPlayer =guildMusicManager.player;


            if (!(audioPlayer.getPlayingTrack()==null)){
                channel.sendMessage("Changing Gaana.").queue();
                trackScheduler.nextTrack();

            }else{
                channel.sendMessage("Gaana Nhi baj rha").queue();
            }

        }

    }
}
