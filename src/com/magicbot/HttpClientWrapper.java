package com.magicbot;

import org.json.simple.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class HttpClientWrapper
{
    private HttpClient httpClient;

    public HttpClientWrapper( )
    {
        httpClient = HttpClient.newHttpClient( );
    }

    /**
     * Sends a POST request to a target URL
     * @param url       The URL to send the post request to
     * @param data      The JSON object which contains the request body
     * @param headers   An array of string arrays which contain the headers in the following format:
     *                  {
     *                      { "key1", "value1 },
     *                      { "key2", "value2" }
     *                  }
     */
    public void sendPost( String url, JSONObject data, String[][] headers )
    {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder( URI.create( url ) );
        httpRequestBuilder = httpRequestBuilder.POST( HttpRequest.BodyPublishers.ofString( data.toJSONString( ) ) );

        for( String[] header : headers )
        {
            httpRequestBuilder = httpRequestBuilder.setHeader( header[0], header[1] );
        }

        HttpRequest httpRequest = httpRequestBuilder.build( );
        this.httpClient.sendAsync( httpRequest, HttpResponse.BodyHandlers.ofString( ) );
    }

    /**
     * Sends a POST request to a target URL
     * @param url       The URL to send the post request to
     * @param rawData   A string which contains the raw data to be included in the request body
     * @param headers   An array of string arrays which contain the headers in the following format:
 *
 *                      { "key1", "value1 },
 *                      { "key2", "value2" }
     * @return
     */
    public CompletableFuture<HttpResponse<String>> sendPost(String url, String rawData, String[][] headers )
    {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder( URI.create( url ) );
        httpRequestBuilder = httpRequestBuilder.POST( HttpRequest.BodyPublishers.ofString( rawData ) );

        for( String[] header : headers )
        {
            httpRequestBuilder = httpRequestBuilder.setHeader( header[0], header[1] );
        }

        HttpRequest httpRequest = httpRequestBuilder.build( );

        return httpClient.sendAsync( httpRequest, HttpResponse.BodyHandlers.ofString( ) );
    }

    /**
     * Sends a GET request ot a target URL
     * @param url       The URL to send the post request to
     * @param headers   An array of string arrays which contain the headers in the following format:
     *                  {
     *                      { "key1", "value1" },
     *                      { "key2", "value2" }
     *                  }
     */
    public CompletableFuture<HttpResponse<String>> sendGet( String url, String[][] headers )
    {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder( URI.create( url ) );
        httpRequestBuilder = httpRequestBuilder.GET( );

        for( String[] header : headers )
        {
            httpRequestBuilder = httpRequestBuilder.setHeader( header[0], header[1] );
        }

        HttpRequest httpRequest = httpRequestBuilder.build( );

        return httpClient.sendAsync( httpRequest, HttpResponse.BodyHandlers.ofString( ) );
    }
}
