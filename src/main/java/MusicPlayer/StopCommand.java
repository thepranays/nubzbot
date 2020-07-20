package MusicPlayer;



import net.dv8tion.jda.api.entities.TextChannel;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand extends ListenerAdapter {

    //v1.0

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] messageReceived = event.getMessage().getContentRaw().split(" ");
        if(messageReceived[0].equalsIgnoreCase("chuphoja")){
            TextChannel channel = event.getChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();
            if (audioManager.isConnected()) {
                channel.sendMessage("Chup Ho Rha!").queue();
                audioManager.closeAudioConnection();

            }

        }

    }

}
