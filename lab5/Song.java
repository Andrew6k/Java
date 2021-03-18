package com.company;

public class Song extends Item{
    public String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    Song(String s){
        author=s;
    }
}
