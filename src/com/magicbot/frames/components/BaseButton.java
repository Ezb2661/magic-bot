package com.magicbot.frames.components;

import javax.swing.*;
import java.awt.event.ActionListener;

public class BaseButton extends JButton
{
    public BaseButton( String text, int x, int y, int width, int height, ActionListener actionListener )
    {
        super( text );
        this.setBounds( x, y, width, height );
        this.addActionListener( actionListener );
    }
}
