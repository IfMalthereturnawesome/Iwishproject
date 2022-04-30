package com.example.iwishproject.controller;

import com.example.iwishproject.model.Wish;
import com.example.iwishproject.repository.IWishRepository;
import com.example.iwishproject.repository.WishListRepository;
import com.example.iwishproject.utility.FileUploadUtil;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class WishController {

  IWishRepository iWishRepository;
  WishListRepository wishListRepository;

  public WishController(IWishRepository iWishRepository, WishListRepository wishListRepository){
    this.iWishRepository = iWishRepository;
    this.wishListRepository = wishListRepository;
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


  @GetMapping("/onsker/{wishListID}")
  public String viewPage(@PathVariable("wishListID") int wishListID,  Model model){
    List<Wish> onsker = iWishRepository.findAllWishes(wishListID);
    model.addAttribute("onske",onsker);

    return "onsker";
  }



  @PostMapping("/tilføjonske")
  public String addWish(@RequestParam("wishListID") int wishListID,
                        @RequestParam("title") String title,
                        @RequestParam("description") String description,
                        @RequestParam("price") double price,
                        @RequestParam("link") String link,
                        @ModelAttribute("wish") Wish wish,
                        @RequestParam( value = "image", required = false) MultipartFile multipartFile
                       ) throws IOException {
    IWishRepository iWishRepository = new IWishRepository();
    Wish newWish = new Wish();
    newWish.setTitle(title);
    newWish.setDescription(description);
    newWish.setPrice(price);
    newWish.setLink(link);
    newWish.setListID(wishListID);


    String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    newWish.setPhotos(fileName);

    if (fileName.isEmpty()) {
      newWish.setPhotos("gave.jpg");


    } else {
      String uploadDir = "user-photos/";
      FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

    }

    iWishRepository.addWish(newWish);
    return "redirect:/onsker/"+wishListID;

  }

  @GetMapping("/sletonske/{id}")
  public String deleteWish(@PathVariable("id") int id){
    IWishRepository iWishRepository = new IWishRepository();
    iWishRepository.deleteWish(id);

    return "redirect:/onsker";
  }

}
