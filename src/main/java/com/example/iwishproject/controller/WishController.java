package com.example.iwishproject.controller;

import com.example.iwishproject.model.Wish;
import com.example.iwishproject.repository.IWishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WishController {

  IWishRepository iWishRepository;

  public WishController(IWishRepository iWishRepository){
    this.iWishRepository = iWishRepository;
  }

  @GetMapping("/")
  public String index(){
    return "index";
  }
@GetMapping("/hvordan")
  public String hvordan(){
    return "hvordan";
}
@GetMapping("/omos")
public String omOs(){
    return "omos";
}
@GetMapping("/onskeliste")
public String onskeliste(){
    return "onskeliste";
}

/*@GetMapping("/onsker")
    public String onsker(){
      return "onsker";
  }*/

  @GetMapping("/onsker/{id}")
  public String viewPage(Model model, @PathVariable("id") int id){
    List<Wish> onsker = iWishRepository.findAllWishes();
    model.addAttribute("onske",onsker);

    return "onsker";
  }

  @PostMapping("/tilføjonske")
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

    return "redirect:/onskeliste";
  }

  @PostMapping("/sletonske")
  public String deletePokemon(@RequestParam("id") int id){
    IWishRepository iWishRepository = new IWishRepository();
    iWishRepository.deleteWish(id);

    return "redirect:/onskeliste";
  }

}
