package com.magicbot.frames.components;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel
{
    /**
     * Creates a new BasePanel Object
     * @param title     The title of the border for the panel
     * @param x         X Coordinate of the panel
     * @param y         Y Coordinate of the panel
     * @param width     Width of the panel
     * @param height    Height of the panel
     */
    public BasePanel( String title, int x, int y, int width, int height )
    {
        super( );
        this.setBorder( BorderFactory.createTitledBorder( title ) );
        this.setLayout( null );
        this.setBounds( x, y, width, height );
    }


    @Override
    public Insets getInsets( )
    {
        return new Insets( 0, 0, 0, 0 );
    }
}
