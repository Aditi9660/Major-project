package com.example.Major.Project.Service;

import com.example.Major.Project.DTO.*;
import com.example.Major.Project.Models.Address;
import com.example.Major.Project.Models.Package;
import com.example.Major.Project.Models.Tracking;
import com.example.Major.Project.Models.User;
import com.example.Major.Project.Repository.AddressRepository;
import com.example.Major.Project.Repository.PackageRepository;
import com.example.Major.Project.Repository.TrackingRepository;
import com.example.Major.Project.Repository.UserRepository;
import com.example.Major.Project.handler.BaseHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PackageService extends BaseHandler {

    @Autowired
     AddressRepository addressRepository;

    @Autowired
     PackageRepository packageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrackingRepository trackingRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }


    public Long saveAddress(PackageDTO packageDTO) throws Exception {

        Address sender = new Address();
        sender.setName(packageDTO.getSenderAddress().getName());
        sender.setMobileNo(packageDTO.getSenderAddress().getMobileNo());
        sender.setAddress(packageDTO.getSenderAddress().getAddress());
        sender.setState(packageDTO.getSenderAddress().getState());
        sender.setCity(packageDTO.getSenderAddress().getCity());
        sender.setPostalCode(packageDTO.getSenderAddress().getPostalCode());
        sender.setSenderAddress(true);
        sender.setReceiverAddress(false);

        addressRepository.save(sender);

        Address receiver = new Address();
        receiver.setName(packageDTO.getReceiverAddress().getName());
        receiver.setMobileNo(packageDTO.getReceiverAddress().getMobileNo());
        receiver.setAddress(packageDTO.getReceiverAddress().getAddress());
        receiver.setState(packageDTO.getReceiverAddress().getState());
        receiver.setCity(packageDTO.getReceiverAddress().getCity());
        receiver.setPostalCode(packageDTO.getReceiverAddress().getPostalCode());
        receiver.setSenderAddress(false);
        receiver.setReceiverAddress(true);

        addressRepository.save(receiver);

        Package p = new Package();
        p.setWeight(packageDTO.getWeight());
        p.setHeight(packageDTO.getHeight());
        p.setLength(packageDTO.getLength());
        p.setBreadth(packageDTO.getBreadth());
        p.setCostOfPackage(packageDTO.getCostOfPackage());
        p.setPaymentType(packageDTO.getPaymentType());
        p.setType(packageDTO.getType());
        p.setDetails(packageDTO.getDetails());
        p.setSender(sender);
        p.setReceiver(receiver);
        p.setPickUpDate(packageDTO.getPickUpDate());
        p.setPaymentType(packageDTO.getPaymentType());
        p.setDeliveryCost(cost(packageDTO));
        p.setDeliveryType(packageDTO.getDeliveryType());

       /* Optional<User> user = userRepository.findById(packageDTO.getUserId());
        if(user.isEmpty()){
            throw new Exception("User not found");
        }*/

        /*Optional<Tracking> tracking = trackingRepository.findById(packageDTO.getTrackingId());
        if(tracking.isEmpty()){
            throw new Exception("trackingId not found");
        }*/

        //p.setUser(user.get());
        //p.setTracking(tracking.get());

        Package savedpackage = packageRepository.save(p);
        return savedpackage.getPackageId();

    }

    public UserTrackingDTO displayDetails(Long packageId) {
        Package p = packageRepository.findById(packageId).orElse(null);

        if (p == null) {
            return null;  // Handle case where package is not found
        }

        return new UserTrackingDTO(
                p.getUser().getUserId(),
                p.getUser().getUsername(),
                p.getTracking().getTrackingNo(),
                p.getTracking().getDeliveryStatus(),
                p.getPackageId(),
                p.getDeliveryType()
        );

    }


    public void saveTracking(TrackingDTO trackingDTO){
        String generateTrackingNumber = generateTrackingNo();


        Tracking tracking = new Tracking();
        tracking.setTrackingNo(generateTrackingNumber);
        tracking.setDeliveryStatus(trackingDTO.getDeliveryStatus());
        tracking.setDeliveryDate(trackingDTO.getDeliveryDate());
        trackingRepository.save(tracking);

    }


   public PackageDTO displayPackageDetails(Long packageId){
       Package p = packageRepository.findById(packageId).orElse(null);

       if (p == null) {
           return null;
       }

       System.out.println("package id "+ p.getPackageId()+"package length"+p.getLength());

       PackageDTO packageDTO = new PackageDTO();
       packageDTO = new PackageDTO(
               p.getPackageId(),
               p.getLength(),
               p.getBreadth(),
               p.getHeight(),
               p.getWeight(),
               p.getCostOfPackage(),
               p.getType(),
               p.getDetails(),
               p.getDeliveryCost());

       System.out.println("id"+packageDTO.getPackageId());

       return packageDTO;
    }

    public PackageDTO displaycost(Long packageId){
        Package p = packageRepository.findById(packageId).orElse(null);
        PackageDTO packageDTO = new PackageDTO();
        packageDTO.getDeliveryCost();
        return packageDTO;

    }

    public void updatePackageDetails(Long packageId, PackageDTO packageDTO) {
        // Retrieve package from the database
        Package p = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // ✅ Ensure pickUpDate and paymentType are updated correctly
        if (packageDTO.getPickUpDate() != null) {
            p.setPickUpDate(packageDTO.getPickUpDate());
        }

        if (packageDTO.getPaymentType() != null) {
            p.setPaymentType(packageDTO.getPaymentType());
        }

        // ✅ Ensure changes are saved
        packageRepository.save(p);
        System.out.println("Package details updated for ID: " + packageId);
    }

    public PackageDTO getPackageById(Long packageId) {
        Package packageEntity = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found with ID: " + packageId));

        System.out.println("Retrieved package with cost: " + packageEntity.getDeliveryCost());
        // Convert entity to DTO
        return new PackageDTO(
                packageEntity.getPackageId(),
                packageEntity.getDeliveryCost(),
                packageEntity.getPickUpDate(),
                packageEntity.getPaymentType()
        );
    }

    public TrackingDTO getTrackingByPackageId(Long packageId) {
        Optional<Tracking> trackingOptional = trackingRepository.findByPackageId(packageId);

        if (trackingOptional.isPresent()) {
            Tracking tracking = trackingOptional.get();
            return new TrackingDTO(
                    tracking.getTrackingNo(),
                    tracking.getDeliveryStatus(),
                    tracking.getDeliveryDate(),
                    tracking.getAPackage().getPackageId() // Ensure packageId is set
            );
        }

        // Return null instead of throwing an exception
        return null;
    }

    public Long findLatestPackageIdByUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Package latestPackage = packageRepository.findLatestPackageByUser(user);

        Long packageId = (latestPackage != null) ? latestPackage.getPackageId() : null;

        System.out.println("Fetched Package ID: " + packageId); // Debugging output

        return packageId;
    }
















}
