package com.magicbot.dankmemer;

import com.magicbot.MagicBot;
import com.magicbot.frames.BaseFrame;
import com.magicbot.frames.components.BaseButton;
import com.magicbot.frames.components.BaseList;
import com.magicbot.frames.components.BasePanel;

import javax.swing.*;

public class DankMemerInventoryFrame extends BaseFrame
{
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 500;

    private BasePanel inventoryPanel;
    private BaseButton sellButton;

    private BaseList< InventoryItem > inventoryItems;

    private DankMemerClient client;

    private Thread updateInventoryThread;

    /**
     * @param client   The client object of the inventory to be viewed
     */
    public DankMemerInventoryFrame( DankMemerClient client )
    {
        super( "Inventory - " + client.getUsername( ), FRAME_WIDTH, FRAME_HEIGHT, true );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.client = client;
        this.updateInventoryThread.start( );
    }


    @Override
    public void initComponents( )
    {
        this.inventoryPanel = new BasePanel( "Inventory", 10, 30, FRAME_WIDTH - 20, FRAME_HEIGHT - 40 );
        this.inventoryItems = new BaseList< InventoryItem >( 10, 20, 200, FRAME_HEIGHT - 70 );
        updateInventoryThread = new Thread( ( ) ->
        {
            this.inventoryItems.removeAll( );
            while( this.client.updatingInventory )
            {
                try
                {
                    Thread.sleep( 1000 );
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            for( int i = 0; i < this.client.getInventoryItems( ).size( ); i++ )
            {
                InventoryItem item = this.client.getInventoryItems( ).get( i );
                this.inventoryItems.addItem( item.getName( ) + " (x" + item.getCount( ) + ")", this.client.getInventoryItems( ).get( i ) );
            }
        } );

        this.sellButton = new BaseButton("Sell", 215, 25, this.getStringWidth( "Sell" ) + 20, 30, e ->
        {
            this.client.sellItem( MagicBot.dankMemerFrame.getChannelID( ), this.inventoryItems.getSelectedItem( ).getId( ), "max" );
            this.inventoryItems.removeItem( this.inventoryItems.getSelectedIndex( ) );
            this.inventoryItems.clearSelection( );
        } );
    }


    @Override
    public void addComponents( )
    {
        this.add( inventoryPanel );
        inventoryPanel.add( inventoryItems );
        inventoryPanel.add( sellButton );
    }
}
