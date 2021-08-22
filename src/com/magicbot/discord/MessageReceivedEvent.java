package com.magicbot.discord;

import org.json.simple.JSONArray;

public class MessageReceivedEvent
{
    private String authorId;
    private String channelId;
    private String content;
    private JSONArray embeds;

    public MessageReceivedEvent(String authorId, String channelId, String content, JSONArray embeds )
    {
        this.authorId = authorId;
        this.channelId = channelId;
        this.content = content;
        this.embeds = embeds;
    }

    public String getAuthorId() { return authorId; }

    public String getChannelId()
    {
        return channelId;
    }

    public String getContent()
    {
        return content;
    }

    public JSONArray getEmbeds()
    {
        return embeds;
    }
}
