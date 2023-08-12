package com.nubzbot;
import com.nubzbot.Commands.HelpMe;
import com.nubzbot.Commands.ServerInfo;
import com.nubzbot.Commands.UserInfo;
import com.nubzbot.Events.*;
import com.nubzbot.MusicPlayer.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import io.github.cdimascio.dotenv.Dotenv;



//AQBIJeQQ_-Cl2LhdLUJBSBuVG0ilTqKEUpX_fs85o_qnkwQwIVp3w44CJ7q_AvFaewKcJSPYrkJrnVYzKjEjttnt1mYHUsw1v_3Zkp7Z26A7djdkDKrFF8Wn1s_m5aUWmKSbSfJA_-JG6l0oIubxEe2XTZiSRPOaZBFbFOrC7ieCCA

/*
DO NOT EDIT WITHOUT PRANAYS PERMISSION
Discord Private Bot For NooBs CommuniTy :3
v1.0
 */

//https://open.spotify.com/track/1nH2PkJL1XoUq8oE6tBZoU?si=33958a99e9ba439c


//public class main {


//    public static void main(String[] args) {
//        Scanner s = new Scanner(System.in);
//        System.out.println("Enter :");
//        String link = s.nextLine();
//
//        ClientLogin.clientCredentials_Async();
//        SpotifyApi spotifyApi = ClientLogin.getSpotifyApi();
//    GetTrackRequest getTrackRequest = spotifyApi.getTrack(id)
////          .market(CountryCode.SE)
//                .build();
//    getTrack_Async(getTrackRequest);
//
//    }
//    public static void getTrack_Async(GetTrackRequest getTrackRequest) {
//        try {
//            final CompletableFuture<Track> trackFuture = getTrackRequest.executeAsync();
//
//            // Thread free to do other tasks...
//
//            // Example Only. Never block in production code.
//            final Track track = trackFuture.join();
//            System.out.println("Name: " + track.getName() +" " +track.getArtists()[0].getName());
//        } catch (CompletionException e) {
//            System.out.println("Error: " + e.getCause().getMessage());
//        } catch (CancellationException e) {
//            System.out.println("Async operation cancelled.");
//        }
//    }
//
//}



















public class main extends ListenerAdapter {

    public static void main(String[] args) throws Exception {
//        Dotenv  dotenv =  Dotenv.load();
//        String discordAPIKey = dotenv.get("DISCORD_KEY");
//        String ytAPIKey = dotenv.get("YT_KEY");
//        System.out.println(System.getenv("DISCORD_KEY"));
//        System.out.println(System.getenv("YT_KEY"));
        String discordAPIKey = System.getenv("DISCORD_KEY");
        String ytAPIKey =System.getenv("YT_KEY");
        launchBot(discordAPIKey,ytAPIKey);


    }

    ///////////LAUNCH-BOT CONTAINS API CONNECTION AND INTEGRATION OF FEATURES//////
    private static void launchBot(String discordAPIKey,String ytAPIKey) throws Exception {
        //CONNECT TO DISCORD API
        JDA jdaBuilder = JDABuilder.createDefault(discordAPIKey)
                .enableIntents(GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGES,GatewayIntent.MESSAGE_CONTENT)
                .enableCache(CacheFlag.VOICE_STATE, CacheFlag.MEMBER_OVERRIDES)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)


                //TO ACCESS MEMBERS CACHE IN GUILD
                .build();


        //CODE HERE


        //ADDING EVENTS OF BOT
        jdaBuilder.addEventListener(new MyStatus());
        jdaBuilder.addEventListener(new HelpMe());
        jdaBuilder.addEventListener(new ServerInfo());
        jdaBuilder.addEventListener(new GreetUser());
//        jdaBuilder.addEventListener(new SelfDefense());
        jdaBuilder.addEventListener(new NubzAppReq());
        jdaBuilder.addEventListener(new PlayCommand(ytAPIKey));
        jdaBuilder.addEventListener(new QueueCommand());
        jdaBuilder.addEventListener(new MusicPlayer());
        jdaBuilder.addEventListener(new SeekPlayer());

        jdaBuilder.addEventListener(new Spam());
        jdaBuilder.addEventListener(new UserInfo());
        jdaBuilder.addEventListener(new Vote());

    }


}