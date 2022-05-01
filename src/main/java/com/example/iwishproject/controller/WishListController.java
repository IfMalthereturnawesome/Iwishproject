package com.example.iwishproject.controller;

import com.example.iwishproject.model.User;
import com.example.iwishproject.model.WishList;
import com.example.iwishproject.repository.IWishRepository;
import com.example.iwishproject.repository.UserRepository;
import com.example.iwishproject.repository.WishListRepository;
import com.example.iwishproject.utility.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@ControllerAdvice
@Controller
public class WishListController {
    UserRepository userRepository;
    WishListRepository wishListRepository;
    IWishRepository iWishRepository;

    private Map<Long, WishList> wishListMap = new HashMap<>();

    public WishListController(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    @GetMapping("/onskeliste")
    public String viewPage(HttpSession session, Model model) {
        String onskeListeSide;
        User loggedUser;
        Cookie cookie = (Cookie) session.getAttribute("id");
        if (cookie != null) {
            onskeListeSide = "onskeliste";
            loggedUser = userRepository.findUserById(cookie.getValue());

            List<WishList> onskelister = wishListRepository.findAllWishLists(loggedUser.getID());
            model.addAttribute("onskeliste", onskelister);

        } else {
            onskeListeSide = "login";
        }
        return onskeListeSide + userRepository.findUserById(String.valueOf(cookie));
    }

    @GetMapping("/onskeliste/{wishListID}")
    public String viewWishes(@PathVariable("wishListID") int wishListID, HttpSession session, Model model) {
        boolean user = false;
        boolean creator = false;

        Cookie cookie = (Cookie) session.getAttribute("id");

        if (cookie != null) {
            user = true;
            String id = cookie.getValue();
            WishList wishList = this.wishListRepository.findWishListByID(wishListID);
            if (wishList != null) {
                creator = true;
            }

        }

        model.addAttribute("user", user);
        model.addAttribute("creator", creator);
        model.addAttribute("userID", wishListID);
        model.addAttribute("wishList", wishListRepository.findAllWishLists(wishListID));
        return "onskeliste";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("msg", "Welcome to the Netherlands!");
    }

    @PostMapping("/tilføjonskeliste")
    public String addWishList(@RequestParam("userID") int userID,
                              @RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @ModelAttribute("user") WishList user,
                              ModelMap model,
                              @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        WishListRepository wishListRepository = new WishListRepository();
        WishList newWishList = new WishList();
        newWishList.setTitle(title);
        newWishList.setDescription(description);

        newWishList.setUserID(userID);


        model.addAttribute("userID", user.getUserID());
        model.addAttribute("title", user.getTitle());
        model.addAttribute("listID", user.getId());



        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        newWishList.setPhotos(fileName);


        if (fileName.isEmpty() && newWishList.getTitle().toLowerCase().contains("Fød".toLowerCase())) {
            newWishList.setPhotos("tillykke-med-foedselsdagen-1.jpg");

        } else if (fileName.isEmpty() && newWishList.getTitle().contains("Jul".toLowerCase())) {
            newWishList.setPhotos("christmas.jpg");

        } else if(!(fileName.isEmpty())){
            String uploadDir = "user-photos/";
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        wishListRepository.addWishList(newWishList);
        //model.addAttribute("userID",userID);
        return "redirect:/onskeliste/" + userID;
    }

    @GetMapping("/sletonskeliste/{id}/{userID}")
    public String deleteWishList(@PathVariable("id") int id,@PathVariable("userID") int userID,Model model) {

        wishListRepository.deleteWishList(id);
        model.addAttribute("wishList", wishListRepository.findAllWishLists(userID));

        return "redirect:/onskeliste" + "/" + userID;
    }


}
