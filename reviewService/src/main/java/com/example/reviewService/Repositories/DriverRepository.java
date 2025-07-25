package com.example.reviewService.Repositories;

import com.example.reviewService.Models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Dictionary;
import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {

   // Optional<Driver> findByIdAndLicenseNumber(Long id, String licenseNumber);

   List<Driver> findAllByIdIn(List<Long> driverIds);

}
