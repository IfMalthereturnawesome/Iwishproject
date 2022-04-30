package com.example.iwishproject.controller;

import com.example.iwishproject.model.Wish;
import com.example.iwishproject.model.WishList;
import com.example.iwishproject.repository.IWishRepository;
import com.example.iwishproject.repository.WishListRepository;
import com.example.iwishproject.utility.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Controller
public class WishListController {

    WishListRepository wishListRepository;

    public WishListController(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    @RequestMapping("/onskeliste")
    public String viewPage(Model model) {
        List<WishList> onskelister = wishListRepository.findAllWishLists();
        model.addAttribute("onskeliste", onskelister);

        return "onskeliste";
    }

    @PostMapping("/tilføjonskeliste")
    public String addWishList(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        WishListRepository wishListRepository = new WishListRepository();
        WishList newWishList = new WishList();
        newWishList.setTitle(title);
        newWishList.setDescription(description);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        newWishList.setPhotos(fileName);

        if (fileName.isEmpty() && newWishList.getTitle().toLowerCase().contains("Fødsel".toLowerCase())) {
            newWishList.setPhotos("tillykke-med-foedselsdagen-1.jpg");
            wishListRepository.addWishList(newWishList);
        } else if (fileName.isEmpty() && newWishList.getTitle().toLowerCase().contains("Jul".toLowerCase())) {
            newWishList.setPhotos("christmas.jpg");
            wishListRepository.addWishList(newWishList);
        } else {

            //Wish savedWish = iWishRepository.addWish(wish);
            String uploadDir = "user-photos/" + newWishList.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            wishListRepository.addWishList(newWishList);
        }

        return "redirect:/onskeliste";
    }

    @GetMapping("/sletonskeliste/{id}")
    public String deleteWishList(@PathVariable("id") int id) {
        WishListRepository wishListRepository = new WishListRepository();
        wishListRepository.deleteWishList(id);

        return "redirect:/onskeliste";
    }


}
