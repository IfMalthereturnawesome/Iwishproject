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
public class WishListController {

    WishListController wishListController;

    public WishListController(WishListController wishListController) {
        this.wishListController = wishListController;
    }

    @RequestMapping("/onskeliste")
    public String viewPage(Model model){
        List<Wish> onskelister = wishListController.findAllWishes();
        model.addAttribute("onskeliste",onskelister);

        return "onskeliste";
    }

    @PostMapping("/tilføjonskeliste")
    public String addWish(@RequestParam("title") String title,
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

        return "redirect:/onskeliste";
    }

    @GetMapping("/sletonskeliste/{id}")
    public String deleteWishList(@PathVariable("id") int id){
        IWishRepository iWishRepository = new IWishRepository();
        iWishRepository.deleteWish(id);

        return "redirect:/onskeliste";
    }


}
