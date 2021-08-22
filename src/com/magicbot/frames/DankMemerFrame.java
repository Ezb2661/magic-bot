package com.magicbot.frames;

import com.magicbot.DankMemerThread;
import com.magicbot.MagicBot;
import com.magicbot.discord.DiscordClient;
import com.magicbot.frames.components.*;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DankMemerFrame extends BaseFrame
{
    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 500;

    public String channelId = "";

    private BasePanel mainPanel;

    private BasePanel accountPanel;
    private BaseList< DiscordClient > accountList;
    private BaseButton importAccountsButton;

    private BasePanel automationPanel;
    private BaseCheckBox autoBegCheckBox;
    private BaseCheckBox autoFishCheckBox;
    private BaseCheckBox autoHuntCheckBox;

    private BasePanel settingsPanel;
    private BaseLabeledTextField channelIdField;
    private BaseButton setChannelId;

    public DankMemerFrame( )
    {
        super( "Magic Bot - Dank Memer", FRAME_WIDTH, FRAME_HEIGHT, true );
    }

    @Override
    public void initComponents( )
    {
        mainPanel = new BasePanel( "Dank Memer", 10, 30, FRAME_WIDTH - 20, FRAME_HEIGHT - 40 );

        accountPanel = new BasePanel( "Accounts", 10, 20, 350, 275 );
        accountList = new BaseList( 10, 20, this.getStringWidth( "Import From Clipboard" ), 200 );
        /*  Import Accounts  */
        /*==========================================================================================*/
        importAccountsButton = new BaseButton("Import From Clipboard",
                10, 230,
                this.getStringWidth("Import From Clipboard"), 30,
                e -> importAccounts( ) );
        /*==========================================================================================*/

        /*  Automation  */
        /*==========================================================================================*/
        automationPanel = new BasePanel( "Automation", 365, 20, 300, 275 );
        autoBegCheckBox = new BaseCheckBox( 10, 20, this.getStringWidth( "Auto Beg" ) + 20, 30, "Auto Beg" );
        autoFishCheckBox = new BaseCheckBox( 10, 50, this.getStringWidth( "Auto Fish" ) + 20, 30, "Auto Fish" );
        autoHuntCheckBox = new BaseCheckBox( 10, 80, this.getStringWidth( "Auto Hunt" ) + 20, 30, "Auto Hunt" );
        /*==========================================================================================*/

        /*  Settings  */
        /*==========================================================================================*/
        settingsPanel = new BasePanel( "Settings", 10, 300, 250, 150 );
        channelIdField = new BaseLabeledTextField( "Channel ID", this.getStringWidth( "Channel ID" ), 10, 20 );
        setChannelId = new BaseButton("Set Channel ID", 10, 90, 200, 30, e ->
                this.channelId = this.channelIdField.getValue( ) );
        /*==========================================================================================*/

    }

    @Override
    public void addComponents( )
    {
        this.add( mainPanel );
        mainPanel.add( accountPanel );
        mainPanel.add( automationPanel );
        mainPanel.add( settingsPanel );

        accountPanel.add( accountList );
        accountPanel.add( importAccountsButton );

        automationPanel.add( autoBegCheckBox );
        automationPanel.add( autoFishCheckBox );
        automationPanel.add( autoHuntCheckBox );

        settingsPanel.add( setChannelId );
        settingsPanel.add( channelIdField );
    }

    public boolean isAutoBegEnabled( ) { return this.autoBegCheckBox.isSelected( ); };

    public boolean isAutoFishEnabled( ) { return this.autoFishCheckBox.isSelected( ); };

    public boolean isAutoHuntEnabled( ) { return this.autoHuntCheckBox.isSelected( ); };

    public String getChannelID( ) { return this.channelId; };

    private boolean clientAlreadyAdded( String clientId )
    {
        for( int i = 0; i < accountList.getListSize( ); i++ )
        {
            if( accountList.getItem( i ).getId( ).equals( clientId ) )
                return true;
        }
        return false;
    }

    private void importAccounts( )
    {
        try
        {
            String clipboard = ( String )Toolkit.getDefaultToolkit( ).getSystemClipboard( ).getData( DataFlavor.stringFlavor );
            if( !clipboard.contains( "," ) )
            {
                DiscordClient client = new DiscordClient( MagicBot.httpWrapper, clipboard );
                client.initUserData( discordClient ->
                {
                    if( discordClient.getId( ) != null && !clientAlreadyAdded( discordClient.getId( ) ) )
                    {
                        new DankMemerThread( client ).start( );
                        accountList.addItem(discordClient.getUsername() + "#" + discordClient.getDiscrim(), discordClient);
                    }
                } );
            }
            else
            {
                String[] tokens = clipboard.split( "," );
                for( int i = 0; i < tokens.length; i++ )
                {
                    DiscordClient client = new DiscordClient( MagicBot.httpWrapper, tokens[i] );
                    client.initUserData( discordClient ->
                    {
                        if( discordClient.getId( ) != null && !clientAlreadyAdded( discordClient.getId( ) ) )
                        {
                            accountList.addItem(discordClient.getUsername() + "#" + discordClient.getDiscrim(), discordClient);
                        }
                    } );
                }
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace( );
        }
    }
}
