package uz.pdp.audiobook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RestController
public class HomeController {

    @GetMapping()
    public String home(@RequestParam(name = "token", required = false) String token) {
        return "OAuth2 Login Successful! Your Token: " + token;
    }
}

