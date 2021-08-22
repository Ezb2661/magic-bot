package com.magicbot;

import com.magicbot.dankmemer.DankMemerFrame;
import com.magicbot.frames.BotSelectFrame;

import javax.swing.*;

public class MagicBot
{
    public static HttpClientWrapper httpWrapper = new HttpClientWrapper( );

    public static BotSelectFrame botSelectFrame;
    public static DankMemerFrame dankMemerFrame;

    public static void main( String[] args )
    {
        try
        {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName( ) );
        }
        catch( Exception ex )
        {
            ex.printStackTrace( );
        }

        botSelectFrame = new BotSelectFrame( );
    }

    public static String getHwid( )
    {
        return "-1";
    }
}
