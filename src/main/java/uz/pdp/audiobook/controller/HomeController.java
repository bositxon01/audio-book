package uz.pdp.audiobook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(@RequestParam(name = "token", required = false) String token) {
        return "OAuth2 Login Successful! Your Token: " + token;
    }
}

