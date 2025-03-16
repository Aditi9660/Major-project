package com.example.Major.Project.Repository;

import com.example.Major.Project.Models.Package;
import com.example.Major.Project.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    Package findLatestPackageByUser(User user);

}
