package com.example.iwishproject.controller;

import com.example.iwishproject.model.Wish;
import com.example.iwishproject.repository.IWishRepository;
import com.example.iwishproject.utility.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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


  @GetMapping("/onsker/{wishListID}")
  public String viewPage(@PathVariable("wishListID") int wishListID,  Model model){
    List<Wish> onsker = iWishRepository.findAllWishes(wishListID);
    model.addAttribute("onske",onsker);

    return "onsker";
  }

  @PostMapping("/tilføjonske")
  public String addWish(@RequestParam("title") String title,
                        @RequestParam("description") String description,
                        @RequestParam("price") double price,
                        @RequestParam("link") String link,
                        @RequestParam( value = "image", required = false) MultipartFile multipartFile) throws IOException {
    IWishRepository iWishRepository = new IWishRepository();
    Wish newWish = new Wish();
    newWish.setTitle(title);
    newWish.setDescription(description);
    newWish.setPrice(price);
    newWish.setLink(link);

    String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    newWish.setPhotos(fileName);

    if (fileName.isEmpty()) {
      newWish.setPhotos("gave.jpg");
      iWishRepository.addWish(newWish);
    } else {

      //Wish savedWish = iWishRepository.addWish(wish);
      String uploadDir = "user-photos";

      FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
      iWishRepository.addWish(newWish);
    }

    return "redirect:/onsker";
  }

  @GetMapping("/sletonske/{id}")
  public String deleteWish(@PathVariable("id") int id){
    IWishRepository iWishRepository = new IWishRepository();
    iWishRepository.deleteWish(id);

    return "redirect:/onsker";
  }

}
