import Commands.HelpMe;
import Commands.ServerInfo;
import Commands.UserInfo;
import Events.MyStatus;
import Events.Spam;
import Events.Suicide;
import Events.Vote;
import MusicPlayer.PlayCommand;
import MusicPlayer.QueueCommand;
import MusicPlayer.SkipCommand;
import MusicPlayer.StopCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
DO NOT EDIT BY PRANAYS PERMISSION
Discord Private Bot For NooBs CommuniTy :3
v1.0
 */



public class main extends ListenerAdapter {

    public static void main(String[] args)  {

        ///////////UI FOR BOT LAUNCHING AND ETC..///////

        JFrame frame=new JFrame();
        // Creating Button
        JButton launchButton = new JButton("LAUNCH BOT!");
        JTextPane textPane = new JTextPane();

        //Setting Components
        launchButton.setBounds(60,50,150, 50);
        textPane.setBounds(90,100,90,50);

        //Adding Components onto the frame
        frame.add(launchButton);
        frame.add(textPane);

        // Setting Frame
        frame.setSize(300,200);
        frame.setTitle("NuBzLauncher");
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //OnButtonClicked
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                try {
                    launchBot();
                    textPane.setText("Bot Running..");
                }catch (Exception unexpectedException){
                    textPane.setText("Unexpected Error while connecting");
                }
            }
        });
    }


    ///////////LAUNCH-BOT CONTAINS API CONNECTION AND INTEGRATION OF FEATURES//////
    private static void launchBot() throws Exception{
        //CONNECT TO DISCORD API
        JDA jdaBuilder = JDABuilder.createDefault("DISCORDBOTTOKEN").build();




        //CODE HERE






        //INTEGRATING EVENTS OF BOT
        jdaBuilder.addEventListener(new MyStatus());
        jdaBuilder.addEventListener(new HelpMe());
        jdaBuilder.addEventListener(new Suicide());
        jdaBuilder.addEventListener(new ServerInfo());


        jdaBuilder.addEventListener(new PlayCommand());
        jdaBuilder.addEventListener(new StopCommand());
        jdaBuilder.addEventListener(new SkipCommand());
        jdaBuilder.addEventListener(new QueueCommand());

        jdaBuilder.addEventListener(new Spam());
        jdaBuilder.addEventListener(new UserInfo());
        jdaBuilder.addEventListener(new Vote());
    }




}