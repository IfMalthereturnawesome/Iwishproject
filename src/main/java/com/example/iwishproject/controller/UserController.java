package com.example.iwishproject.controller;

import com.example.iwishproject.model.User;
import com.example.iwishproject.model.Wish;
import com.example.iwishproject.repository.IWishRepository;
import com.example.iwishproject.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

  @GetMapping("/login")
  public String login(){
    return "login";
  }

  @GetMapping("/tilmeld")
  public String tilmeldSide(){
    return "tilmeld";
  }


  @PostMapping("/tilmeld")
  public String tilmeld(@RequestParam("eMail") String eMail,
                        @RequestParam("password") String password,
                        @RequestParam("firstName") String firstName,
                        @RequestParam("lastName") String lastName){

    UserRepository userRepository = new UserRepository();
    User newUser = new User();
    newUser.seteMail(eMail);
    newUser.setPassword(password);
    newUser.setFirstName(firstName);
    newUser.setLastName(lastName);

    userRepository.createUser(newUser);
    return "redirect:/login";
  }

  @PostMapping("/login")
  public String loginValidate(@RequestParam("eMail") String eMail,
                              @RequestParam("password") String password,
                              HttpSession session,
                              ModelMap modelMap){
    UserRepository userRepository = new UserRepository();
  if (userRepository.passwordCheck(userRepository.findUser(eMail),password)){
    return "redirect:/index";
  }else
    return "redirect:/tilmeld";
  }
}
