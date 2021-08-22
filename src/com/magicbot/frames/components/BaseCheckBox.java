package com.magicbot.frames.components;

import javax.swing.*;

public class BaseCheckBox extends JCheckBox
{
    public BaseCheckBox( int x, int y, int width, int height, String text )
    {
        super( text );
        this.setBounds( x, y, width, height );
    }

}
