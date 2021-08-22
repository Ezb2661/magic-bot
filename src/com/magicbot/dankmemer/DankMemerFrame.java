package com.magicbot.dankmemer;

import com.magicbot.MagicBot;
import com.magicbot.discord.DiscordClient;
import com.magicbot.discord.MessageReceivedEvent;
import com.magicbot.discord.MessageReceivedHandler;
import com.magicbot.frames.BaseFrame;
import com.magicbot.frames.components.*;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

public class DankMemerFrame extends BaseFrame
{
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;

    public String channelId = "";

    private BasePanel mainPanel;

    private BasePanel accountPanel;
    private BaseList< DankMemerClient > accountList;
    private BaseButton importAccountsButton;

    private BasePanel automationPanel;
    private BaseCheckBox autoBegCheckBox;
    private BaseCheckBox autoFishCheckBox;
    private BaseCheckBox autoHuntCheckBox;
    private BaseCheckBox autoSearchCheckBox;
    private BaseCheckBox autoDepositCheckBox;

    private BasePanel settingsPanel;
    private BaseLabeledTextField channelIdField;
    private BaseButton setChannelId;

    private BasePanel transferPanel;
    private BaseLabeledTextField transferAccountIdField;
    private BaseButton withdrawButton;
    private BaseButton transferButton;

    private BaseButton inventoryButton;

    public DankMemerFrame( )
    {
        super( "Magic Bot - Dank Memer", FRAME_WIDTH, FRAME_HEIGHT, true );
    }

    @Override
    public void initComponents( )
    {
        mainPanel = new BasePanel( "Dank Memer", 10, 30, FRAME_WIDTH - 20, FRAME_HEIGHT - 40 );

        /*  Accounts  */
        /*==========================================================================================*/
        accountPanel = new BasePanel( "Accounts", 10, 20, 350, 275 );
        accountList = new BaseList( 10, 20, this.getStringWidth( "Import From Clipboard" ), 200 );
        importAccountsButton = new BaseButton("Import From Clipboard",
                10, 230,
                this.getStringWidth("Import From Clipboard"), 30,
                e -> importAccounts( ) );
        inventoryButton = new BaseButton( "Inventory", 215, 15, this.getStringWidth( "Inventory" ) + 20, 30, e->
        {
            getSelectedClient( ).updateInventory( getChannelID( ) );
            new DankMemerInventoryFrame( this.accountList.getSelectedItem( ) );
        } );
        /*==========================================================================================*/

        /*  Automation  */
        /*==========================================================================================*/
        automationPanel = new BasePanel( "Automation", 365, 20, 400, 275 );
        autoBegCheckBox = new BaseCheckBox( 10, 20, this.getStringWidth( "Auto Beg" ) + 20, 30, "Auto Beg" );
        autoFishCheckBox = new BaseCheckBox( 10, 50, this.getStringWidth( "Auto Fish" ) + 20, 30, "Auto Fish" );
        autoHuntCheckBox = new BaseCheckBox( 10, 80, this.getStringWidth( "Auto Hunt" ) + 20, 30, "Auto Hunt" );
        autoSearchCheckBox = new BaseCheckBox( 10, 110, this.getStringWidth( "Auto Search" ) + 20, 30, "Auto Search" );
        autoDepositCheckBox = new BaseCheckBox( 10, 140, this.getStringWidth( "Auto Deposit" ) + 20, 30, "Auto Deposit" );
        /*==========================================================================================*/

        /*  Settings  */
        /*==========================================================================================*/
        settingsPanel = new BasePanel( "Settings", 10, 300, 350, 150 );
        channelIdField = new BaseLabeledTextField( "Channel ID", this.getStringWidth( "Channel ID" ), 10, 15 );
        setChannelId = new BaseButton("Set Channel ID", 10, 85, 200, 30, e ->
                this.channelId = this.channelIdField.getValue( ) );
        /*==========================================================================================*/

        /*  Transfer  */
        /*==========================================================================================*/
        transferPanel = new BasePanel( "Transfer", 365, 300, 400, 150 );
        transferAccountIdField = new BaseLabeledTextField( "Account ID", this.getStringWidth( "Account ID" ), 10, 15 );
        withdrawButton = new BaseButton( "Withdraw", 10, 85, 200, 30, e->
        {
            for( int i = 0; i < accountList.getListSize( ); i++ )
            {
                accountList.getItem( i ).withdraw( getChannelID( ) );
            }
        } );
        transferButton = new BaseButton( "Transfer", 10, 115, 200, 30, e ->
        {
            for( int i = 0; i < accountList.getListSize( ); i++ )
            {
                accountList.getItem( i ).transferTo( getChannelID( ), transferAccountIdField.getValue( ) );
            }
        } );
        /*==========================================================================================*/
    }

