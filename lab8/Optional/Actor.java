package com.company;

public class Actor {
    private Integer id;
    private String name;
    private String birthDate;
    private Integer height;

    public Actor() {
        this.id = 0;
        this.name = "";
        this.birthDate = "20-11-2020";
        this.height = 0;
    }

    public Actor(Integer id, String name, String birthDate, Integer height) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.height = height;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", height=" + height +
                '}';
    }
}
