package dev.mobile.zenithhouseapp;

public class feeds {
    private int id;
    private String name;
    private String number;
    private String feed;

    public feeds(int id, String name, String number, String feed) {
        this.id = id;
        this.name = name;
        this.number = number;
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

    @Override
    public String toString() {
        return "feeds{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", feed='" + feed + '\'' +
                '}';
    }
}