    @Override
    public void addComponents( )
    {
        this.add( mainPanel );
        mainPanel.add( accountPanel );
        mainPanel.add( automationPanel );
        mainPanel.add( settingsPanel );
        mainPanel.add( transferPanel );

        accountPanel.add( accountList );
        accountPanel.add( importAccountsButton );
        accountPanel.add( inventoryButton );

        automationPanel.add( autoBegCheckBox );
        automationPanel.add( autoFishCheckBox );
        automationPanel.add( autoHuntCheckBox );
        automationPanel.add( autoSearchCheckBox );
        automationPanel.add( autoDepositCheckBox );

        settingsPanel.add( setChannelId );
        settingsPanel.add( channelIdField );

        transferPanel.add( transferAccountIdField );
        transferPanel.add( withdrawButton );
        transferPanel.add( transferButton );
    }

    public boolean isAutoBegEnabled( ) { return this.autoBegCheckBox.isSelected( ); };

    public boolean isAutoFishEnabled( ) { return this.autoFishCheckBox.isSelected( ); };

    public boolean isAutoHuntEnabled( ) { return this.autoHuntCheckBox.isSelected( ); };

    public boolean isAutoSearchEnabled( ) { return this.autoSearchCheckBox.isSelected( ); };

    public boolean isAutoDepositEnabled( ) { return this.autoDepositCheckBox.isSelected( ); };

    public String getChannelID( ) { return this.channelId; };

    public DankMemerClient getClientFromId( String clientId )
    {
        if( !clientAlreadyAdded( clientId ) ) return null;
        for( int i = 0; i < accountList.getListSize( ); i++ )
        {
            if( accountList.getItem( i ).getId( ).equals( clientId ) )
                return accountList.getItem( i );
        }
        return null;
    }

    public DankMemerClient getSelectedClient( )
    {
        return this.accountList.getSelectedItem( );
    }

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
                DankMemerClient client = new DankMemerClient( MagicBot.httpWrapper, clipboard );
                client.initUserData( discordClient ->
                {
                    if( discordClient.getId( ) != null && !clientAlreadyAdded( discordClient.getId( ) ) )
                    {
                        new DankMemerThread( client ).start( );
                        accountList.addItem(discordClient.getUsername() + "#" + discordClient.getDiscrim(), ( DankMemerClient )discordClient);
                        client.setMessageReceivedHandler( new DankMemerMessageHandler( client ) );
                        client.initGateway( );
                    }
                } );
            }
            else
            {
                String[] tokens = clipboard.split( "," );
                for( int i = 0; i < tokens.length; i++ )
                {
                    DankMemerClient client = new DankMemerClient( MagicBot.httpWrapper, tokens[i] );
                    int finalI = i;
                    client.initUserData(discordClient ->
                    {
                        if( discordClient.getId( ) != null && !clientAlreadyAdded( discordClient.getId( ) ) )
                        {
                            while( finalI != accountList.getListSize( ) )
                            {
                                try
                                {
                                    Thread.sleep(1);
                                }
                                catch( Exception ex )
                                {

                                }
                            }
                            accountList.addItem(discordClient.getUsername( ) + "#" + discordClient.getDiscrim(), ( DankMemerClient )discordClient);
                            new DankMemerThread( client ).start( );
                            client.setMessageReceivedHandler( new DankMemerMessageHandler( client ) );
                            client.initGateway( );
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