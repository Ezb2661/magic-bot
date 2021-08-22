package com.magicbot.frames;

import com.magicbot.MagicBot;
import com.magicbot.dankmemer.DankMemerFrame;
import com.magicbot.frames.components.BaseButton;
import com.magicbot.frames.components.BaseList;
import com.magicbot.frames.components.BasePanel;

public class BotSelectFrame extends BaseFrame
{
    private static final int FRAME_WIDTH = 350;
    private static final int FRAME_HEIGHT = 200;

    private BaseList<BotType> botList;
    private BaseButton selectButton;
    private BasePanel selectionPanel;

    enum BotType
    {
        DANK_MEMER,
    }

    public BotSelectFrame( )
    {
        super( "MagicBot", FRAME_WIDTH, FRAME_HEIGHT, true );
    }

    @Override
    public void initComponents( )
    {
        selectionPanel = new BasePanel( "Bot Select", 10, 30, FRAME_WIDTH - 20, FRAME_HEIGHT - 40 );

        botList = new BaseList( 10, 20, 150, 100 );
        botList.addItem( "Dank Memer", BotType.DANK_MEMER );
        selectButton = new BaseButton( "Select", 165, 20,150, 50, e-> selectBot( botList.getSelectedItem( ) ) );
    }

    @Override
    public void addComponents( )
    {
        this.add( selectionPanel );
        selectionPanel.add( botList );
        selectionPanel.add( selectButton );
    }

    private void selectBot( BotType botType )
    {
        switch( botType )
        {
            case DANK_MEMER:
            {
                this.setVisible( false );
                MagicBot.dankMemerFrame = new DankMemerFrame( );
                break;
            }
            default:
            {
                this.errorMsg( "Bot has not yet been implemented." );
            }
        }
    }
}
