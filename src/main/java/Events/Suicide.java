package Events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Suicide extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] messageReceived = e.getMessage().getContentRaw().split(" ");

        if(messageReceived[0].equalsIgnoreCase("Aathmahatya")){
            e.getChannel().sendMessage("KOTA ZINDABAAD!").queue();
            e.getGuild().leave().queue();
        }

    }
}
