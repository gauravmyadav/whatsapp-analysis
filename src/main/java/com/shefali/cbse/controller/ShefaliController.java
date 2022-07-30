package com.shefali.cbse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/love")
public class ShefaliController {

    private static final String LOVELY_MESSAGE = "This is to hereby inform you, that your boyfriend loves you a lot %s";

    @GetMapping
    public String getLovelyMessages(@RequestParam (defaultValue = "Shefali") String name) {
        return String.format(LOVELY_MESSAGE, name);
    }
}
