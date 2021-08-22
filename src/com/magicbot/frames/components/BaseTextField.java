package com.magicbot.frames.components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class BaseTextField extends JTextField
{
    private static Font baseFieldFont = new Font( "Verdana", Font.PLAIN, 13 );

    /**
     * Creates a new BaseTextField element
     * @param x       X coordinate of the text field
     * @param y       Y coordinate of the text field
     * @param width   Width of the text field
     * @param height  Height of the text field
     */

    public BaseTextField( int x, int y, int width, int height )
    {
        super( );
        this.setFont( getBaseFieldFont( ) );
        this.setLayout( null );
        this.setForeground( Color.BLACK );
        this.setBounds( x, y, width, height );
    }

    /**
     * The base field font used throughout BaseTextField elements
     * @return The font object that to be used by text fields
     */
    public static Font getBaseFieldFont( )
    {
        return baseFieldFont;
    }
}