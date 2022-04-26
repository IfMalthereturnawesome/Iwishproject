package com.example.iwishproject.controller;

import com.example.iwishproject.model.Wish;
import com.example.iwishproject.repository.IWishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

  @PostMapping("/tilføjønske")
  public String addWish(@RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("price") double price,
                           @RequestParam("link") String link) {
    IWishRepository iWishRepository = new IWishRepository();
    Wish newWish = new Wish();
    newWish.setTitle(title);
    newWish.setDescription(description);
    newWish.setPrice(price);
    newWish.setLink(link);
    iWishRepository.addWish(newWish);

    return "redirect:/ønskeliste";
  }

  @PostMapping("/sletønske")
  public String deletePokemon(@RequestParam("id") int id){
    IWishRepository iWishRepository = new IWishRepository();
    iWishRepository.deleteWish(id);

    return "redirect:/ønskeliste";
  }
}
