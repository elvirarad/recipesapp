package me.elvira.recipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class InfoController {
    @GetMapping("/")
    public String helloWord() {
        return "Hello, web";
    }
    @GetMapping("/info")
    public String Info() {
            String name = "Enikeeva Elvira";
            String project = "Recipe";
            LocalDate data = LocalDate.of(2023,3,13);
            String infoProject = "app for managing the recipes";
            return "student: " + name +
                    ", project: " + project +
                    ", date: " + data +
                    ", project description: " + infoProject;
    }
}
