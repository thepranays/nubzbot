package Commands;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

/* TO SEND INFO ABOUT COMMANDS IF REQUESTED  */

public class HelpMe extends ListenerAdapter {

    //v1.0

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] inputFound = e.getMessage().getContentRaw().split(" ");
        if(inputFound[0].equalsIgnoreCase("helpme")){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Helping You");
            embedBuilder.setDescription("Commands,Info,How To Use.");
            //SERVER_INFO COMMAND
            embedBuilder.addField("✍serverinfo","Display Server Information",false);
            //SPAM COMMAND
            embedBuilder.addField("⚡spam","Use To Spam Someone In PM \nspam @mention {message}",false);
            //MUSIC COMMAND
            embedBuilder.addField("\uD83C\uDFB5Music","Use To Play Music\ngaana {link}\nskip:To Skip Song\nchuphoja:To Stop Song",false);
            //VOTE COMMAND
            embedBuilder.addField("\uD83D\uDDF3Vote","Use To Start A Vote\nmatdan {topic of vote}",false);





            embedBuilder.setColor(Color.CYAN);
            e.getChannel().sendMessage(embedBuilder.build()).queue();

        }

    }
}
