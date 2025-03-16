package com.example.Major.Project.Service;

import com.example.Major.Project.DTO.PackageDTO;
import com.example.Major.Project.DTO.TrackingDTO;
import com.example.Major.Project.Models.Package;
import com.example.Major.Project.Models.Tracking;
import com.example.Major.Project.Repository.PackageRepository;
import com.example.Major.Project.Repository.TrackingRepository;
import com.example.Major.Project.handler.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService extends BaseHandler {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private TrackingRepository trackingRepository;



    public void saveTracking(TrackingDTO trackingDTO){
        Tracking tracking = new Tracking();
        tracking.setTrackingNo(generateTrackingNo());
        trackingRepository.save(tracking);
    }

    public String displayTrackingNo(){
       return generateTrackingNo();
    }





}
