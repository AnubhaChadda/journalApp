package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserEntry user) {
        userService.saveNewUser(user);
    }

    @GetMapping("/health-check")
    public String healthCheck(){
            return "ok";
        }



}
