package MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class PauseCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String inputReceived = e.getMessage().getContentRaw();
        String[] input = inputReceived.split(" ");
        if(input[0].equals("rukja")){
            TextChannel channel = e.getChannel();
            PlayerManager playerManager = PlayerManager.getINSTANCE();
            GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(e.getGuild());
            AudioPlayer audioPlayer =guildMusicManager.player;

            if (!(audioPlayer.getPlayingTrack()==null)){
                channel.sendMessage("Ruk rha..").queue();
                audioPlayer.setPaused(true);    //Pauses the music in musicplayer



            }else{
                channel.sendMessage("Gaana Nhi baj rha").queue();      /* Issue:also displays This when user not joined the channel */
            }


        }else if(input[0].equals("baja")){
            TextChannel channel = e.getChannel();
            PlayerManager playerManager = PlayerManager.getINSTANCE();
            GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(e.getGuild());
            AudioPlayer audioPlayer =guildMusicManager.player;
            if (audioPlayer.isPaused()){          //Checks wheather the music was paused
                channel.sendMessage("Baja rha Mota bhai :D..").queue();
                audioPlayer.setPaused(false);    //Resumes the music in the player

            }else{          //if already running a song
                channel.sendMessage("Pehle se baj rha..").queue();  /* Issue:also displays This when user not joined the channel */

            }

        }


    }
}
