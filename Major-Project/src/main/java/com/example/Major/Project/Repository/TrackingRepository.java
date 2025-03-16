package com.example.Major.Project.Repository;

import com.example.Major.Project.Models.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TrackingRepository extends JpaRepository<Tracking , Long> {
    @Query("SELECT t FROM Tracking t WHERE t.aPackage.packageId = :packageId")
    Optional<Tracking> findByPackageId(@Param("packageId") Long packageId);


}
