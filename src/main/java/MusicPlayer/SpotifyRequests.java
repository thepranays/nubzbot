package MusicPlayer;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyApiThreading;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;


import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SpotifyRequests extends ListenerAdapter {
    private static  SpotifyApi spotifyApi;
    private GetPlaylistRequest getPlaylistRequest;
    private static ClientCredentialsRequest clientCredentialsRequest;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest1;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest2;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest3;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest4;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest5;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest6;
    private String playlistID;

    public SpotifyRequests(String playlistID){
    //SPOTIFY API AUTHENTICATION
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId("")
                .setClientSecret("")
                .build();

        clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        //SYNCHRONOUS
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        //Creating Request objects to get PlaylistItems and Playlist From spotify
       this.playlistID= playlistID;


       getPlaylistRequest = spotifyApi.getPlaylist(playlistID)
                .build();

         getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistID)
                .build();

         getPlaylistsItemsRequest1 = spotifyApi
                 .getPlaylistsItems(playlistID)
                 .offset(100)

                 .build();

        getPlaylistsItemsRequest2 = spotifyApi
                .getPlaylistsItems(playlistID)
                .offset(200)

                .build();


        getPlaylistsItemsRequest3 = spotifyApi
                .getPlaylistsItems(playlistID)
                .offset(300)

                .build();

        getPlaylistsItemsRequest4 = spotifyApi
                .getPlaylistsItems(playlistID)
                .offset(400)

                .build();

        getPlaylistsItemsRequest5 = spotifyApi
                .getPlaylistsItems(playlistID)
                .offset(500)

                .build();

        getPlaylistsItemsRequest6 = spotifyApi
                .getPlaylistsItems(playlistID)
                .offset(600)

                .build();

    }

    //TO ACCESS PLAYLIST AND GET SONGS

    public List<PlaylistTrack> getPlaylist(){
        List<PlaylistTrack> playlistTrackList = new ArrayList<PlaylistTrack>();
            try {


                final CompletableFuture<Playlist> playlistFuture = getPlaylistRequest.executeAsync();

                // Thread free to do other tasks... AS ASync

//                //Test
//                final Playlist playlist = playlistFuture.join();
//                System.out.println("Name: " + playlist.getName());

                //CompleteableFuturePage
                final CompletableFuture<Paging<PlaylistTrack>> pagingFuture = getPlaylistsItemsRequest.executeAsync();
                final CompletableFuture<Paging<PlaylistTrack>> pagingFuture1 = getPlaylistsItemsRequest1.executeAsync();
                final CompletableFuture<Paging<PlaylistTrack>> pagingFuture2 = getPlaylistsItemsRequest2.executeAsync();
                final CompletableFuture<Paging<PlaylistTrack>> pagingFuture3 = getPlaylistsItemsRequest3.executeAsync();
                final CompletableFuture<Paging<PlaylistTrack>> pagingFuture4 = getPlaylistsItemsRequest4.executeAsync();
                final CompletableFuture<Paging<PlaylistTrack>> pagingFuture5 = getPlaylistsItemsRequest5.executeAsync();
                final CompletableFuture<Paging<PlaylistTrack>> pagingFuture6 = getPlaylistsItemsRequest6.executeAsync();


                //PagingAfterFutureIsFormed
                final Paging<PlaylistTrack> playlistTrackPaging = pagingFuture.join();
                final Paging<PlaylistTrack> playlistTrackPaging1 = pagingFuture1.join();
                final Paging<PlaylistTrack> playlistTrackPaging2 = pagingFuture2.join();
                final Paging<PlaylistTrack> playlistTrackPaging3 = pagingFuture3.join();
                final Paging<PlaylistTrack> playlistTrackPaging4 = pagingFuture4.join();
                final Paging<PlaylistTrack> playlistTrackPaging5 = pagingFuture5.join();
                final Paging<PlaylistTrack> playlistTrackPaging6 = pagingFuture6.join();


                //Adding everything to one list
                playlistTrackList = Arrays.stream(playlistTrackPaging.getItems()).collect(Collectors.toList());
                playlistTrackList.addAll(Arrays.stream(playlistTrackPaging1.getItems()).collect(Collectors.toList()));
                playlistTrackList.addAll(Arrays.stream(playlistTrackPaging2.getItems()).collect(Collectors.toList()));
                playlistTrackList.addAll(Arrays.stream(playlistTrackPaging3.getItems()).collect(Collectors.toList()));
                playlistTrackList.addAll(Arrays.stream(playlistTrackPaging4.getItems()).collect(Collectors.toList()));
                playlistTrackList.addAll(Arrays.stream(playlistTrackPaging5.getItems()).collect(Collectors.toList()));
                playlistTrackList.addAll(Arrays.stream(playlistTrackPaging6.getItems()).collect(Collectors.toList()));







            } catch (CompletionException e) {
                System.out.println("Error: " + e.getCause().getMessage());
            } catch (CancellationException e) {
                System.out.println("Async operation cancelled.");
            }




//
//            System.out.println("Done SpotifyReq");
            return playlistTrackList;
        }
    }

