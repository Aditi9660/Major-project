package com.example.Major.Project.Controller;

import com.example.Major.Project.DTO.UserDTO;
import com.example.Major.Project.Service.PackageService;
import com.example.Major.Project.Service.SignUpService;
import com.example.Major.Project.enums.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignUpController {
    @Autowired
    SignUpService signUpService;

    @Autowired
    PackageService packageService;

    @GetMapping("/home")
    public String showhome(Model model) {

        return "home";
    }

    @GetMapping("/aboutus")
    public String showabout(Model model){
        return "AboutUs";
    }

    @GetMapping("/TermsAndConditions")
    public String showTerms(Model model){
        return "TermsAndConditions";
    }

    @GetMapping("/ContactUs")
    public String showContact(Model model){
        return "ContactUs";
    }

    @GetMapping("/services")
    public String showservices(Model model){
        return "Services";
    }


    @GetMapping("/signup")
    public String showsignup(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("role", Role.values());
        return "SignUp";
    }

    @PostMapping("/signup")
    public String saveAllUser(@ModelAttribute UserDTO userDTO, Model model) {
        signUpService.saveUser(userDTO);
        model.addAttribute("message", "sign up successful");
        return "home";
    }

    @GetMapping("/signin")
    public String showlogin(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "SignIn";
    }

    @PostMapping("/signin")
    public String validatelogin(@ModelAttribute UserDTO userDTO, Model model, HttpSession session) {
        UserDTO loginUser = signUpService.validate(userDTO);

        System.out.println("userid"+loginUser.getUserId());
        if (loginUser != null) {
            // Fetch the latest package ID for the user
            Long packageId = packageService.findLatestPackageIdByUser(loginUser);

            if (packageId != null) {
                session.setAttribute("packageId", packageId);
                return "redirect:/display/" + packageId;
            } else {
                model.addAttribute("error", "No package found for this user.");
                return "SignIn";
            }
        } else {
            model.addAttribute("error", "Invalid Username or Password");
            return "SignIn";
        }
    }


    }

