package com.example.iwishproject.controller;

import com.example.iwishproject.repository.IWishRepository;
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
    IWishRepository iWishRepository = new IWishRepository();
    iWishRepository.createUser(eMail,password);

    return "redirect:/login";
  }

}
