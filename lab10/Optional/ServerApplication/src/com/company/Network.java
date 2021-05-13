package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Network {
    public List<Client> users;
    Network(){
        users=new ArrayList<>();
    }
    public void addUser(Client c){
        users.add(c);
    }

    public List<Client> getUsers() {
        return users;
    }

    public void setUsers(List<Client> users) {
        this.users = users;
    }

    public void printUsers(){
        Stream.of(users.toString())
                .forEach(System.out::println);
    }

}
