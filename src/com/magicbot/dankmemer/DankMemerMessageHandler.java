package com.magicbot.dankmemer;

import com.magicbot.MagicBot;
import com.magicbot.discord.DiscordClient;
import com.magicbot.discord.MessageReceivedEvent;
import com.magicbot.discord.MessageReceivedHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Random;

public class DankMemerMessageHandler extends MessageReceivedHandler
{

    private DankMemerClient client;
    private static final String BOT_ID = "270904126974590976";

    private Random random = new Random( );

    public DankMemerMessageHandler( DankMemerClient dankMemerClient )
    {
        super( dankMemerClient );
        this.client = dankMemerClient;
    }

    private void trySleep( int ms )
    {
        try
        {
            Thread.sleep( ms );
        }
        catch( InterruptedException e )
        {
            e.printStackTrace();
        }
    }

    private boolean mentionsCurrentClient( String message )
    {
        String user = message.split( "<@" )[1];
        user = user.split( ">" )[0];
        DiscordClient client = MagicBot.dankMemerFrame.getClientFromId( user );
        return this.discordClient.equals( client );
    }

    public void onSearchPrompt( String channelId, String content )
    {
        if( !mentionsCurrentClient( content ) ) return;
        String[] options = content.split( "`" );
        int choice = ( random.nextInt( 2 ) * 2 ) + 1;
        this.discordClient.sendMessage( channelId, options[choice] );
    }

    public void onDragonFound( String channelId, String content )
    {
        if( !mentionsCurrentClient( content ) ) return;
        String phrase = content.split( "`" )[1];
        trySleep( 2000 );
        this.discordClient.sendMessage( channelId, phrase );
    }

    public void onStrongFishFound( String channelId, String content )
    {
        if( !mentionsCurrentClient( content ) ) return;
        String phrase = content.split( "`" )[1];
        trySleep( 2000 );
        this.discordClient.sendMessage( channelId, phrase );
    }

    private void updateInventory( String channelId, String text, int currentPage, int maxPage )
    {
        String[] inventoryItems = text.split( "\n\n" );
        for( String itemStr : inventoryItems )
        {
            String itemName = itemStr.split( "\\*\\*" )[1];
            String itemCountStr = itemStr.split( "\\*\\* ─ " )[1];
            itemCountStr = itemCountStr.split( "\n" )[0];
            int itemCount = Integer.parseInt( itemCountStr );
            String itemId = itemStr.split( "`" )[1];
            InventoryItem item = new InventoryItem( itemName, itemId, itemCount );
            this.client.addInventoryItem( item );
        }

        if( currentPage < maxPage )
        {
            this.client.sendMessage(channelId, "pls inv " + (currentPage + 1));
        }
        else
        {
            MagicBot.dankMemerFrame.getSelectedClient( ).updatingInventory = false;
        }
    }

    @Override
    public void onMessageReceived( MessageReceivedEvent e )
    {
        if( e.getAuthorId( ).equals( BOT_ID ) && e.getChannelId( ).equals( MagicBot.dankMemerFrame.getChannelID( ) ) )
        {
            if( e.getContent( ).contains( "Where do you want to search?" ) && MagicBot.dankMemerFrame.isAutoSearchEnabled( ) )
            {
                onSearchPrompt( e.getChannelId( ), e.getContent( ) );
            }
            if( e.getContent( ).contains( "Holy fricking ship god forbid you find something innocent like a duck, ITS A DRAGON!" ) )
            {
                onDragonFound( e.getChannelId( ), e.getContent( ) );
            }
            if( e.getContent( ).contains( "ahhhhh the fish is too strong" ) )
            {
                onStrongFishFound( e.getChannelId( ), e.getContent( ) );
            }
            if( e.getEmbeds( ).size( ) > 0 && MagicBot.dankMemerFrame.getSelectedClient( ).updatingInventory )
            {
                JSONObject embed = ( JSONObject )e.getEmbeds( ).get( 0 );
                JSONObject footer = ( JSONObject )embed.get( "footer" );
                String footerText = ( String )footer.get( "text" );
                if( !footerText.startsWith( "Owned Items ─ Page " ) ) return;
                String pageNumberStr = ( String )footerText.split( "Page " )[1];
                pageNumberStr = pageNumberStr.split( " of" )[0];
                int pageNumber = Integer.parseInt( pageNumberStr );
                int maxPageNumber = Integer.parseInt( footerText.split( " of ")[1] );
                JSONObject author = ( JSONObject )embed.get( "author" );
                String title = ( String )author.get( "name" );
                String user = title.split( "'s inventory" )[0];
                if( !user.equals( this.discordClient.getUsername( ) ) ) return;
                JSONArray fieldsArray = ( JSONArray )embed.get( "fields" );
                JSONObject fields = ( JSONObject )fieldsArray.get( 0 );
                String inventoryText = ( String )fields.get( "value" );
                updateInventory( e.getChannelId( ), inventoryText, pageNumber, maxPageNumber );
            }
        }
    }
}
