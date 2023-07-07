package com.nubzbot.Spotify;



import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public  class AuthorizationCode implements Runnable {
    private static final String clientId = "22f84464e69e4e2fb2c217f89e3ef581";
    private static final String clientSecret = "a13e37a4d0da434583d633d98cd8885c";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://github.com/thepranays/");
    private static final String code = "AQDM_ca5ZGYRPaW6OLGMByx6Pr9aM7QdN8rVHv-Wb22zshHA1yrtYat_MGHljUJvTbb-04UaKqb8hHtAeM9LrycneG18fnk7qeqH522H3kuOj6rIQwjMhCedh7ajUBum6io1eP7GxGavGGVo1nKWDqLgLjlbaZtFWY4FczESg5dm1A";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
            .build();

    private String refreshToken;

    private Timer timer;



    @Override
  public void run() {
        authorizationCode_Async();
       startTimer();


  }
    private void startTimer() {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                  authorizationCodeRefresh_Async();
            }
        }, 0,3000 ); // delay
        }


        public  void authorizationCodeRefresh_Async() {
          final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(this.refreshToken)
                .build();
          final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();
        try {
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRefreshRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            //Again set new refreshtoken for next go
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            this.refreshToken = authorizationCodeCredentials.getRefreshToken();

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public  void  authorizationCode_Async() {
        try {
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());


            this.refreshToken= authorizationCodeCredentials.getRefreshToken();

            System.out.println("token will expire in:"+authorizationCodeCredentials.getExpiresIn());


        } catch (CompletionException e) {
            System.out.println("Completetion error");

        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");

        }
    }


}
