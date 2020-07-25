package MusicPlayer;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

//v1.0

public class PlayCommand extends ListenerAdapter {
    private final YouTube youTube;

    //CONSTRUCTOR USED TO MAKE YOUTUBE BUILDER
    public PlayCommand(){
        //Connect To YOUTUBE-API
        YouTube youTubeTemp = null;
        try{
            youTubeTemp = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),null)
                    .setApplicationName("NubZBot")
                    .build();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.youTube = youTubeTemp;
    }



    private void joinChannel(GuildMessageReceivedEvent event,String inputReceived){
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

                //CHECK WHEATHER THE INPUT MESSAGE IS URL OR NORMAL SEARCH
                play(inputReceived,playerManager,channel,event);
            }else{
                channel.sendMessage("Join kr rha bhai <3").queue();

                //CHECK WHEATHER THE INPUT MESSAGE IS URL OR NORMAL SEARCH
                play(inputReceived,playerManager,channel,event);
            }
            return;

        }else{
            //CHECK WHEATHER THE INPUT MESSAGE IS URL OR NORMAL SEARCH
            play(inputReceived,playerManager,channel,event);
        }


        //Check whether user  is in channel or not
        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Bhai Channel Join Karle Pehle..").queue();
            return;
        }


        //Check for permission
        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            channel.sendMessageFormat("Meri awkaad nhi hai join karne ki %s", voiceChannel).queue();

        }


    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String inputRawString = e.getMessage().getContentRaw();
        String[] inputFound = inputRawString.split(" ");
        if (inputFound[0].equalsIgnoreCase("gaana")) {
            joinChannel(e,inputRawString.substring(5));
            PlayerManager playerManager = PlayerManager.getINSTANCE();
            playerManager.getGuildMusicManger(e.getGuild()).player.setVolume(10);
            e.getChannel().sendMessage("Playing Gaana :D").queue();


        }
    }

    //CHECK WHEATHER INPUT IS URL OR NOT
    public boolean isUrl(String input){
       try{
           new URL(input);
           return true;
       }catch(MalformedURLException e){
           return false;
       }
    }

    /*
        If URL found - Play through url using playermanager
        else:Search on Youtube Using YT-V3 API And get a Video id and Make A URL STRING
        Then,Play
     */
    public void play(String inputReceived,PlayerManager playerManager,TextChannel channel,GuildMessageReceivedEvent event){
        //CHECK WHEATHER THE INPUT MESSAGE IS URL OR NORMAL SEARCH
        if(!(isUrl(inputReceived))){
            String ytSearched = youtubeAPISearch(inputReceived);
            if(ytSearched ==null){
                channel.sendMessage("Gaana nhi mila !").queue();
                return;
            }

            inputReceived = ytSearched;
            playerManager.loadAndPlay(channel,inputReceived);
        }else {
            playerManager.loadAndPlay(event.getChannel(), inputReceived);
        }
    }

   //YOUTUBE API SEARCH AND RETURN VIDEOID
    @Nullable
    public String youtubeAPISearch(String input){
        try{
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey("YOUTUBEAPIV3TOKEN")
                    .execute()
                    .getItems();
            if(!results.isEmpty()){
                String videoId =  results.get(0).getId().getVideoId();
                return "https://www.youtube.com/watch?v=" + videoId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
