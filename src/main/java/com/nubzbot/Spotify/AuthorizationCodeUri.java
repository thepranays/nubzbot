package com.nubzbot.Spotify;



import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AuthorizationCodeUri implements  Runnable {
    /* STEP 1
        com.nubzbot.Spotify Auth Process
        Getting Auth Code Using URI

        @PranayS 2022
     */

    private static final String clientId = "22f84464e69e4e2fb2c217f89e3ef581";
    private static final String clientSecret = "a13e37a4d0da434583d633d98cd8885c";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://github.com/thepranays/");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
//          .scope("user-read-birthdate,user-read-email")
//          .show_dialog(true)
            .build();

    @Override
    public void run() {
        authorizationCodeUri_Async();
    }

    public static void authorizationCodeUri_Async() {
        try {
            final CompletableFuture<URI> uriFuture = authorizationCodeUriRequest.executeAsync();

            // Thread free to do other tasks

            final URI uri = uriFuture.join();
            try {
                URLConnection con = new URL(uri.toString()).openConnection();
                System.out.println("orignal url: " + con.getURL());
                con.connect();
                System.out.println("connected url: " + con.getURL());
                InputStream is = con.getInputStream();
                System.out.println("redirected url: " + con.getURL());
                is.close();

            }catch(IOException e){
                System.out.println("IOEXCEPTION CAUGHT");
            }
            System.out.println("URI: " + uri.toString());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }




}
