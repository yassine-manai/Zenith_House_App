package dev.mobile.zenithhouseapp;

public class feeds {
    private int id;
    private String name;
    private String phone;
    private String feed;

    public feeds(int id, String name, String phone, String feed)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.feed = feed;
    }

    public feeds(int id)
    {
        this.id = id;
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

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    @Override
    public String toString() {
        return "feeds{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", feed='" + feed + '\'' +
                '}';
    }
}