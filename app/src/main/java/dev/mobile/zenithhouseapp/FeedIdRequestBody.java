package dev.mobile.zenithhouseapp;

public class FeedIdRequestBody {
    private int id;
    private String name;
    private String number;
    private String feed;

    public FeedIdRequestBody(int id)
    {
        this.id = id;
    }

    // Getters and setters if needed

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}
