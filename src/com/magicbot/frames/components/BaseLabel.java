package com.magicbot.frames.components;

import com.magicbot.frames.BaseFrame;

import javax.swing.*;

public class BaseLabel extends JLabel
{
    public BaseLabel(String text, int x, int y, int width, int height )
    {
        super( text );
        this.setFont( BaseFrame.getBaseFont( ) );
        this.setBounds( x, y, width, height );
    }

}
