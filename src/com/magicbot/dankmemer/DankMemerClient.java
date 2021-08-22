package com.magicbot.dankmemer;

import com.magicbot.HttpClientWrapper;
import com.magicbot.discord.DiscordClient;

import java.util.ArrayList;

public class DankMemerClient extends DiscordClient
{
    private ArrayList< InventoryItem > inventoryItems = new ArrayList< InventoryItem >( );
    public boolean updatingInventory = false;

    /**
     * Creates a new DiscordClient object and attempts to initialise user data using the token
     *
     * @param httpWrapper An HttpClientWrapper object which this object can use to send Http requests
     * @param token       The token to login to Discord with
     */
    public DankMemerClient( HttpClientWrapper httpWrapper, String token )
    {
        super(httpWrapper, token);
    }

    public void transferTo( String channelId, String id )
    {
        this.sendMessage( channelId, "pls give <@" + id + "> all" );
    }

    public void withdraw( String channelId )
    {
        this.sendMessage( channelId, "pls withdraw all" );
    }

    public void sellItem( String channelId, String itemId, String countStr )
    {
        InventoryItem item = null;
        int count = 0;

        for( InventoryItem i : this.inventoryItems )
        {
            if( i.getId( ).equals( itemId ) )
            {
                item = i;
            }
        }

        if( item == null ) return;

        if( countStr.equals( "max" ) )
        {
            this.sendMessage( channelId, "pls sell " + itemId + " max" );
        }
        else
        {
            try
            {
                count = Integer.parseInt( countStr );
                if( count > item.getCount( ) )
                    return;
            }
            catch( Exception ex )
            {
                return;
            }

            this.sendMessage( channelId, "pls sell " + itemId + " " + count );
        }
    }

    public void addInventoryItem( InventoryItem item )
    {
        for( InventoryItem i : this.inventoryItems)
        {
            if( i.getName( ).equals( item.getName( ) ) )
            {
                i.setCount( item.getCount( ) );
                return;
            }
        }
        this.inventoryItems.add( item );
    }

    public void updateInventory( String channelId )
    {
        this.updatingInventory = true;
        this.sendMessage( channelId, "pls inv" );
    }

    public ArrayList< InventoryItem > getInventoryItems( )
    {
        return this.inventoryItems;
    }
}
