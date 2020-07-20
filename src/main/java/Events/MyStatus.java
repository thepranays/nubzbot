package Events;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class MyStatus extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);
        Activity activity = Activity.playing("Type 'helpme'");
        event.getJDA().getPresence().setActivity(activity);
    }
}
