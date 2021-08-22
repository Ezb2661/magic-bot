package com.magicbot.discord;

import com.magicbot.HttpClientWrapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.util.function.Consumer;

public class DiscordClient implements Runnable
{
    public static final String DISCORD_API_URL = "https://discord.com/api/v8/";

    private final HttpClientWrapper httpWrapper;
    private WebsocketClientEndpoint websocketClientEndpoint;
    private String gateway;
    private MessageReceivedHandler messageReceivedHandler;

    private final String token;
    private String username;
    private String discrim;
    private String id;

    public int lastSequenceNumber;
    public long heartbeatInterval;

    /**
     * Creates a new DiscordClient object and attempts to initialise user data using the token
     * @param httpWrapper   An HttpClientWrapper object which this object can use to send Http requests
     * @param token         The token to login to Discord with
     */
    public DiscordClient( HttpClientWrapper httpWrapper, String token )
    {
        this.token = token;
        this.httpWrapper = httpWrapper;
    }

    /**
     * Attempts to initialise user data using the token to login
     *
     */
    public void initUserData( Consumer< DiscordClient > lambda )
    {
        httpWrapper.sendGet(DISCORD_API_URL + "users/@me", new String[][]{ { "authorization", token } } ).thenApply(resp ->
        {
            try {
                JSONObject jsonResp = ( JSONObject )new JSONParser( ).parse( resp.body( ) );
                if ( jsonResp.get( "code" ) == ( Integer )0 )
                {
                    return resp;
                }
                else
                {
                    this.id = (String) jsonResp.get("id");
                    this.username = (String) jsonResp.get("username");
                    this.discrim = (String) jsonResp.get("discriminator");
                    lambda.accept( this );
                }
            }
            catch ( ParseException e )
            {
                e.printStackTrace();
            }
            return resp;
            });
    }

    public void initGateway( )
    {
        this.httpWrapper.sendGet( DISCORD_API_URL + "gateway", new String[][]{ { "authorization",this.token } } ).thenApply( response ->
        {
            JSONObject jsonResponse = ( JSONObject )JSONValue.parse( response.body( ) );
            this.gateway = ( String )jsonResponse.get( "url" );
            new Thread( this ).start( );
            return response;
        }
        );
    }

    public void setMessageReceivedHandler( MessageReceivedHandler messageReceivedHandler )
    {
        this.messageReceivedHandler = messageReceivedHandler;
    }

    public void run( )
    {
        websocketClientEndpoint = new WebsocketClientEndpoint( URI.create( this.gateway ) );
        websocketClientEndpoint.addMessageHandler( this.messageReceivedHandler );
        sendHeartbeat( );
        sendIdentify( );
        while( true )
        {
            try
            {
                Thread.sleep(this.heartbeatInterval);
            }
            catch( InterruptedException e )
            {
                e.printStackTrace( );
            }
            try
            {
                sendHeartbeat();
            }
            catch( Exception e )
            {
                websocketClientEndpoint = new WebsocketClientEndpoint( URI.create( gateway ) );
                websocketClientEndpoint.addMessageHandler( this.messageReceivedHandler );
                sendIdentify( );
                sendHeartbeat( );
            }
        }
    }

    /**
     * Sends a message to a specified channel under the current account
     * @param channelId A string value containing the desired channel Id
     * @param message A string value containing the desired message
     */
    public void sendMessage( String channelId, String message )
    {
        JSONObject jsonBody = new JSONObject( );
        jsonBody.put( "content" , message );
        jsonBody.put( "nonce" , 0 );
        jsonBody.put( "tts", false );

        httpWrapper.sendPost( DISCORD_API_URL + "channels/" + channelId + "/messages", jsonBody, new String[][]{ { "content-type" , "application/json" }, { "authorization", this.token } } );
    }

    public void sendHeartbeat( )
    {
        JSONObject data = new JSONObject( );
        data.put( "op", 1 );
        data.put( "d", lastSequenceNumber );
        this.websocketClientEndpoint.sendMessage( data.toJSONString( ) );
    }

    public void sendIdentify( )
    {
        JSONObject properties = new JSONObject( );
        properties.put( "$os" , "1337" );
        properties.put( "$browser", "1337" );
        properties.put( "$device", "1337" );

        JSONObject data = new JSONObject( );
        data.put( "token" ,  token );
        data.put( "intents", 4608 );
        data.put( "properties" , properties );

        JSONObject requestData = new JSONObject( );
        requestData.put( "op" , 2 );
        requestData.put( "d", data );

        this.websocketClientEndpoint.sendMessage( requestData.toJSONString( ) );
    }

    /**
     * Get the username of the client
     * @return  String object containing the username of the client
     */
    public String getUsername( )
    {
        return this.username;
    }

    public String getDiscrim( )
    {
        return this.discrim;
    }

    public String getId( )
    {
        return this.id;
    }
}
