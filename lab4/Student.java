package com.company;

public class Student {
    String nume;
    Student(String s){
      nume=s;
    }
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                '}';
    }
}
