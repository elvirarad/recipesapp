package me.elvira.recipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class FirstController {
    @GetMapping("/")
    public String helloWord() {
        return "Hello, web";
    }
    @GetMapping("/info")
    public String Info() {
            String name = "Колобок";
            String project = "Побег";
            LocalDate data = LocalDate.of(2023,3,12);
            String infoProject = "я от бабушки убежал";
            return "имя ученика: " + name +
                    ", название проекта: " + project +
                    ", дата создания проекта: " + data +
                    ", описание проекта: " + infoProject;
    }
}
