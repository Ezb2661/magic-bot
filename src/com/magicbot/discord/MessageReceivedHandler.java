package com.magicbot.discord;

import com.magicbot.dankmemer.DankMemerClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public abstract class MessageReceivedHandler implements WebsocketClientEndpoint.MessageHandler
{
    private static final int EVENT = 0;
    private static final int HEARTBEAT = 10;

    private static final String READY = "READY";
    private static final String MESSAGE_CREATE = "MESSAGE_CREATE";

    protected DiscordClient discordClient;

    public MessageReceivedHandler( DiscordClient discordClient )
    {
        this.discordClient = discordClient;
    }

    @Override
    public void handleMessage( String message )
    {
        JSONObject jsonMsg = ( JSONObject ) JSONValue.parse( message );
        long opCode = ( long )jsonMsg.get( "op" );
        if( jsonMsg.get( "s" ) != null )
            this.discordClient.lastSequenceNumber = ( int )( long )jsonMsg.get( "s" );
        JSONObject data;

        switch( (int) opCode )
        {
            case HEARTBEAT:
            {
                data = ( JSONObject ) jsonMsg.get( "d" );
                this.discordClient.heartbeatInterval = ( long )data.get( "heartbeat_interval" );
                this.discordClient.sendHeartbeat( );
                break;
            }

            case EVENT:
            {
                String eventName = ( String )jsonMsg.get( "t" );
                if( eventName.equals( "SESSIONS_REPLACE" ) ) return;
                data = ( JSONObject )jsonMsg.get( "d" );
                switch( eventName )
                {
                    case MESSAGE_CREATE:
                    {
                        JSONObject authorJson = ( JSONObject )data.get( "author" );
                        String authorId = ( String )authorJson.get( "id" );
                        String channelId = ( String )data.get( "channel_id" );
                        String content = ( String )data.get( "content" );
                        JSONArray embeds = ( JSONArray ) data.get( "embeds" );
                        MessageReceivedEvent evt = new MessageReceivedEvent( authorId, channelId, content, embeds );
                        onMessageReceived( evt );
                        break;
                    }
                }

                break;
            }
        }
    }

    public abstract void onMessageReceived( MessageReceivedEvent e );
}
