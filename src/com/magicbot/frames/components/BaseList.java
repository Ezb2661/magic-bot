package com.magicbot.frames.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BaseList< T > extends JList
{
    private DefaultListModel listModel = new DefaultListModel< String >( );
    public ArrayList< T > objectList = new ArrayList< T >( );

    public BaseList( int x, int y, int width, int height )
    {
        super( );
        this.setModel( listModel );
        this.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        this.setBounds( x, y, width, height );
        this.setBorder( BorderFactory.createLineBorder( Color.GRAY ) );
    }

    public void addItem( String name, T object )
    {
        listModel.addElement( name );
        objectList.add( object );
    }

    public void removeItem( int index )
    {
        listModel.remove( index );
        objectList.remove( index );
    }

    public T getSelectedItem( )
    {
        if( this.getSelectedIndex( ) == -1 ) return null;
        return objectList.get( this.getSelectedIndex( ) );
    }

    public T getItem( int index )
    {
        return objectList.get( index );
    }

    public int getListSize( )
    {
        return objectList.size( );
    }

}
