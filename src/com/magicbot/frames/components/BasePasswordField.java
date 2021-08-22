package com.magicbot.frames.components;

import com.magicbot.frames.BaseFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BasePasswordField extends JPasswordField
{
    public BasePasswordField( int x, int y, int width, int height )
    {
        super( );
        this.setBounds( x, y, width, height );
        this.setFont( BaseFrame.getBaseFont( ) );
    }

    public String getPasswordString( )
    {
        return String.valueOf( this.getPassword( ) );
    }

}

