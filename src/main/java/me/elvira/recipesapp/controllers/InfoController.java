package me.elvira.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Tag(name = "Информация", description = "информация о приложении")
public class InfoController {
    @GetMapping("/")
    @Operation(summary = "приветствие")
    public String helloWord() {
        return "Hello, web";
    }
    @GetMapping("/info")
    @Operation(summary = "данные проекта")
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
