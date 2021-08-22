package com.magicbot.frames.components;

import javax.swing.*;
import java.awt.*;

public class BaseLabeledPasswordField extends JPanel
{
    /*
    Define text box types, password, and plain text
     */
    public static final int PLAIN = 0;
    public static final int PASSWORD = 1;

    private BasePasswordField passwordFieldComponent;
    private BaseLabel labelComponent;

    /**
     * Create a new labeled text box for user input
     * @param text          The text of the label
     * @param stringWidth   The width of the string, returned by BaseFrame.getStringWidth
     * @param x             The x coordinate of the element
     * @param y             The y coordinate of the element
     */
    public BaseLabeledPasswordField(String text, int stringWidth, int x, int y )
    {
        this.setBorder( null );
        this.setLayout( null );
        this.setBounds( x, y, ( 200 > stringWidth ? 200 : stringWidth ), 60 );

        labelComponent = new BaseLabel( text, 0, 0, stringWidth, 30 );
        passwordFieldComponent = new BasePasswordField( 0, 30, 200, 30 );

        this.add( labelComponent );
        this.add( passwordFieldComponent );
    }


    @Override
    public Insets getInsets( )
    {
        return new Insets( 0, 0, 0, 0 );
    }

    /**
     * Returns the value of the text field
     * @return  The string value of the text field
     */
    public String getValue( )
    {
        return this.passwordFieldComponent.getPasswordString( );
    }
}
