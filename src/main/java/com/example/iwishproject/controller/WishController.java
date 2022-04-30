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

  @RequestMapping("/onsker")
  public String viewPage(Model model){
    List<Wish> onsker = iWishRepository.findAllWishes();
    model.addAttribute("onske",onsker);

    return "onsker";
  }

  @PostMapping("/tilføjonske")
  public String addWish(Wish wish,@RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("price") double price,
                           @RequestParam("link") String link,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
    IWishRepository iWishRepository = new IWishRepository();
    Wish newWish = new Wish();
    newWish.setTitle(title);
    newWish.setDescription(description);
    newWish.setPrice(price);
    newWish.setLink(link);

    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    newWish.setPhotos(fileName);

    //Wish savedWish = iWishRepository.addWish(wish);
    String uploadDir = "user-photos/" + newWish.getId();

    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
   iWishRepository.addWish(newWish);

    return "redirect:/onsker";
  }

  @PostMapping("/sletonske")
  public String deletePokemon(@RequestParam("id") int id){
    IWishRepository iWishRepository = new IWishRepository();
    iWishRepository.deleteWish(id);

    return "redirect:/onskeliste";
  }

}
