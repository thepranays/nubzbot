package MusicPlayer;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

//v1.0

public class PlayCommand extends ListenerAdapter {
    private final YouTube youTube;
    private TextChannel tchannel;
    private static ClientCredentialsRequest clientCredentialsRequest;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest;




    //CONSTRUCTOR USED TO MAKE YOUTUBE API BUILDER
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
//                channel.sendMessage("Pehle se tumhare sath hu!").queue();


                //CHECK WHEATHER THE INPUT MESSAGE IS URL OR NORMAL SEARCH
                play(inputReceived,playerManager,channel,event);
            }else{
//                channel.sendMessage("Join kr rha bhai <3").queue();

                //CHECK WHEATHER THE INPUT MESSAGE IS URL OR NORMAL SEARCH
                play(inputReceived,playerManager,channel,event);
            }
            return;

        }

        else{
            //CHECK WHEATHER THE INPUT MESSAGE IS URL OR NORMAL SEARCH
            play(inputReceived,playerManager,channel,event);
        }


        //Check whether user  is in channel or not
        if (!(memberVoiceState.inVoiceChannel())) {
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
        Member selfMember = e.getGuild().getSelfMember();

        //Youtube
        try {
            if(!(Objects.equals(e.getMessage().getMember(), selfMember))) {
                if (e.getChannel().getName().equalsIgnoreCase("NubzMusicRequest")) {
                    joinChannel(e, inputRawString);
                    PlayerManager playerManager = PlayerManager.getINSTANCE();
                    playerManager.getGuildMusicManger(e.getGuild()).player.setVolume(100);

//                e.getChannel().sendMessage("Playing Gaana :D").queue();


                }
            }
        }catch(NullPointerException NullPointer){
          e.getChannel().sendMessage("Type 'nubzsetup' first").queue();
        }

        //Spotify Test
//        try {
//            if(!(Objects.equals(e.getMessage().getMember(), selfMember))) {
//                if (e.getChannel().getName().equalsIgnoreCase("NubzMusicSpotifyRequest")) {
//                    for(PlaylistTrack playlistTrack:spotifyGetSongNameByPlaylist(inputRawString)) {
//                        joinChannel(e, playlistTrack.getTrack().getName());
//                    }
//                    PlayerManager playerManager = PlayerManager.getINSTANCE();
//                    playerManager.getGuildMusicManger(e.getGuild()).player.setVolume(100);
//
//               e.getChannel().sendMessage("Playing Gaana :D").queue();
//
//
//                }
//            }
//        }catch(NullPointerException NullPointer){
//            e.getChannel().sendMessage("Type 'nubzsetup' first").queue();
//        }

    }

    //CHECK WHEATHER INPUT IS URL OR NOT
    private boolean isUrl(String input){
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

    private void play(String inputReceived,PlayerManager playerManager,TextChannel channel,GuildMessageReceivedEvent event){
        //CHECK WHEATHER THE INPUT MESSAGE IS URL OR NORMAL SEARCH
        if(!(isUrl(inputReceived))){
            String ytSearched = youtubeAPISearch(inputReceived);
            if(ytSearched ==null){
                playerManager.loadAndPlay(event.getChannel(), inputReceived);
                return;
            }

            inputReceived = ytSearched;

            //TEST
            /*channel.sendMessage(inputReceived).queue();  //RECEVING LINK*/

            playerManager.loadAndPlay(channel, inputReceived);

        }else {
            playerManager.loadAndPlay(event.getChannel(),inputReceived);

        }
    }




    private List<PlaylistTrack> spotifyGetSongNameByPlaylist(String uri){
            SpotifyRequests spotifyRequests = new SpotifyRequests( convertIntoPlaylistID(uri));
            return spotifyRequests.getPlaylist();
    }

    //AS spotify requires 22 Character ID which contains in URI of playlist
    //It starts from 34+ character Ends after 34+22 character
    private String convertIntoPlaylistID(String uri){
        String[] uriSplit = uri.split("");
        return uri.substring(34,56);


    }




   //YOUTUBE API SEARCH AND RETURN VIDEOID
    @Nullable
    private String youtubeAPISearch(String input){
        try{
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey("")
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


