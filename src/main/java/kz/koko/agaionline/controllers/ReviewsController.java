package kz.koko.agaionline.controllers;


import kz.koko.agaionline.models.Reviews;
import kz.koko.agaionline.models.User;
import kz.koko.agaionline.service.Interfaces.ReviewsService;
import kz.koko.agaionline.service.Interfaces.UserService;
import kz.koko.agaionline.service.ReviewsServiceImpl;
import kz.koko.agaionline.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class ReviewsController {

    @Autowired
    private ReviewsServiceImpl reviewsServiceImpl;

    @GetMapping("/reviews")
    public String showReviews(Model model) {
        Iterable<Reviews> reviews = reviewsServiceImpl.findAll();
        model.addAttribute("title", "Отзывы");
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @GetMapping("/reviews/addReview")
    public String showReviewForm(Model model) {
        model.addAttribute("title", "Отзывы");
        return "addReview";
    }

    @PostMapping("/reviews/addReview")
    public String addUser(@AuthenticationPrincipal User user, @RequestParam String reviewtext, Model model) {
//        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Reviews review = new Reviews(user.getUsername(), user.getUsername(), reviewtext, formatter.format(date));
        reviewsServiceImpl.save(review);
        return "redirect:/reviews";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/reviews/deleteReview/{reviewId}")
    public String deleteBlog(@PathVariable Integer reviewId) {
        reviewsServiceImpl.delete(reviewsServiceImpl.findById(reviewId));
        return "redirect:/reviews";
    }
}