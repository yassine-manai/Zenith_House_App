package dev.mobile.zenithhouseapp;

public class FeedRequestBody
{
    private int id;
    private String name;
    private String phone;
    private String feed;

    public FeedRequestBody(int id, String name, String phone, String feed)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.feed = feed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}
