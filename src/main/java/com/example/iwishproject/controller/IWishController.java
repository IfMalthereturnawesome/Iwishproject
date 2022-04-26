package com.example.iwishproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IWishController {

  @GetMapping("/")
  public String index(){
    return "index";
  }
@GetMapping("/hvordan")
  public String hvordan(){
    return "hvordan";
}
@GetMapping("/login")
public String login(){
    return "login";
}
@GetMapping("/omos")
public String omOs(){
    return "omos";
}
@GetMapping("/ønskeliste")
public String onskeliste(){
    return "ønskeliste";
}
}
