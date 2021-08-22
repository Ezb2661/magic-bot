package com.magicbot.dankmemer;

import com.magicbot.MagicBot;
import com.magicbot.discord.DiscordClient;

public class DankMemerThread extends Thread implements Runnable
{
    private int begTimer = 0;
    private int fishTimer = 0;
    private int huntTimer = 0;
    private int searchTimer = 0;
    private int depositTimer = 0;

    private DiscordClient client;

    public DankMemerThread( DiscordClient client )
    {
        super( );
        this.client = client;
    }

    int clampTimer( int t )
    {
        if( t < 0 )
            return 0;
        else
            return t;
    }

    @Override
    public void run( )
    {
        while( true )
        {
            try
            {
                Thread.sleep( 1000 );
                begTimer = clampTimer( --begTimer );
                fishTimer = clampTimer( --fishTimer );
                huntTimer = clampTimer( --huntTimer );
                searchTimer = clampTimer( --searchTimer );
                depositTimer = clampTimer( --depositTimer );
            }
            catch (InterruptedException e)
            {
                e.printStackTrace( );
            }

            if( begTimer <= 0 && MagicBot.dankMemerFrame.isAutoBegEnabled( ) )
            {
                this.client.sendMessage( MagicBot.dankMemerFrame.getChannelID( ), "pls beg" );
                begTimer = 46;
            }

            if( fishTimer <= 0 && MagicBot.dankMemerFrame.isAutoFishEnabled( ) )
            {
                fishTimer = 41;
                this.client.sendMessage( MagicBot.dankMemerFrame.getChannelID( ), "pls fish" );
            }

            if( huntTimer <= 0 && MagicBot.dankMemerFrame.isAutoHuntEnabled( ) )
            {
                huntTimer = 41;
                this.client.sendMessage( MagicBot.dankMemerFrame.getChannelID( ), "pls hunt" );
            }

            if( searchTimer <= 0 && MagicBot.dankMemerFrame.isAutoSearchEnabled( ) )
            {
                searchTimer = 36;
                this.client.sendMessage( MagicBot.dankMemerFrame.getChannelID( ), "pls search" );
            }

            if( depositTimer <= 0 && MagicBot.dankMemerFrame.isAutoDepositEnabled( ) )
            {
                depositTimer = 60;
                this.client.sendMessage( MagicBot.dankMemerFrame.getChannelID( ), "pls dep all" );
            }

        }
    }
}
