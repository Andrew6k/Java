package com.company;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public String name;
    public List<Client> friends;
    public String messages;
    Client(){
        this.friends=new ArrayList<>();
    }
    Client(String name){
        this.name=name;
        this.friends=new ArrayList<>();
    }
    Client(Client c){
        this.name=c.name;
        this.friends=new ArrayList<>();
        this.friends=c.getFriends();
        this.messages=c.messages;
    }
    public void addFriend(Client c){
        friends.add(c);
    }
    public String getName() {
        return name;
    }
    public void addMessage(Client c,String s){ this.messages=" "+c.name+" "+s;}
    public void setName(String name) {
        this.name = name;
    }

    public List<Client> getFriends() {
        return friends;
    }

    public void setFriends(List<Client> friends) {
        this.friends = friends;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                '}';
    }
}
