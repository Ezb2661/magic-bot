package com.magicbot.frames;

import javax.swing.*;
import java.awt.*;

public abstract class BaseFrame extends JFrame
{
    private static Font baseFont = new Font( "Verdana", Font.PLAIN, 16 );

    /**
     *
     * @param title     The title of the window
     * @param width     The width of the window
     * @param height    The height of the window
     * @param visible   Whether or not the window should be visible upon creation
     */

    public BaseFrame( String title, int width, int height, boolean visible )
    {
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setTitle( title );
        this.setLayout( null );
        this.setBounds( getScreenSize( ).width / 2 - ( width / 2 ), getScreenSize( ).height / 2 - ( height / 2 ), width, height );
        this.setResizable( false );
        this.initComponents( );
        this.addComponents( );
        this.setVisible( visible );
        this.requestFocus( );
    }

    /*
    Initialize this frames components
     */
    public abstract void initComponents( );

    /*
    Add this frames components
     */
    public abstract void addComponents( );

    /**
     * Override default getInsets method to make more precise measurements
     * @return
     */
    @Override
    public Insets getInsets( )
    {
        return new Insets( 0, 0, 0, 0 );
    }

    /**
     * Returns the width of a string in pixels if it were rendered using BaseLabels default font
     * @param   str  The string to be measured
     * @return  The width of the string in pixels
     */

    public int getStringWidth( String str )
    {
        return this.getFontMetrics( baseFont ).stringWidth( str );
    }


    /**
     * Creates an error message box under the current frame
     * @param message   The message to be displayed
     */
    protected void errorMsg( String message )
    {
        JOptionPane.showMessageDialog( this, message, "MagicBot - Error", JOptionPane.ERROR_MESSAGE );
    }

    /**
     * Get monitor screen size
     * @return  The monitor screen size
     */
    public static Dimension getScreenSize( )
    {
        return Toolkit.getDefaultToolkit( ).getScreenSize( );
    }

    /**
     * Returns the font object used throughout all base components
     * @return  The font object used throughout all base components
     */
    public static Font getBaseFont( )
    {
        return baseFont;
    }



}
