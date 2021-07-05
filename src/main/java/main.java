import Commands.HelpMe;
import Commands.ServerInfo;
import Commands.UserInfo;
import Events.*;
import MusicPlayer.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;


/*
DO NOT EDIT WITHOUT PRANAYS PERMISSION
Discord Private Bot For NooBs CommuniTy :3
v1.0
 */



public class main extends ListenerAdapter {

    public static void main(String[] args) throws Exception  {
        launchBot();



    }


    ///////////LAUNCH-BOT CONTAINS API CONNECTION AND INTEGRATION OF FEATURES//////
    private static void launchBot() throws Exception{
        //CONNECT TO DISCORD API
        JDA jdaBuilder = JDABuilder.createDefault("")
                .enableIntents(GatewayIntent.GUILD_MEMBERS,
                               GatewayIntent.GUILD_VOICE_STATES,
                               GatewayIntent.GUILD_MESSAGE_REACTIONS,GatewayIntent.GUILD_MESSAGES)
                .enableCache(CacheFlag.VOICE_STATE,CacheFlag.MEMBER_OVERRIDES)
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
        jdaBuilder.addEventListener(new PlayCommand());
        jdaBuilder.addEventListener(new QueueCommand());
        jdaBuilder.addEventListener(new MusicPlayer());
        jdaBuilder.addEventListener(new SeekPlayer());

        jdaBuilder.addEventListener(new Spam());
        jdaBuilder.addEventListener(new UserInfo());
        jdaBuilder.addEventListener(new Vote());

    }




}