package com.magicbot;

import com.magicbot.discord.DiscordClient;
import com.magicbot.frames.DankMemerFrame;

public class DankMemerThread extends Thread implements Runnable
{
    private int begTimer = 0;
    private int fishTimer = 0;
    private int huntTimer = 0;

    private DiscordClient client;

    public DankMemerThread( DiscordClient client )
    {
        super( );
        this.client = client;
    }

    @Override
    public void run( )
    {
        while( true )
        {
            try
            {
                Thread.sleep( 1000 );
                begTimer--;
                fishTimer--;
                huntTimer--;
            }
            catch (InterruptedException e)
            {
                begTimer--;
                fishTimer--;
                huntTimer--;
            }

            if( begTimer <= 0 && MagicBot.dankMemerFrame.isAutoBegEnabled( ) )
            {
                this.client.sendMessage( MagicBot.dankMemerFrame.getChannelID( ), "pls beg" );
                begTimer = 46;
            }

            if( fishTimer <= 0 && MagicBot.dankMemerFrame.isAutoFishEnabled( ) )
            {
                //Fish
                fishTimer = 41;
                this.client.sendMessage( MagicBot.dankMemerFrame.getChannelID( ), "pls fish" );
            }

            if( huntTimer <= 0 && MagicBot.dankMemerFrame.isAutoHuntEnabled( ) )
            {
                huntTimer = 41;
                this.client.sendMessage( MagicBot.dankMemerFrame.getChannelID( ), "pls hunt" );
            }

        }
    }
}
