package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping(value="/mypage")
    public String myPage() throws Exception{
        System.out.println("my page?");
        return "user/mypage";
    }
}
