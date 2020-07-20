package MusicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand extends ListenerAdapter {

    //v1.0

    private void joinChannel(GuildMessageReceivedEvent event){
        PlayerManager playerManager = PlayerManager.getINSTANCE();
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManger(event.getGuild());
        AudioPlayer audioPlayer =guildMusicManager.player;
        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = event.getGuild().getSelfMember();
        audioManager.openAudioConnection(voiceChannel);


        if (audioManager.isConnected()) {
            //TO CHECK WHEATHER SONG IS PLAYING OR NOT SO TO GET KNOW CONNECTED + PLAYING STATUS
            //SO THAT OUT MESSAGE DNT REPEAT
            if(!(audioPlayer.getPlayingTrack()==null)) {
                channel.sendMessage("Pehle se tumhare sath hu!").queue();
            }else{
                channel.sendMessage("Join kr rha bhai <3").queue();
            }
            return;
        }



        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Bhai Channel Join Karle Pehle..").queue();
            return;
        }



        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            channel.sendMessageFormat("Meri awkaad nhi hai join karne ki %s", voiceChannel).queue();
            return;
        }


    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] inputFound = e.getMessage().getContentRaw().split(" ");
        if (inputFound[0].equalsIgnoreCase("gaana")) {
            joinChannel(e);
            PlayerManager playerManager = PlayerManager.getINSTANCE();
            playerManager.loadAndPlay(e.getChannel(),inputFound[1]);
            playerManager.getGuildMusicManger(e.getGuild()).player.setVolume(10);
            e.getChannel().sendMessage("Playing Gaana :D").queue();


        }

    }
}
