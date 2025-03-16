package com.example.Major.Project.Controller;


import com.example.Major.Project.DTO.*;
import com.example.Major.Project.Service.PackageService;
import com.example.Major.Project.enums.*;
import com.example.Major.Project.handler.BaseHandler;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PackageController extends BaseHandler {

    @Autowired
    private PackageService packageService;



    @GetMapping("/booking")
    public String showBooking(Model model) {
        model.addAttribute("address", new AddressDTO());
        model.addAttribute("package", new PackageDTO());
        model.addAttribute("type", Type.values());
        model.addAttribute("deliveryType", DeliveryType.values());
        model.addAttribute("PaymentType", PaymentType.values());

        return "ParcelBooking";
    }

    @PostMapping("/booking")
    public String saveBooking(@ModelAttribute PackageDTO packageDTO, Model model, HttpSession session) throws Exception {
        Long packageId = packageService.saveAddress(packageDTO);

        if (packageId == null) {
            throw new RuntimeException("Package ID is NULL after saving the package.");
        }

        System.out.println("Newly saved Package ID: " + packageId);

        // Retrieve the existing package IDs from the session
        List<Long> packageIds = (List<Long>) session.getAttribute("packageIds");
        if (packageIds == null) {
            packageIds = new ArrayList<>();
        }

        packageIds.add(packageId); // Add new package ID
        session.setAttribute("packageIds", packageIds); // Store updated list in session
        session.setAttribute("latestPackageId", packageId); // Store the most recently added package ID separately

        System.out.println("Updated session package IDs: " + packageIds);
        System.out.println("Latest package ID stored in session: " + packageId);

        model.addAttribute("save", "Data saved successfully");

        return "redirect:/payment"; // Redirect to payment page
    }

    @GetMapping("/payment")
    public String showPayment(Model model, HttpSession session) {
        // Retrieve latest package ID from session
        Long packageId = (Long) session.getAttribute("latestPackageId");

        if (packageId == null) {
            // Fallback: Get last stored package ID from packageIds list
            List<Long> packageIds = (List<Long>) session.getAttribute("packageIds");
            if (packageIds != null && !packageIds.isEmpty()) {
                packageId = packageIds.get(packageIds.size() - 1);
                System.out.println("Fallback: Using last package ID from packageIds list: " + packageId);
            } else {
                throw new RuntimeException("No latest package ID found in session.");
            }
        }

        System.out.println("Displaying Payment Page for Package ID: " + packageId);

        // Check if tracking already exists
        TrackingDTO trackingDTO = packageService.getTrackingByPackageId(packageId);
        String trackingNo;

        if (trackingDTO == null) {
            trackingNo = generateTrackingNo();
            trackingDTO = new TrackingDTO();
            trackingDTO.setTrackingNo(trackingNo);
            trackingDTO.setPackageId(packageId);
            packageService.saveTracking(trackingDTO);
        } else {
            trackingNo = trackingDTO.getTrackingNo();
        }

        // Retrieve package details
        PackageDTO packageDTO = packageService.getPackageById(packageId);
        if (packageDTO == null) {
            throw new RuntimeException("Package details not found for ID: " + packageId);
        }

        model.addAttribute("packageDTO", packageDTO);
        model.addAttribute("deliveryCost", packageDTO.getDeliveryCost());
        model.addAttribute("trackingNo", trackingNo);
        model.addAttribute("PaymentType", PaymentType.values());

        return "Payment";
    }

    @PostMapping("/payment")
    public String savePayment(@ModelAttribute PackageDTO packageDTO, HttpSession session, Model model) {
        // Retrieve latest package ID from session
        Long packageId = (Long) session.getAttribute("latestPackageId");

        if (packageId == null) {
            throw new RuntimeException("No latest package ID found in session.");
        }

        System.out.println("Processing Payment for Package ID: " + packageId);

        // Retrieve existing package details
        PackageDTO existingPackage = packageService.getPackageById(packageId);
        if (existingPackage == null) {
            throw new RuntimeException("Package details not found for ID: " + packageId);
        }

        // Update package details
        existingPackage.setPickUpDate(packageDTO.getPickUpDate());
        existingPackage.setPaymentType(packageDTO.getPaymentType());
        packageService.updatePackageDetails(packageId, existingPackage);

        model.addAttribute("message", "Payment saved successfully for Package ID: " + packageId);

        // Remove only the processed package from session
        List<Long> packageIds = (List<Long>) session.getAttribute("packageIds");
        if (packageIds != null) {
            packageIds.remove(packageId);
            if (packageIds.isEmpty()) {
                session.removeAttribute("packageIds");
                System.out.println("Session cleared after last payment.");
            } else {
                session.setAttribute("packageIds", packageIds);
                System.out.println("Remaining package IDs in session: " + packageIds);
            }
        }

        // ✅ Only remove latestPackageId if no more packages are left
        if (packageIds == null || packageIds.isEmpty()) {
            session.removeAttribute("latestPackageId");
            System.out.println("Removed latestPackageId from session.");
        }

        return "redirect:/home"; // Redirect to home after saving
    }


    @GetMapping("/display/{id}")
    public String displayPackage(@PathVariable("id") Long id, Model model, HttpSession session) {
        UserTrackingDTO userTrackingDTO = packageService.displayDetails(id);

        if (userTrackingDTO == null) {
            model.addAttribute("error", "Package details not found.");
            return "errorPage";
        }
        model.addAttribute("tracking", userTrackingDTO);

        // Retrieve the existing package ID list from the session
        List<Long> packageIds = (List<Long>) session.getAttribute("packageIds");

        if (packageIds == null) {
            packageIds = new ArrayList<>();
        }

        if (!packageIds.contains(userTrackingDTO.getPackageId())) {
            packageIds.add(userTrackingDTO.getPackageId()); // Add the package ID
        }

        session.setAttribute("packageIds", packageIds); // Store the list in session

        return "table";  // Return table.html view
    }

    @GetMapping("/display/details")
    public String displayPackageDetail(Model model , HttpSession session) {

        Long packageId = (Long) session.getAttribute("packageId");

       System.out.println("received id in controller "+packageId);

       PackageDTO packageDTO = packageService.displayPackageDetails(packageId);
       // System.out.println("retieved"+packageDTO);

        model.addAttribute("package", packageDTO);

        return "details";
    }





}
