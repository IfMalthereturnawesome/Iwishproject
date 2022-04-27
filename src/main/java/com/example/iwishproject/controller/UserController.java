package com.example.iwishproject.controller;

import com.example.iwishproject.repository.IWishRepository;
import com.example.iwishproject.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

  @GetMapping("/login")
  public String login(){
    return "login";
  }

  @PostMapping("/tilmeld")
  public String tilmeld(@RequestParam("eMail") String eMail,
                        @RequestParam("password") String password){
    UserRepository userRepository = new UserRepository();
    userRepository.createUser(eMail,password);

    return "redirect:/login";
  }

}
