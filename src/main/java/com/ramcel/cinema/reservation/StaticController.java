package com.ramcel.cinema.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/static")
public class StaticController {
    public String welcomePage(){
        return "welcome";
    }
}
