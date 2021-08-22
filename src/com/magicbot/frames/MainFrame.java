package com.magicbot.frames;

import com.magicbot.HttpClientWrapper;

public class MainFrame extends BaseFrame
{
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 600;

    private HttpClientWrapper httpWrapper;

    public MainFrame( HttpClientWrapper httpWrapper )
    {
        super( "MagicBot", FRAME_WIDTH, FRAME_HEIGHT, false );
        this.httpWrapper = httpWrapper;
    }

    @Override
    public void initComponents( )
    {

    }

    @Override
    public void addComponents( )
    {

    }
}
