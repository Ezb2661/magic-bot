package com.magicbot.dankmemer;

public class InventoryItem
{
    private String name;
    private int count;
    private String id;

    public InventoryItem( String name, String id, int count )
    {
        this.name = name;
        this.count = count;
        this.id = id;
    }

    public String getName( )
    {
        return this.name;
    }

    public int getCount( )
    {
        return this.count;
    }

    public String getId( )
    {
        return this.id;
    }

    public void setCount( int count )
    {
        this.count = count;
    }
}
