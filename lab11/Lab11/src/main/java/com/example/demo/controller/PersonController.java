package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final List<Person> people = new ArrayList<>();
    public PersonController() {
        people.add(new Person(1, "Mircea"));
        people.add(new Person(2, "Matei"));
    }
    @GetMapping
    public List<Person> getPerson() {
        return people;
    }
    @GetMapping("/count")
    public int countPeople() {
        return people.size();
    }
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return people.stream()
                .filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @PostMapping
    public int createPerson(@RequestParam String name) {
        int id = 1 + people.size();
        people.add(new Person(id, name));
        return id;
    }

    @PostMapping(value = "/obj", consumes="application/json")
    public ResponseEntity<String>
    createPlayer(@RequestBody Person person) {
        people.add(person);
        return new ResponseEntity<>(
                "Product created successfully", HttpStatus.CREATED);
    }
    public Person findById(int id){
        return people.get(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(
            @PathVariable int id, @RequestParam String name) {
        Person person = findById(id);
        if (person == null) {
            return new ResponseEntity<>(
                    "Player not found", HttpStatus.NOT_FOUND); //or GONE
        }
        person.setName(name);
        return new ResponseEntity<>(
                "Player updated successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable int id) {
        Person person = findById(id);
        if (person == null) {
            return new ResponseEntity<>(
                    "Person not found", HttpStatus.GONE);
        }
        people.remove(person);
        return new ResponseEntity<>("Person removed", HttpStatus.OK);
    }
}
